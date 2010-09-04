package de.fhtrier.gdig.demos.jumpnrun.common.entities.physics;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.engine.entities.physics.CollidableEntity;
import de.fhtrier.gdig.engine.entities.physics.Collisions;

public class LevelCollidableEntity extends CollidableEntity {

	private boolean onGround;
	private TiledMap map;

	/**
	 * Custom entity class which implements level collisions (ugly ?) needs to
	 * have TiledMap and Bounds set before you should call something
	 */
	public LevelCollidableEntity(int id) {
		super(id);
	}

	/**
	 * Entity BoundingBox is relative to center, i.e.
	 *  we need to transform it to a common basis.
	 *  Assuming the entity belongs to the same parent as
	 *  the tilemap we use center offset and position
	 *
	 * @return transformed Shape
	 */
	private Shape getTransformedBounds() {
		return getBounds().transform(Transform.createTranslateTransform(getData()[X], getData()[Y]));
	}
	
	/**
	 * handles collisions for entity with tiledmap
	 * @return hasCollided
	 */
	public boolean handleCollisions() {

		this.onGround = false;
		boolean collided = false;

		if (map != null && getBounds() != null) {

			Shape bbEntity = getTransformedBounds();

			// determine tiles to check for collisions
			int leftTile = (int) (Math.floor(bbEntity.getMinX()
					/ map.getTileWidth()));
			int rightTile = (int) (Math.ceil(bbEntity.getMaxX()
					/ map.getTileWidth()));
			int topTile = (int) (Math.floor(bbEntity.getMinY()
					/ map.getTileHeight()));
			int bottomTile = (int) (Math.ceil(bbEntity.getMaxY()
					/ map.getTileHeight()));

			for (int y = Math.max(0, topTile); y < Math.min(map.getHeight(),
					bottomTile); y++) {
				for (int x = Math.max(0, leftTile); x < Math.min(
						map.getWidth(), rightTile); x++) {

					// items
					int tileId = map.getTileId(x, y, 0);

					if (tileId > 0) {
						Rectangle bbTile = new Rectangle(
								x * map.getTileWidth(),
								y * map.getTileHeight(), map.getTileWidth(),
								map.getTileHeight());

						float[] depth = Collisions.getIntersectionDepth(
								bbEntity, bbTile);

						float absDepthX = Math.abs(depth[X]);
						float absDepthY = Math.abs(depth[Y]);

						if ((absDepthX > 0) || (absDepthY > 0)) {

							switch (tileId) {
							case 1:
							case 13:
								map.setTileId(x, y, 0, 0);
								break;
							default:
								if (absDepthY < absDepthX) {
									getData()[Y] += depth[Y];
									getVel()[Y] = 0.0f;
									bbEntity = getTransformedBounds();

									if (depth[Y] < 0) {
										this.onGround = true;
									}

									collided = true;
								} else {
									getData()[X] += depth[X];
									getVel()[X] = 0.0f;
									bbEntity = getTransformedBounds();
									collided = true;
								}
								break;
							}
						}
					}
				}
			}
		}
		return collided;
	}

	/**
	 * used as hack to highlight tiles
	 * assumes the tileset contains a lighter version
	 * of each tile at tileid+offset, where offset has
	 * to be the same for all tiles
	 */
	@Deprecated
	public void markCollisionTiles(int offset) {

		if (map != null && getBounds()!= null) {

			// Player BoundingBox
			Shape bbPlayer = getTransformedBounds();

			// determine tiles to check for collisions
			int leftTile = (int) (Math.floor(bbPlayer.getMinX()
					/ map.getTileWidth()));
			int rightTile = (int) (Math.ceil(bbPlayer.getMaxX()
					/ map.getTileWidth())) - 1;
			int topTile = (int) (Math.floor(bbPlayer.getMinY()
					/ map.getTileHeight()));
			int bottomTile = (int) (Math.ceil(bbPlayer.getMaxY()
					/ map.getTileHeight())) - 1;

			// mark Collision Tiles
			for (int y = topTile - 1; y <= bottomTile + 1; y++) {

				if ((y < 0) || (y >= map.getHeight())) {
					continue;
				}

				for (int x = leftTile - 1; x <= rightTile + 1; x++) {

					if ((x < 0) || (x >= map.getWidth())) {
						continue;
					}

					// if tile is not empty
					// TODO read from special layer
					int tileId = map.getTileId(x, y, 0);
					if (tileId > 0) {

						// Bounding box for current tile
						Rectangle bbTile = new Rectangle(
								x * map.getTileWidth(),
								y * map.getTileHeight(), map.getTileWidth(),
								map.getTileHeight());

						if (bbPlayer.intersects(bbTile)) {
							// mark tile
							if (tileId < offset) {
								map.setTileId(x, y, 0, tileId + offset);
							}
						} else {
							// unmark tile
							if (tileId > offset) {
								map.setTileId(x, y, 0, tileId - offset);
							}
						}
					}
				}
			}
		}
	}


	public boolean isOnGround() {
		return this.onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
}
