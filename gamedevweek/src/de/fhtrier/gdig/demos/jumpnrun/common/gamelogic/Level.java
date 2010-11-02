﻿package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameInputCommands;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Level1;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.TileLogic;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.graphics.entities.ImageEntity;
import de.fhtrier.gdig.engine.graphics.entities.TiledMapEntity;
import de.fhtrier.gdig.engine.input.InputController;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.entities.MoveableEntity;

public class Level extends MoveableEntity {

	public GameFactory factory;

	private TiledMap groundMap;
	private TiledMapEntity ground;
	public int firstLogicGID;

	private ArrayList<ArrayList<LogicPoint>> spawnPoints;

	private int currentPlayerId;

	private ArrayList<ArrayList<LogicPoint>> teleportExitPoints;

	private ArrayList<LogicPoint> doomsdayDevices;
	private ArrayList<LogicPoint> teleportAnimations;

	MoveableEntity layerBackgroundFar;
	MoveableEntity layerBackground;
	MoveableEntity layerForeground;

	private Random rd = new Random(System.currentTimeMillis());

	private AssetMgr assets;

	public Level(int id, GameFactory factory) throws SlickException {
		super(id, EntityType.LEVEL);

		this.currentPlayerId = -1;

		this.factory = factory;
		assets = new AssetMgr();

		// gfx
		loadBackgroundLayers();

		this.groundMap = assets.storeTiledMap(Assets.Level.TileMapId,
				Assets.Level.TileMapPath);
		this.ground = factory
				.createTiledMapEntity(Assets.Level.TileMapRenderOrder,
						Assets.Level.TileMapId, assets);
		this.ground.setVisible(true);
		this.ground.setActive(true);
		add(this.ground);

		// physics
		setData(new float[] { 0, 0, 0, 0, 1, 1, 0 });

		// network
		setUpdateStrategy(EntityUpdateStrategy.Local);

		// order
		setOrder(EntityOrder.Level);

		// setup
		setActive(true);
		setVisible(true);

		// HACK determine GID for logic layer
		TiledMap tiledMap = ground.getAssetMgr().getTiledMap(
				ground.getAssetId());
		for (int i = 0; i < tiledMap.getTileSetCount(); i++) {
			if (tiledMap.getTileSet(i).lastGID
					- tiledMap.getTileSet(i).firstGID == 127) {
				firstLogicGID = tiledMap.getTileSet(i).firstGID;
			}
		}

		calculateLogicPoints();
	}

	MoveableEntity createBackgroundLayer(int numTiles, int AssetId,
			String AssetPath) throws SlickException {

		// Background far
		MoveableEntity result = new MoveableEntity(AssetId, EntityType.HELPER);
		int xOffset = 0;

		for (int i = 0; i < numTiles; i++) {
			String strFile = (assets.getPathRelativeToAssetPath(AssetPath
					+ "_0" + (i + 1) + "." + Level1.FileExt));
			Image img = new Image(strFile);

			assets.storeImage(AssetId + i, img);

			ImageEntity e = factory.createImageEntity(AssetId, AssetId + i,
					assets);

			e.setVisible(true);
			e.getData()[Entity.SCALE_X] = Level1.ImageBackgroundScaling;
			e.getData()[Entity.SCALE_Y] = Level1.ImageBackgroundScaling;
			e.getData()[Entity.X] = xOffset;
			e.getData()[Entity.Y] = Settings.SCREENHEIGHT - img.getHeight()*Level1.ImageBackgroundScaling;
			xOffset += img.getWidth()*Level1.ImageBackgroundScaling;
			result.add(e);
		}

		result.setVisible(true);
		return result;
	}

	private void loadBackgroundLayers() throws SlickException {

		// Background far
		layerBackgroundFar = createBackgroundLayer(Level1.numBackgroundTiles,
				Level1.ImageBackgroundFarId, Level1.ImageBackgroundFarPath);
		layerBackgroundFar.setVisible(true);
		layerBackgroundFar.setOrder(Level1.ImageBackgroundFarRenderOrder);
//		add(layerBackgroundFar);

		// Background
		layerBackground = createBackgroundLayer(Level1.numBackgroundTiles,
				Level1.ImageBackgroundId, Level1.ImageBackgroundPath);
		layerBackground.setVisible(true);
		layerBackground.setOrder(Level1.ImageBackgroundRenderOrder);
//		add(layerBackground);

		// LayerForeground
		layerForeground = createBackgroundLayer(Level1.numBackgroundTiles,
				Level1.ImageForegroundId, Level1.ImageForegroundPath);
		layerForeground.setVisible(true);
		layerForeground.setOrder(Level1.ImageForegroundRenderOrder);
//		add(layerForeground);
	}

