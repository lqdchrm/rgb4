package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.demos.jumpnrun.common.network.EntityData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.PlayerState;
import de.fhtrier.gdig.engine.entities.gfx.AnimationEntity;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.entities.physics.Collisions;
import de.fhtrier.gdig.engine.entities.physics.PhysicsEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public class Player extends PhysicsEntity {

	private static final Color[] PlayerColors = { Color.blue, Color.green,
			Color.red, Color.yellow, Color.black };

	private static float EPSILON = 0.0001f;

	private int currentState = -1;
	private boolean onGround;

	private Level level;
	private ImageEntity idleImage;
	private AnimationEntity runAnimation;
	private AnimationEntity jumpAnimation;
	private Animation jump;

	private float maxPlayerSpeed = 1000.0f;

	private EntityData playerData;

	public Player(int id, Factory factory) throws SlickException {
		super(id);

		AssetMgr assets = factory.getAssetMgr();

		// gfx
		assets.storeImage(Assets.PlayerIdleImage, "sprites/player/Idle.png");
		assets.storeAnimation(Assets.PlayerRunAnim, "sprites/player/Run.png",
				96, 96, 75);
		this.jump = assets.storeAnimation(Assets.PlayerJumpAnim,
				"sprites/player/Jump.png", 96, 96, 70);
		this.jump.setLooping(false);

		this.idleImage = factory.createImageEntity(Assets.PlayerIdleImage,
				Assets.PlayerIdleImage);
		this.runAnimation = factory.createAnimationEntity(Assets.PlayerRunAnim,
				Assets.PlayerRunAnim);
		this.jumpAnimation = factory.createAnimationEntity(
				Assets.PlayerJumpAnim, Assets.PlayerJumpAnim);

		add(this.idleImage);
		add(this.runAnimation);
		add(this.jumpAnimation);

		// physics
		// X Y OX OY FX FY SY SY ROT
		initData(new float[] { 200, 200, 48, 96, 0, 0, 1, 1, 0 }); // pos +
																	// origin +
																	// focus +
																	// scale +
																	// rot
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 981, 0, 0, 0, 0, 0, 0, 0 }); // gravity
		setBounds(new Rectangle(30, 0, 36, 96)); // bounding box

		setVisible(true);
		setActive(true);

		// order
		setOrder(EntityOrder.Player);
		level = null;

		// startup
		setState(PlayerState.Idle);
	}

	private boolean handleCollisions() {

		this.onGround = false;
		boolean collided = false;

		TiledMap map = this.level.getMap();

		// Player BoundingBox
		Shape bbPlayer = new Rectangle(getData()[X] - 18, getData()[Y] - 96,
				36, 96);

		// determine tiles to check for collisions
		int leftTile = (int) (Math.floor(bbPlayer.getMinX()
				/ map.getTileWidth()));
		int rightTile = (int) (Math.ceil(bbPlayer.getMaxX()
				/ map.getTileWidth()));
		int topTile = (int) (Math.floor(bbPlayer.getMinY()
				/ map.getTileHeight()));
		int bottomTile = (int) (Math.ceil(bbPlayer.getMaxY()
				/ map.getTileHeight()));

		for (int y = Math.max(0, topTile); y < Math.min(map.getHeight(),
				bottomTile); y++) {
			for (int x = Math.max(0, leftTile); x < Math.min(map.getWidth(),
					rightTile); x++) {

				// items
				int tileId = map.getTileId(x, y, 0);

				if (tileId > 0) {
					Rectangle bbTile = new Rectangle(x * map.getTileWidth(), y
							* map.getTileHeight(), map.getTileWidth(),
							map.getTileHeight());

					float[] depth = Collisions.getIntersectionDepth(bbPlayer,
							bbTile);

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
								bbPlayer = new Rectangle(getData()[X] - 18,
										getData()[Y] - 96, 36, 96);

								if (depth[Y] < 0) {
									this.onGround = true;
								}

								collided = true;
							} else {
								getData()[X] += depth[X];
								getVel()[X] = 0.0f;
								bbPlayer = new Rectangle(getData()[X] - 18,
										getData()[Y] - 96, 36, 96);
								collided = true;
							}
							break;
						}
					}
				}
			}
		}
		return collided;
	}

	private void markCollisionTiles() {

		// Player BoundingBox
		Shape bbPlayer = new Rectangle(getData()[X] - 18, getData()[Y] - 96,
				36, 96);

		TiledMap map = this.level.getMap();

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
					Rectangle bbTile = new Rectangle(x * map.getTileWidth(), y
							* map.getTileHeight(), map.getTileWidth(),
							map.getTileHeight());

					if (bbPlayer.intersects(bbTile)) {
						// mark tile
						if (tileId < 12) {
							map.setTileId(x, y, 0, tileId + 12);
						}
					} else {
						// unmark tile
						if (tileId > 12) {
							map.setTileId(x, y, 0, tileId - 12);
						}
					}
				}
			}
		}
	}

	@Override
	public void update(int deltaInMillis) {

		if (isActive() && level != null) {

			super.update(deltaInMillis); // calc physics

			if (getVel()[X] > this.maxPlayerSpeed) {
				getVel()[X] = this.maxPlayerSpeed;
			}

			if (getVel()[Y] > this.maxPlayerSpeed) {
				getVel()[Y] = this.maxPlayerSpeed;
			}

			if (getVel()[X] < -this.maxPlayerSpeed) {
				getVel()[X] = -this.maxPlayerSpeed;
			}

			if (getVel()[Y] < -this.maxPlayerSpeed) {
				getVel()[Y] = -this.maxPlayerSpeed;
			}

			markCollisionTiles();
			handleCollisions();

			if ((this.currentState == PlayerState.Idle)
					&& (Math.abs(getData()[X] - getPrevPos()[X]) < EPSILON)
					&& (Math.abs(getData()[Y] - getPrevPos()[Y]) < EPSILON)) {
				getVel()[X] = getVel()[Y] = 0.0f;
			}
		}
	}

	@Override
	public void renderImpl(Graphics graphicContext) {

		if (getId() == -1) {
			throw new RuntimeException("Wrong Initialization: no Client ID set");
		}

		graphicContext.setColor(PlayerColors[getId() % PlayerColors.length]);

		super.renderImpl(graphicContext);
	}

	@Override
	public void handleInput(Input input) {
		if (isActive()) {
			if (!input.isKeyDown(Input.KEY_LEFT)
					&& !input.isKeyDown(Input.KEY_RIGHT)
					&& !input.isKeyDown(Input.KEY_SPACE)) {
				setState(PlayerState.Idle);
			}

			if (input.isKeyDown(Input.KEY_LEFT)) {
				setState(PlayerState.RunLeft);
			}

			if (input.isKeyDown(Input.KEY_RIGHT)) {
				setState(PlayerState.RunRight);
			}

			if (input.isKeyDown(Input.KEY_UP)) {
				if (isOnGround()) {
					setState(PlayerState.Jump);
				}
			}
		}
		super.handleInput(input);
	}

	public void setState(int state) {
		if (state != this.currentState) {
			leaveState(this.currentState);
			enterState(state);
		}
	}

	private void leaveState(int state) {
		switch (state) {
		case PlayerState.Idle:
			this.idleImage.setActive(false);
			this.idleImage.setVisible(false);
			break;
		case PlayerState.RunLeft:
		case PlayerState.RunRight:
			this.runAnimation.setActive(false);
			this.runAnimation.setVisible(false);
			break;
		case PlayerState.Jump:
			this.jumpAnimation.setActive(false);
			this.jumpAnimation.setVisible(false);
		}
	}

	private void enterState(int state) {
		this.currentState = state;
		switch (state) {
		case PlayerState.Idle:
			getAcc()[X] = 0.0f;
			this.idleImage.setActive(true);
			this.idleImage.setVisible(true);
			break;
		case PlayerState.RunLeft:
			getAcc()[X] = -2000.0f;
			getData()[SX] = 1;
			this.runAnimation.setActive(true);
			this.runAnimation.setVisible(true);
			break;
		case PlayerState.RunRight:
			getAcc()[X] = 2000.0f;
			getData()[SX] = -1;
			this.runAnimation.setActive(true);
			this.runAnimation.setVisible(true);
			break;
		case PlayerState.Jump:
			getVel()[Y] = -800;
			this.jump.start();
			this.jumpAnimation.setActive(true);
			this.jumpAnimation.setVisible(true);
			break;
		}
	}

	public boolean isOnGround() {
		return this.onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public EntityData getPlayerData() {
		if (this.playerData == null) {
			this.playerData = new EntityData();
			this.playerData.id = getId();
		}

		this.playerData.state = this.currentState;
		this.playerData.data = getData();

		return this.playerData;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
