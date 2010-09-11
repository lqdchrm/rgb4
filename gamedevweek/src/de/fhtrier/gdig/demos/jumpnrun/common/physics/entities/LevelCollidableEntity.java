package de.fhtrier.gdig.demos.jumpnrun.common.physics.entities;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Level;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.SpawnPoint;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.physics.Collisions;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;

public class LevelCollidableEntity extends CollidableEntity {

	protected TiledMap map;
	protected Level level;

	private boolean leftCollision = false;
	private boolean rightCollision = false;
	private boolean topCollision = false;
	private boolean bottomCollision = false;
	private boolean bottomLeftCollision = false;
	private boolean bottomRightCollision = false;

	private float[] correction = new float[] { 0.0f, 0.0f };

	/**
	 * Custom entity class which implements level collisions (ugly ?) needs to
	 * have TiledMap and Bounds set before you should call something
	 */
	public LevelCollidableEntity(int id, EntityType type) {
		super(id, type);
	}

	/**
	 * Entity BoundingBox is relative to center, i.e. we need to transform it to
	 * a common basis. Assuming the entity belongs to the same parent as the
	 * tilemap we use center offset and position
	 * 
	 * @return transformed Shape
	 */
	public Shape getTransformedBounds() {
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
		if (!isActive()) {
			return false;
		}

		if (Constants.Debug.showCollisions) {
			markCollisionTiles(12);
		}

		boolean result = super.handleCollisions();

		boolean collided = false;

		// do it two times: one for each direction
		for (int i = 0; i < 2; i++) {

			if (this.map != null && this.getBounds() != null) {

				Shape bbEntity = this.getTransformedBounds();

				leftCollision = false;
				rightCollision = false;
				topCollision = false;
				bottomCollision = false;
				bottomLeftCollision = false;
				bottomRightCollision = false;

				// determine tiles to check for collisions
				final int leftTile = (int) Math.floor(bbEntity.getMinX()
						/ this.map.getTileWidth());
				final int rightTile = (int) Math.ceil(bbEntity.getMaxX()
						/ this.map.getTileWidth() + 1);
				final int topTile = (int) Math.floor(bbEntity.getMinY()
						/ this.map.getTileHeight());
				final int bottomTile = (int) Math.ceil(bbEntity.getMaxY()
						/ this.map.getTileHeight() + 1);

				for (int y = Math.max(0, topTile); y < Math.min(
						this.map.getHeight(), bottomTile); y++) {
					for (int x = Math.max(0, leftTile); x < Math.min(
							this.map.getWidth(), rightTile); x++) {

						// items
						final int tileId = this.map.getTileId(x, y,
								Constants.Level.collisionLayer);

						final Rectangle bbTile = new Rectangle(x
								* this.map.getTileWidth(), y
								* this.map.getTileHeight(),
								this.map.getTileWidth(),
								this.map.getTileHeight());

						if (tileId > 0) {
							collided |= collisionWithTile(bbTile);
						}

						int actionTileId = this.map.getTileId(x, y,
								Constants.Level.logicLayer);

						if (actionTileId > 0) {
							float[] intersectionDepth = Collisions
									.getIntersectionDepth(bbTile,
											this.getTransformedBounds());
							if (level != null
									&& (intersectionDepth[Entity.X] != 0 || intersectionDepth[Entity.Y] != 0)) {
								
								if (Constants.Debug.tileMapLogicDebug) {
									Log.debug("Colission with: " + actionTileId);
								}
								
								actionTileId -= level.firstLogicGID;
								++actionTileId;
								if (actionTileId > 32 && actionTileId <= 64) {
									SpawnPoint randomTeleporterExitPoint = level
											.getRandomTeleporterExitPoint(actionTileId - 32);
									this.getData()[Entity.X] = randomTeleporterExitPoint.x;
									this.getData()[Entity.Y] = randomTeleporterExitPoint.y;
								}
							}
						}
					}
				}
			}

			if (collided
					&& (correction[Entity.X] != 0 || correction[Entity.Y] != 0)) {
				// correct position
				if (Math.abs(correction[Entity.X]) <= Math
						.abs(correction[Entity.Y])) {
					if (leftCollision || rightCollision || !isOnGround()) {
						this.getData()[Entity.X] += correction[Entity.X];
						this.getVel()[Entity.X] = 0.0f;
					}
				} else {
					if (topCollision || bottomCollision) {
						this.getData()[Entity.Y] += correction[Entity.Y];
						this.getVel()[Entity.Y] = 0.0f;
					}
				}
			}
			correction[Entity.X] = 0.0f;
			correction[Entity.Y] = 0.0f;
		}
		return (collided || result);
	}