	private void placeTeleportAnimations() {

		for (LogicPoint lp : teleportAnimations) {
			int teleporterid = factory.createEntity(EntityType.TELEPORTER);
			Entity teleporter = factory.getEntity(teleporterid);
			teleporter.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
			add(teleporter);
			teleporter.getData()[Entity.X] = lp.x; // -teleporter.Assets().getImage(0).getWidth();
			teleporter.getData()[Entity.Y] = lp.y; // -teleporter.Assets().getImage(0).getHeight();
		}
	}

	private void placeDoomsdayDevices() {
		for (LogicPoint lp : doomsdayDevices) {
			int domsDayDeviceID = factory
					.createEntity(EntityType.DOOMSDAYDEVICE);
			DoomsdayDevice doomesdaydevice = (DoomsdayDevice) factory
					.getEntity(domsDayDeviceID);
			add(doomesdaydevice);
			doomesdaydevice.setActive(true);
			doomesdaydevice
					.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
			doomesdaydevice.getData()[X] = lp.x;
			doomesdaydevice.getData()[Y] = lp.y;
			doomesdaydevice.initServer();
		}
	}

	private void calculateLogicPoints() {

		spawnPoints = new ArrayList<ArrayList<LogicPoint>>();
		teleportExitPoints = new ArrayList<ArrayList<LogicPoint>>();
		doomsdayDevices = new ArrayList<LogicPoint>();
		teleportAnimations = new ArrayList<LogicPoint>();

		for (int i = TileLogic.minSpawnId; i <= TileLogic.maxSpawnId; i++) {
			spawnPoints.add(new ArrayList<LogicPoint>());
		}

		for (int i = TileLogic.minTeleportExitId; i <= TileLogic.maxTeleportExitId; i++) {
			teleportExitPoints.add(new ArrayList<LogicPoint>());
		}

		TiledMap tiledMap = ground.getAssetMgr().getTiledMap(
				ground.getAssetId());

		for (int x = 0; x < tiledMap.getWidth(); x++) {
			for (int y = 0; y < tiledMap.getHeight(); y++) {
				int tileId = tiledMap.getTileId(x, y,
						Constants.Level.logicLayer);
				if (tileId == TileLogic.nullId) {
					continue;
				}
				tileId -= firstLogicGID;
				++tileId;
				// is a spawnpoint
				if (tileId >= TileLogic.minSpawnId
						&& tileId <= TileLogic.maxSpawnId) {
					if (Constants.Debug.tileMapLogicDebug) {
						Log.debug("SpawnPoint " + tileId + " at: " + x + ", "
								+ y);
					}
					spawnPoints.get(tileId - TileLogic.minSpawnId).add(
							new LogicPoint(tileId, x * tiledMap.getTileWidth(),
									y * tiledMap.getTileHeight()));
				}

				// is teleport entry
				if (tileId >= TileLogic.minTeleportEntryId
						&& tileId <= TileLogic.maxTeleportEntryId) {
					if (Constants.Debug.tileMapLogicDebug) {
						Log.debug("Teleporter entry " + tileId + " at: " + x
								+ ", " + y);
					}
				}

				// is a teleporterexit
				if (tileId >= TileLogic.minTeleportExitId
						&& tileId <= TileLogic.maxTeleportExitId) {
					if (Constants.Debug.tileMapLogicDebug) {
						Log.debug("Teleporter exit " + tileId + " at: " + x
								+ ", " + y);
					}
					teleportExitPoints
							.get(tileId - TileLogic.minTeleportExitId).add(
									new LogicPoint(tileId, x
											* tiledMap.getTileWidth(), y
											* tiledMap.getTileHeight()));
				}

				// is doomsday Device
				if (tileId == TileLogic.doomsDayDeviceId) {
					if (Constants.Debug.tileMapLogicDebug) {
						Log.debug("Doomsday device " + tileId + " at: " + x
								+ ", " + y);
					}

					doomsdayDevices.add(new LogicPoint(tileId, x
							* tiledMap.getTileWidth(), y
							* tiledMap.getTileHeight()));
				}

				// is teleport animation Device
				if (tileId == TileLogic.teleporterDeviceId) {
					if (Constants.Debug.tileMapLogicDebug) {
						Log.debug("Teleporter animation " + tileId + " at: "
								+ x + ", " + y);
					}

					teleportAnimations.add(new LogicPoint(tileId, x
							* tiledMap.getTileWidth(), y
							* tiledMap.getTileHeight()));
				}
			}
		}
	}

	public ArrayList<LogicPoint> getSpawnPoints(int id) {
		return spawnPoints.get(id - 1);
	}

	public LogicPoint getRandomSpawnPoint(int id) {
		ArrayList<LogicPoint> sp = getSpawnPoints(id);
		return sp.get(rd.nextInt(sp.size()));
	}

