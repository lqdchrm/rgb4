package de.fhtrier.gdig.demos.jumpnrun.common.entities.physics;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.demos.jumpnrun.common.Constants;
import de.fhtrier.gdig.demos.jumpnrun.common.Level;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.physics.CollidableEntity;
import de.fhtrier.gdig.engine.entities.physics.Collisions;

public class LevelCollidableEntity extends CollidableEntity
{

	private boolean onGround;
	protected TiledMap map;

	/**
	 * Custom entity class which implements level collisions (ugly ?) needs to
	 * have TiledMap and Bounds set before you should call something
	 */
	public LevelCollidableEntity(int id, EntityType type)
	{
		super(id, type);
	}

	/**
	 * Entity BoundingBox is relative to center, i.e. we need to transform it to
	 * a common basis. Assuming the entity belongs to the same parent as the
	 * tilemap we use center offset and position
	 * 
	 * @return transformed Shape
	 */
	private Shape getTransformedBounds()
	{
		return this.getBounds().transform(
				Transform.createTranslateTransform(this.getData()[Entity.X],
						this.getData()[Entity.Y]));
	}

	/**
	 * handles collisions for entity with tiledmap
	 * 
	 * @return hasCollided
	 */
	@Override
	public boolean handleCollisions() {
		if(!isActive())
		{
			return false;
		}
		
		if (Constants.Debug.showCollisions) {
			markCollisionTiles(12);
		}
		
		boolean result = super.handleCollisions();
		
		this.onGround = false;
		boolean collided = false;

		if (this.map != null && this.getBounds() != null)
		{

			Shape bbEntity = this.getTransformedBounds();

			// determine tiles to check for collisions
			final int leftTile = (int) Math.floor(bbEntity.getMinX()
					/ this.map.getTileWidth());
			final int rightTile = (int) Math.ceil(bbEntity.getMaxX()
					/ this.map.getTileWidth());
			final int topTile = (int) Math.floor(bbEntity.getMinY()
					/ this.map.getTileHeight());
			final int bottomTile = (int) Math.ceil(bbEntity.getMaxY()
					/ this.map.getTileHeight());

			for (int y = Math.max(0, topTile); y < Math.min(
					this.map.getHeight(), bottomTile); y++)
			{
				for (int x = Math.max(0, leftTile); x < Math.min(
						this.map.getWidth(), rightTile); x++)
				{

					// items
					final int tileId = this.map.getTileId(x, y, 0);

					if (tileId > 0)
					{
						final Rectangle bbTile = new Rectangle(x
								* this.map.getTileWidth(), y
								* this.map.getTileHeight(),
								this.map.getTileWidth(),
								this.map.getTileHeight());

						final float[] depth = Collisions.getIntersectionDepth(
								bbEntity, bbTile);

						final float absDepthX = Math.abs(depth[Entity.X]);
						final float absDepthY = Math.abs(depth[Entity.Y]);

						if (absDepthX > 0 || absDepthY > 0)
						{

							switch (tileId)
							{
							case 1:
							case 13:
								this.map.setTileId(x, y, 0, 0);
								break;
							default:
								if (absDepthY < absDepthX)
								{
									this.getData()[Entity.Y] += depth[Entity.Y];
									this.getVel()[Entity.Y] = 0.0f;
									bbEntity = this.getTransformedBounds();

									if (depth[Entity.Y] < 0)
									{
										this.onGround = true;
									}

									collided = true;
								} else
								{
									this.getData()[Entity.X] += depth[Entity.X];
									this.getVel()[Entity.X] = 0.0f;
									bbEntity = this.getTransformedBounds();
									collided = true;
								}
								break;
							}
						}
					}
				}
			}
		}
		return (collided || result);
	}

	public boolean isOnGround()
	{
		return this.onGround;
	}

	/**
	 * used as hack to highlight tiles assumes the tileset contains a lighter
	 * version of each tile at tileid+offset, where offset has to be the same
	 * for all tiles
	 */
	@Deprecated
	public void markCollisionTiles(final int offset)
	{

		if (this.map != null && this.getBounds() != null)
		{

			// Player BoundingBox
			Shape bbEntity = getTransformedBounds();

			// determine tiles to check for collisions
			int leftTile = (int) (Math.floor(bbEntity.getMinX()
					/ map.getTileWidth()));
			int rightTile = (int) (Math.ceil(bbEntity.getMaxX()
					/ map.getTileWidth())) - 1;
			int topTile = (int) (Math.floor(bbEntity.getMinY()
					/ map.getTileHeight()));
			int bottomTile = (int) (Math.ceil(bbEntity.getMaxY()
					/ map.getTileHeight())) - 1;

			// mark Collision Tiles
			for (int y = topTile - 1; y <= bottomTile + 1; y++)
			{

				if (y < 0 || y >= this.map.getHeight())
				{
					continue;
				}

				for (int x = leftTile - 1; x <= rightTile + 1; x++)
				{

					if (x < 0 || x >= this.map.getWidth())
					{
						continue;
					}

					// if tile is not empty
					// TODO read from special layer
					final int tileId = this.map.getTileId(x, y, 0);
					if (tileId > 0)
					{

						// Bounding box for current tile
						Rectangle bbTile = new Rectangle(
								x * map.getTileWidth(),
								y * map.getTileHeight(), map.getTileWidth(),
								map.getTileHeight());

						if (bbEntity.intersects(bbTile)) {
							// mark tile
							if (tileId < offset)
							{
								this.map.setTileId(x, y, 0, tileId + offset);
							}
						} else
						{
							// unmark tile
							if (tileId > offset)
							{
								this.map.setTileId(x, y, 0, tileId - offset);
							}
						}
					}
				}
			}
		}
	}

	public void setMap(final TiledMap map)
	{
		this.map = map;
	}

	public void setOnGround(final boolean onGround)
	{
		this.onGround = onGround;
	}

	public void setLevel(Level level) {
		this.map = level.getMap();
	}
}
