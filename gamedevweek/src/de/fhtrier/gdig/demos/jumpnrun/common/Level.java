package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.demos.jumpnrun.JumpNRun;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.entities.gfx.TiledMapEntity;
import de.fhtrier.gdig.engine.entities.physics.PhysicsEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class Level extends PhysicsEntity {

	private GameFactory factory;

	private ImageEntity backgroundImage;
	private ImageEntity middlegroundImage;
	private TiledMap groundMap;
	private TiledMapEntity ground;
	
	private int currentPlayerId;

	public Level(int id, GameFactory factory) throws SlickException {
		super(id);

		this.currentPlayerId = -1;

		this.factory = factory;
		AssetMgr assets = factory.getAssetMgr();

		// Load Images
		Image tmp = assets.storeImage(Assets.LevelBackgroundImage,
				"backgrounds/background.png");
		assets.storeImage(Assets.LevelBackgroundImage,
				tmp.getScaledCopy(1350, 800));
		tmp = assets.storeImage(Assets.LevelMiddlegroundImage,
				"backgrounds/middleground.png");
		assets.storeImage(Assets.LevelMiddlegroundImage,
				tmp.getScaledCopy(1850, 800));
		this.groundMap = assets.storeTiledMap(Assets.LevelTileMap,
				"tiles/blocks.tmx");

		// gfx
		this.backgroundImage = factory.createImageEntity(
				Assets.LevelBackgroundImage, Assets.LevelBackgroundImage);
		this.backgroundImage.setVisible(true);
		this.backgroundImage.setActive(true);
		add(this.backgroundImage);

		this.middlegroundImage = factory.createImageEntity(
				Assets.LevelMiddlegroundImage, Assets.LevelMiddlegroundImage);
		this.middlegroundImage.setVisible(true);
		this.middlegroundImage.setActive(true);
		add(this.middlegroundImage);

		this.ground = factory.createTiledMapEntity(Assets.LevelTileMap,
				Assets.LevelTileMap);
		this.ground.setVisible(true);
		this.ground.setActive(true);
		add(this.ground);

		// physics
		setData(new float[] { 0, 0, 0, 0, 1, 1, 0 });

		// order
		setOrder(EntityOrder.Level);
	}

	@Override
	protected void postRender(Graphics graphicContext) {
		super.postRender(graphicContext);

		graphicContext.drawString("NetworkID: "
				+ NetworkComponent.getInstance().getNetworkId() + "\n"
				+ factory.size() + " entities", 20, 50);

		Entity e = this;
		graphicContext.drawString(
				"Level\n" +
				"ID: " + e.getId() + "\n" + " X: " + e.getData()[X] + "  Y: "
						+ e.getData()[Y] + "\n" + "OX: " + e.getData()[CENTER_X]
						+ " OY: " + e.getData()[CENTER_Y] + "\n" + "FX: "
						+ "SX: " + e.getData()[SCALE_X] + " SY: " + e.getData()[SCALE_Y]
						+ "\n" + "ROT: " + e.getData()[ROTATION], 20, 100);

		e = getCurrentPlayer();
		if (e != null) {
			graphicContext.drawString(
					"Player\n" +
					"ID: " + e.getId() + "\n" + " X: " + e.getData()[X]
							+ "  Y: " + e.getData()[Y] + "\n" + "OX: "
							+ e.getData()[CENTER_X] + " OY: " + e.getData()[CENTER_Y]
                        	+ "SX: " + e.getData()[SCALE_X]
							+ " SY: " + e.getData()[SCALE_Y] + "\n" + "ROT: "
							+ e.getData()[ROTATION], 20, 250);
		}
	}

	@Override
	public void update(int deltaInMillis) {

		super.update(deltaInMillis); // calculate physics

		if (isActive()) {
			focusOnPlayer();

			checkLevelBordersScrolling();

			parallaxScrollingBackground();
		}
	}

	/**
	 *  scrolls background layers relative to foreground
	 */
	private void parallaxScrollingBackground() {
		this.middlegroundImage.getData()[X] = -getData()[X] * 0.6f;
		this.middlegroundImage.getData()[Y] = -getData()[Y];
		this.backgroundImage.getData()[X] = -getData()[X] * 0.95f;
		this.backgroundImage.getData()[Y] = -getData()[Y];
	}

	/**
	 *  Ensures, that we don't scroll across level borders
	 *  TODO: doesn't work with scaling factor != 1
	 */
	private void checkLevelBordersScrolling() {

		// Left
		if (getData()[X] > 0) {
			getData()[X] = 0.0f;
			getVel()[X] = 0.0f;
		}
		// Top
		if (getData()[Y] > 0) {
			getData()[Y] = 0.0f;
			getVel()[Y] = 0.0f;
		}
		// Right
		if (getData()[X] < -this.groundMap.getWidth()
				* this.groundMap.getTileWidth() + JumpNRun.SCREENWIDTH) {
			getData()[X] = -this.groundMap.getWidth()
					* this.groundMap.getTileWidth() + JumpNRun.SCREENWIDTH;
			getVel()[X] = 0.0f;
		}
		// Bottom
		if (getData()[Y] < -this.groundMap.getHeight()
				* this.groundMap.getTileHeight() + JumpNRun.SCREENHEIGHT) {
			getData()[Y] = -this.groundMap.getHeight()
					* this.groundMap.getTileHeight() + JumpNRun.SCREENHEIGHT;
			getVel()[Y] = 0.0f;
		}
	}

	/**
	 *  keep our player in the middle of the screen
	 */
	private void focusOnPlayer() {
		Player player = getCurrentPlayer();
		if (player != null) {

			// Focus on Player
			getData()[X] = JumpNRun.SCREENWIDTH/2 - player.getData()[X];
			getData()[Y] = JumpNRun.SCREENHEIGHT/2 - player.getData()[Y];
		}
	}

	@Override
	public void handleInput(Input input) {
		if (isActive()) {

			// Left / Right
			if (!input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D)) {
				getVel()[X] = 0.0f;
			}
			if (input.isKeyDown(Input.KEY_A)) {
				getVel()[X] = 600.0f;
			}
			if (input.isKeyDown(Input.KEY_D)) {
				getVel()[X] = -600.0f;
			}

			// Up / Down
			if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) {
				getVel()[Y] = 0.0f;
			}

			if (input.isKeyDown(Input.KEY_W)) {
				getVel()[Y] = 600.0f;
			}

			if (input.isKeyDown(Input.KEY_S)) {
				getVel()[Y] = -600.0f;
			}

			// Zoom
			if (!input.isKeyDown(Input.KEY_R) && !input.isKeyDown(Input.KEY_F)) {
				getVel()[SCALE_X] = getVel()[SCALE_Y] = 0.0f;
			}

			if (input.isKeyDown(Input.KEY_R)) {
				getVel()[SCALE_X] = getVel()[SCALE_Y] = 1;
			}

			if (input.isKeyDown(Input.KEY_F)) {
				getVel()[SCALE_X] = getVel()[SCALE_Y] = -1;
			}

			// Rotation
			if (!input.isKeyDown(Input.KEY_Q) && !input.isKeyDown(Input.KEY_E)) {
				getVel()[ROTATION] = 0.0f;
			}

			if (input.isKeyDown(Input.KEY_Q)) {
				getVel()[ROTATION] = -15;
			}
			if (input.isKeyDown(Input.KEY_E)) {
				getVel()[ROTATION] = 15;
			}
		}
		super.handleInput(input);
	}

	public TiledMap getMap() {
		return this.groundMap;
	}

	public Player getCurrentPlayer() {
		return getPlayer(this.currentPlayerId);
	}

	public void setCurrentPlayer(int playerId) {
		if (playerId != currentPlayerId) {
			Player player = getCurrentPlayer();
			if (player != null) {
				player.setActive(false);
			}
			this.currentPlayerId = playerId;
			player = getCurrentPlayer();
			if (player != null) {
				player.setActive(true);
			}
		}
	}

	public Player getPlayer(int id) {
		if (id == -1) {
			return null;
		}

		Entity player = factory.getEntity(id);
		if (player instanceof Player) {
			return (Player) player;
		}
		throw new RuntimeException("id doesn't match requested entity type");
	}

	@Override
	public Entity add(Entity e) {
		Entity result = super.add(e);

		// tell player that he belongs to level
		if (e instanceof Player) {
			((Player) e).setLevel(this);
		}

		return result;
	}
}