	public LogicPoint getRandomSpawnPoint() {
		ArrayList<LogicPoint> sp = getSpawnPoints(rd
				.nextInt(spawnPoints.size()) + 1);
		return sp.get(rd.nextInt(sp.size()));
	}

	public ArrayList<LogicPoint> getTeleporterExitPoints(int id) {
		return teleportExitPoints.get(id - 1);
	}

	public LogicPoint getRandomTeleporterExitPoint(int id) {
		ArrayList<LogicPoint> sp = getTeleporterExitPoints(id);
		return sp.get(rd.nextInt(sp.size()));
	}

	public LogicPoint getRandomTeleporterExitPoint() {
		ArrayList<LogicPoint> sp = getTeleporterExitPoints(rd
				.nextInt(teleportExitPoints.size()) + 1);
		return sp.get(rd.nextInt(sp.size()));
	}

	@Override
	protected void preRender(final Graphics graphicContext) {

		layerBackgroundFar.render(graphicContext, null);
		layerForeground.render(graphicContext, null);
		layerBackground.render(graphicContext, null);
		
		super.preRender(graphicContext);
	}
		
	@Override
	protected void postRender(Graphics graphicContext) {
		if (isVisible()) {
			ground.getAssetMgr().getTiledMap(ground.getAssetId())
					.render(0, 0, 2);
		}
		super.postRender(graphicContext);

		graphicContext.setColor(Constants.Debug.overlayColor);
		graphicContext.drawString("Team 1: " + Team.team1.getKills()
				+ "\nTeam 2: " + Team.team2.getKills(), 200, 20);

		if (Constants.Debug.showDebugOverlay) {
			graphicContext.setColor(Constants.Debug.overlayColor);
			graphicContext.drawString("NetworkID: "
					+ NetworkComponent.getInstance().getNetworkId() + "\n"
					+ factory.size() + " entities", 20, 50);

			Entity e = this;

			graphicContext.drawString(
					"Level\n" + "ID: " + e.getId() + "\n" + " X: "
							+ e.getData()[X] + "  Y: " + e.getData()[Y] + "\n"
							+ "OX: " + e.getData()[CENTER_X] + " OY: "
							+ e.getData()[CENTER_Y] + "\n" + "FX: " + "SX: "
							+ e.getData()[SCALE_X] + " SY: "
							+ e.getData()[SCALE_Y] + "\n" + "ROT: "
							+ e.getData()[ROTATION], 20, 100);

			e = getCurrentPlayer();
			if (e != null) {
				graphicContext.drawString(
						"Player\n" + "ID: "
								+ e.getId()
								+ "\n"
								+ " X: "
								+ e.getData()[X]
								+ "  Y: "
								+ e.getData()[Y]
								+ "\n"
								+ "OX: "
								+ e.getData()[CENTER_X]
								+ " OY: "
								+ e.getData()[CENTER_Y]
								+ "SX: "
								+ e.getData()[SCALE_X]
								+ " SY: "
								+ e.getData()[SCALE_Y]
								+ "\n"
								+ "ROT: "
								+ e.getData()[ROTATION]
								+ "\n"
								+ "STATE: "
								+ ((Player) e).getCurrentPlayerAsset()
										.toString() + "\n" + "IsOnGround "
								+ ((Player) e).isOnGround() + "\n" + "SPEED ("
								+ ((Player) e).getVel()[Entity.X] + ", "
								+ ((Player) e).getVel()[Entity.Y] + ")", 20,
						250);
			}
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
	 * scrolls background layers relative to foreground
	 */
	private void parallaxScrollingBackground() {

		float scaleFactor = getData()[SCALE_X] * getData()[SCALE_X];
		
		layerBackgroundFar.getData()[X] = getData()[X]
				* Level1.ImageBackgroundFarParallaxFactor * scaleFactor;
		
		layerBackground.getData()[X] = getData()[X]
				* Level1.ImageBackgroundParallaxFactor * scaleFactor;
		
		layerForeground.getData()[X] = getData()[X]
				* Level1.ImageForegroundParallaxFactor * scaleFactor;
	}

	/**
	 * Ensures, that we don't scroll across level borders
	 */
	// TODO: doesn't work with scaling factor != 1
	private void checkLevelBordersScrolling() {
//
//		// Left
//		if (getData()[X] > 0) {
//			getData()[X] = 0.0f;
//			getVel()[X] = 0.0f;
//		}
//		// Top
//		if (getData()[Y] > 0) {
//			getData()[Y] = 0.0f;
//			getVel()[Y] = 0.0f;
//		}
//		// Right
//		if (getData()[X] < -this.groundMap.getWidth()
//				* this.groundMap.getTileWidth() + Settings.SCREENWIDTH) {
//			getData()[X] = -this.groundMap.getWidth()
//					* this.groundMap.getTileWidth() + Settings.SCREENWIDTH;
//			getVel()[X] = 0.0f;
//		}
//		// Bottom
//		if (getData()[Y] < -this.groundMap.getHeight()
//
//		* this.groundMap.getTileHeight() + Settings.SCREENHEIGHT) {
//			getData()[Y] = -this.groundMap.getHeight()
//					* this.groundMap.getTileHeight() + Settings.SCREENHEIGHT;
//			getVel()[Y] = 0.0f;
//		}
	}

	/**
	 * keep our player in the middle of the screen
	 */
	private void focusOnPlayer() {
		Player player = getCurrentPlayer();
		if (player != null) {

			// Focus on Player
			getData()[X] = Settings.SCREENWIDTH / 2.0f - player.getData()[X];
			getData()[Y] = Settings.SCREENHEIGHT / 2.0f - player.getData()[Y];
			
			getData()[CENTER_X] = player.getData()[X] + player.getData()[CENTER_X];
			getData()[CENTER_Y] = player.getData()[Y] + player.getData()[CENTER_Y];
		}
	}

	@Override
	public void handleInput(InputController<?> _input) {
		super.handleInput(_input);

		if (isActive()) {

			GameInputController input = (GameInputController)_input;
			
			// Left / Right
			if (!input.isKeyDown(GameInputCommands.SCROLLLEFT) &&
					!input.isKeyDown(GameInputCommands.SCROLLRIGHT)) {
				getVel()[X] = 0.0f;
			}
			if (input.isKeyDown(GameInputCommands.SCROLLRIGHT)) {
				getVel()[X] = 600.0f;
			}
			if (input.isKeyDown(GameInputCommands.SCROLLLEFT)) {
				getVel()[X] = -600.0f;
			}

			// Up / Down
			if (!input.isKeyDown(GameInputCommands.SCROLLUP) &&
					!input.isKeyDown(GameInputCommands.SCROLLDOWN)) {
				getVel()[Y] = 0.0f;
			}

			if (input.isKeyDown(GameInputCommands.SCROLLDOWN)) {
				getVel()[Y] = 600.0f;
			}

			if (input.isKeyDown(GameInputCommands.SCROLLUP)) {
				getVel()[Y] = -600.0f;
			}

			// Zoom
			if (!input.isKeyDown(GameInputCommands.ZOOMIN) &&
					!input.isKeyDown(GameInputCommands.ZOOMOUT)) {
				getVel()[SCALE_X] = getVel()[SCALE_Y] = 0.0f;
			}

			if (input.isKeyDown(GameInputCommands.ZOOMIN)) {
				getVel()[SCALE_X] = getVel()[SCALE_Y] = 1;
			}

			if (input.isKeyDown(GameInputCommands.ZOOMOUT)) {
				getVel()[SCALE_X] = getVel()[SCALE_Y] = -1;
			}

			// Rotation
			if (!input.isKeyDown(GameInputCommands.ROTATELEFT) &&
					!input.isKeyDown(GameInputCommands.ROTATERIGHT)) {
				getVel()[ROTATION] = 0.0f;
			}

			if (input.isKeyDown(GameInputCommands.ROTATELEFT)) {
				getVel()[ROTATION] = -15;
			}
			if (input.isKeyDown(GameInputCommands.ROTATERIGHT)) {
				getVel()[ROTATION] = 15;
			}
		}
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
				player.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
			}

			this.currentPlayerId = playerId;

			player = getPlayer(currentPlayerId);
			if (player != null) {
				player.setUpdateStrategy(EntityUpdateStrategy.ClientToServer);
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
		} else if (player == null)
		{
			return null;
		}
		
		throw new RuntimeException("id doesn't match requested entity type");
	}

	@Override
	public Entity add(Entity e) {
		Entity result = super.add(e);

		// tell player that he belongs to level
		if (e instanceof LevelCollidableEntity) {
			((LevelCollidableEntity) e).setLevel(this);
		}
		if (e instanceof DoomsdayDevice) {
			((DoomsdayDevice) e).setLevel(this);
		}

		return result;
	}

	/**
	 * Returns the size in pixel
	 * 
	 * @return
	 */
	public int getWidth() {
		return getMap().getWidth() * getMap().getTileWidth();
	}

	/**
	 * Returns the size in pixel
	 * 
	 * @return
	 */
	public int getHeight() {
		return getMap().getHeight() * getMap().getTileHeight();
	}

	public AssetMgr getAssets() {
		return assets;
	}

	/**
	 * This is to Inelize Entetys in the Level. only the Server do this.
	 * 
	 * @param isServer
	 *            TODO
	 */
	public void init(boolean isServer) {
		placeDoomsdayDevices();
		placeTeleportAnimations();
	}
}