	/**
	 * Returns true if Entity collides with bbTile
	 * 
	 * @param bbTile
	 *            The Tile to check collision with
	 * @return Returns if Entity collides with bbTile
	 */
	private boolean collisionWithTile(Shape bbTile) {

		Shape bbEntity = this.getTransformedBounds();

		boolean collided = false;

		// collisions punkte berechnen
		leftCollision |= bbTile.contains(bbEntity.getMinX()
				- Constants.GamePlayConstants.colissionPointDistance,
				bbEntity.getCenterY());
		rightCollision |= bbTile.contains(bbEntity.getMaxX()
				+ Constants.GamePlayConstants.colissionPointDistance,
				bbEntity.getCenterY());

		if (bbEntity.getHeight() >= bbTile.getHeight() * 2) {
			leftCollision |= bbTile.contains(bbEntity.getMinX()
					- Constants.GamePlayConstants.colissionPointDistance,
					bbEntity.getMaxY() - bbTile.getHeight() / 2);
			rightCollision |= bbTile.contains(bbEntity.getMaxX()
					+ Constants.GamePlayConstants.colissionPointDistance,
					bbEntity.getMaxY() - bbTile.getHeight() / 2);

			leftCollision |= bbTile.contains(bbEntity.getMinX()
					- Constants.GamePlayConstants.colissionPointDistance,
					bbEntity.getMinY() + bbTile.getHeight() / 2);
			rightCollision |= bbTile.contains(bbEntity.getMaxX()
					+ Constants.GamePlayConstants.colissionPointDistance,
					bbEntity.getMinY() + bbTile.getHeight() / 2);
		}

		topCollision |= bbTile.contains(bbEntity.getCenterX(),
				bbEntity.getMinY()
						- Constants.GamePlayConstants.colissionPointDistance);
		bottomCollision |= bbTile.contains(bbEntity.getCenterX(),
				bbEntity.getMaxY()
						+ Constants.GamePlayConstants.colissionPointDistance);
		bottomLeftCollision |= bbTile.contains(bbEntity.getMinX()
				- Constants.GamePlayConstants.colissionPointDistance,
				bbEntity.getMaxY()
						+ Constants.GamePlayConstants.colissionPointDistance);
		bottomRightCollision |= bbTile.contains(bbEntity.getMaxX()
				+ Constants.GamePlayConstants.colissionPointDistance,
				bbEntity.getMaxY()
						+ Constants.GamePlayConstants.colissionPointDistance);

		if (bbEntity.getWidth() >= bbTile.getWidth() * 2) {
			topCollision |= bbTile
					.contains(
							bbEntity.getMaxX() - bbTile.getWidth() / 2,
							bbEntity.getMinY()
									- Constants.GamePlayConstants.colissionPointDistance);
			bottomCollision |= bbTile
					.contains(
							bbEntity.getMaxX() - bbTile.getWidth() / 2,
							bbEntity.getMaxY()
									+ Constants.GamePlayConstants.colissionPointDistance);

			topCollision |= bbTile
					.contains(
							bbEntity.getMinX() + bbTile.getWidth() / 2,
							bbEntity.getMinY()
									- Constants.GamePlayConstants.colissionPointDistance);
			bottomCollision |= bbTile
					.contains(
							bbEntity.getMinX() + bbTile.getWidth() / 2,
							bbEntity.getMaxY()
									+ Constants.GamePlayConstants.colissionPointDistance);
		}

		float[] depth = Collisions.getIntersectionDepth(bbEntity, bbTile);

		final float absDepthX = Math.abs(depth[Entity.X]);
		final float absDepthY = Math.abs(depth[Entity.Y]);

		if (absDepthX > 0 || absDepthY > 0) {
			collided = true;
			if (absDepthX + absDepthY > Math.abs(correction[Entity.X])
					+ Math.abs(correction[Entity.Y])) {
				correction[Entity.X] = depth[Entity.X];
				correction[Entity.Y] = depth[Entity.Y];
			}
		}
		return collided;
	}

	public boolean isLeftCollision() {
		return leftCollision;
	}

	public boolean isRightCollision() {
		return rightCollision;
	}

	public boolean isTopCollision() {
		return topCollision;
	}

	public boolean isBottomCollision() {
		return bottomCollision;
	}

	public boolean isOnGround() {
		if (bottomLeftCollision && bottomRightCollision)
			return true;
		if (!bottomCollision && bottomLeftCollision && leftCollision)
			return false;
		if (!bottomCollision && bottomRightCollision && rightCollision)
			return false;
		return this.bottomCollision || this.bottomLeftCollision
				|| this.bottomRightCollision;
	}

	/**
	 * used as hack to highlight tiles assumes the tileset contains a lighter
	 * version of each tile at tileid+offset, where offset has to be the same
	 * for all tiles
	 */
	@Deprecated
	public void markCollisionTiles(final int offset) {

		if (this.map != null && this.getBounds() != null) {

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
			for (int y = topTile - 1; y <= bottomTile + 1; y++) {

				if (y < 0 || y >= this.map.getHeight()) {
					continue;
				}

				for (int x = leftTile - 1; x <= rightTile + 1; x++) {

					if (x < 0 || x >= this.map.getWidth()) {
						continue;
					}

					// if tile is not empty
					// TODO read from special layer
					final int tileId = this.map.getTileId(x, y,
							Constants.Level.collisionLayer);
					if (tileId > 0) {

						// Bounding box for current tile
						Rectangle bbTile = new Rectangle(
								x * map.getTileWidth(),
								y * map.getTileHeight(), map.getTileWidth(),
								map.getTileHeight());

						if (bbEntity.intersects(bbTile)) {
							// mark tile
							if (tileId < offset) {
								this.map.setTileId(x, y, 0, tileId + offset);
							}
						} else {
							// unmark tile
							if (tileId > offset) {
								this.map.setTileId(x, y, 0, tileId - offset);
							}
						}
					}
				}
			}
		}
	}

	public void setMap(final TiledMap map) {
		this.map = map;
	}

	public void setOnGround(final boolean onGround) {
		this.bottomCollision = bottomLeftCollision = bottomRightCollision = onGround;
	}

	public void setLevel(Level level) {
		this.level = level;
		this.map = level.getMap();
	}
}
