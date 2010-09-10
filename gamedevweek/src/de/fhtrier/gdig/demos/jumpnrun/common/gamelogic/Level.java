package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.graphics.entities.ImageEntity;
import de.fhtrier.gdig.engine.graphics.entities.TiledMapEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.entities.MoveableEntity;

public class Level extends MoveableEntity
{

	public GameFactory factory;

	private ImageEntity backgroundImage;
	private ImageEntity middlegroundImage;
	private TiledMap groundMap;
	private TiledMapEntity ground;
	public int firstLogicGID;

	private ArrayList<ArrayList<SpawnPoint>> spawnPoints;

	private int currentPlayerId;

	private ArrayList<ArrayList<SpawnPoint>> teleportExitPoints;

	private Random rd = new Random(System.currentTimeMillis());

	private AssetMgr assets;

	public Level(int id, GameFactory factory) throws SlickException
	{
		super(id, EntityType.LEVEL);
		level = this;
		this.currentPlayerId = -1;

		this.factory = factory;
		assets = new AssetMgr();

		// Load Images
		Image tmp = assets.storeImage(Assets.Level.BackgroundImageId,
				"backgrounds/background.png");
		assets.storeImage(Assets.Level.BackgroundImageId,
				tmp.getScaledCopy(1350, 800));
		tmp = assets.storeImage(Assets.Level.MiddlegroundImageId,
				"backgrounds/middleground.png");
		assets.storeImage(Assets.Level.MiddlegroundImageId,
				tmp.getScaledCopy(1850, 800));
		this.groundMap = assets.storeTiledMap(Assets.Level.TileMapId,
				"tiles/blocks.tmx");

		// gfx
		this.backgroundImage = factory.createImageEntity(
				Assets.Level.BackgroundImageId, Assets.Level.BackgroundImageId,
				assets);
		this.backgroundImage.setVisible(true);
		add(this.backgroundImage);

		this.middlegroundImage = factory.createImageEntity(

		Assets.Level.MiddlegroundImageId, Assets.Level.MiddlegroundImageId,
				assets);
		this.middlegroundImage.setVisible(true);
		add(this.middlegroundImage);

		this.ground = factory.createTiledMapEntity(Assets.Level.TileMapId,
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
		TiledMap tiledMap = ground.Assets().getTiledMap(ground.getAssetId());
		for (int i = 0; i < tiledMap.getTileSetCount(); i++)
		{
			if (tiledMap.getTileSet(i).lastGID
					- tiledMap.getTileSet(i).firstGID == 127)
			{
				firstLogicGID = tiledMap.getTileSet(i).firstGID;
			}
		}

		calculateSpawnpoints();
		calculateTeleportExits();
	}

	private void calculateSpawnpoints()
	{

		spawnPoints = new ArrayList<ArrayList<SpawnPoint>>();

		for (int i = 0; i < 32; i++)
		{
			spawnPoints.add(new ArrayList<SpawnPoint>());
		}

		TiledMap tiledMap = ground.Assets().getTiledMap(ground.getAssetId());

		for (int x = 0; x < tiledMap.getWidth(); x++)
		{
			for (int y = 0; y < tiledMap.getHeight(); y++)
			{
				int tileId = tiledMap.getTileId(x, y,
						Constants.Level.logicLayer);
				if (tileId == 0)
				{
					continue;
				}
				tileId -= firstLogicGID;
				++tileId;
				// is a spawnpoint
				if (tileId < 32)
				{
					spawnPoints.get(tileId - 1).add(
							new SpawnPoint(tileId, x * tiledMap.getTileWidth(),
									y * tiledMap.getTileHeight()));
				}
			}
		}
	}

	private void calculateTeleportExits()
	{

		teleportExitPoints = new ArrayList<ArrayList<SpawnPoint>>();

		for (int i = 0; i < 32; i++)
		{
			teleportExitPoints.add(new ArrayList<SpawnPoint>());
		}

		TiledMap tiledMap = ground.Assets().getTiledMap(ground.getAssetId());

		for (int x = 0; x < tiledMap.getWidth(); x++)
		{
			for (int y = 0; y < tiledMap.getHeight(); y++)
			{
				int tileId = tiledMap.getTileId(x, y,
						Constants.Level.logicLayer);
				if (tileId == 0)
				{
					continue;
				}
				tileId -= firstLogicGID;
				++tileId;
				// is a teleporterexit
				if (tileId > 64 && tileId < 96)
				{
					teleportExitPoints.get(tileId - 65).add(
							new SpawnPoint(tileId, x * tiledMap.getTileWidth(),
									y * tiledMap.getTileHeight()));
				}
			}
		}
	}

	public ArrayList<SpawnPoint> getSpawnPoints(int id)
	{
		return spawnPoints.get(id - 1);
	}

	public SpawnPoint getRandomSpawnPoint(int id)
	{
		ArrayList<SpawnPoint> sp = getSpawnPoints(id);
		return sp.get(rd.nextInt(sp.size()));
	}

	public SpawnPoint getRandomSpawnPoint()
	{
		ArrayList<SpawnPoint> sp = getSpawnPoints(rd
				.nextInt(spawnPoints.size()));
		return sp.get(rd.nextInt(sp.size()));
	}

	public ArrayList<SpawnPoint> getTeleporterExitPoints(int id)
	{
		return teleportExitPoints.get(id - 1);
	}

	public SpawnPoint getRandomTeleporterExitPoint(int id)
	{
		ArrayList<SpawnPoint> sp = getTeleporterExitPoints(id);
		return sp.get(rd.nextInt(sp.size()));
	}

	public SpawnPoint getRandomTeleporterExitPoint()
	{
		ArrayList<SpawnPoint> sp = getTeleporterExitPoints(rd
				.nextInt(teleportExitPoints.size()));
		return sp.get(rd.nextInt(sp.size()));
	}

	@Override
	protected void postRender(Graphics graphicContext)
	{
		if (isVisible())
		{
			ground.Assets().getTiledMap(ground.getAssetId()).render(0, 0, 2);
		}
		super.postRender(graphicContext);

		if (Constants.Debug.showDebugOverlay)
		{
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
			if (e != null)
			{
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
	public void update(int deltaInMillis)
	{

		super.update(deltaInMillis); // calculate physics

		if (isActive())
		{
			focusOnPlayer();

			// checkLevelBordersScrolling();

			parallaxScrollingBackground();
		}
	}

	/**
	 * scrolls background layers relative to foreground
	 */
	private void parallaxScrollingBackground()
	{
		this.middlegroundImage.getData()[X] = -getData()[X] * 0.6f;
		this.middlegroundImage.getData()[Y] = -getData()[Y];
		this.backgroundImage.getData()[X] = -getData()[X] * 0.95f;
		this.backgroundImage.getData()[Y] = -getData()[Y];
	}

	/**
	 * Ensures, that we don't scroll across level borders
	 */
	// TODO: doesn't work with scaling factor != 1
	private void checkLevelBordersScrolling()
	{

		// Left
		if (getData()[X] > 0)
		{
			getData()[X] = 0.0f;
			getVel()[X] = 0.0f;
		}
		// Top
		if (getData()[Y] > 0)
		{
			getData()[Y] = 0.0f;
			getVel()[Y] = 0.0f;
		}
		// Right
		if (getData()[X] < -this.groundMap.getWidth()

		* this.groundMap.getTileWidth() + Settings.SCREENWIDTH)
		{
			getData()[X] = -this.groundMap.getWidth()
					* this.groundMap.getTileWidth() + Settings.SCREENHEIGHT;
			getVel()[X] = 0.0f;
		}
		// Bottom
		if (getData()[Y] < -this.groundMap.getHeight()

		* this.groundMap.getTileHeight() + Settings.SCREENWIDTH)
		{
			getData()[Y] = -this.groundMap.getHeight()
					* this.groundMap.getTileHeight() + Settings.SCREENHEIGHT;
			getVel()[Y] = 0.0f;
		}
	}

	/**
	 * keep our player in the middle of the screen
	 */
	private void focusOnPlayer()
	{
		Player player = getCurrentPlayer();
		if (player != null)
		{

			// Focus on Player
			getData()[X] = Settings.SCREENWIDTH / 2 - player.getData()[X];
			getData()[Y] = Settings.SCREENHEIGHT / 2 - player.getData()[Y];
		}
	}

	@Override
	public void handleInput(Input input)
	{
		if (isActive())
		{

			// Left / Right
			if (!input.isKeyDown(Input.KEY_A) && !input.isKeyDown(Input.KEY_D))
			{
				getVel()[X] = 0.0f;
			}
			if (input.isKeyDown(Input.KEY_A))
			{
				getVel()[X] = 600.0f;
			}
			if (input.isKeyDown(Input.KEY_D))
			{
				getVel()[X] = -600.0f;
			}

			// Up / Down
			if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S))
			{
				getVel()[Y] = 0.0f;
			}

			if (input.isKeyDown(Input.KEY_W))
			{
				getVel()[Y] = 600.0f;
			}

			if (input.isKeyDown(Input.KEY_S))
			{
				getVel()[Y] = -600.0f;
			}

			// Zoom
			if (!input.isKeyDown(Input.KEY_R) && !input.isKeyDown(Input.KEY_F))
			{
				getVel()[SCALE_X] = getVel()[SCALE_Y] = 0.0f;
			}

			if (input.isKeyDown(Input.KEY_R))
			{
				getVel()[SCALE_X] = getVel()[SCALE_Y] = 1;
			}

			if (input.isKeyDown(Input.KEY_F))
			{
				getVel()[SCALE_X] = getVel()[SCALE_Y] = -1;
			}

			// Rotation
			if (!input.isKeyDown(Input.KEY_Q) && !input.isKeyDown(Input.KEY_E))
			{
				getVel()[ROTATION] = 0.0f;
			}

			if (input.isKeyDown(Input.KEY_Q))
			{
				getVel()[ROTATION] = -15;
			}
			if (input.isKeyDown(Input.KEY_E))
			{
				getVel()[ROTATION] = 15;
			}
		}
		super.handleInput(input);
	}

	public TiledMap getMap()
	{
		return this.groundMap;
	}

	public Player getCurrentPlayer()
	{
		return getPlayer(this.currentPlayerId);
	}

	public void setCurrentPlayer(int playerId)
	{
		if (playerId != currentPlayerId)
		{
			Player player = getCurrentPlayer();
			if (player != null)
			{
				player.setActive(false);
				player.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
			}

			this.currentPlayerId = playerId;

			player = getPlayer(currentPlayerId);
			if (player != null)
			{
				player.setUpdateStrategy(EntityUpdateStrategy.ClientToServer);
				player.setActive(true);
			}
		}
	}

	public Player getPlayer(int id)
	{
		if (id == -1)
		{
			return null;
		}

		Entity player = factory.getEntity(id);
		if (player instanceof Player)
		{
			return (Player) player;
		}
		throw new RuntimeException("id doesn't match requested entity type");
	}

	@Override
	public Entity add(Entity e)
	{
		e.setLevel(this);
		Entity result = super.add(e);

		return result;
	}

	/**
	 * Returns the size in pixel
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return getMap().getWidth() * getMap().getTileWidth();
	}

	/**
	 * Returns the size in pixel
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return getMap().getHeight() * getMap().getTileHeight();
	}

	public AssetMgr getAssets()
	{
		return assets;
	}

	/**
	 * This is to Inelize Entetys in the Level. only the Server do this.
	 */
	public void serverInit()
	{
		// TODO Wie kann ich mit der Factory DoomsdayDevices erstellen mit
		// assetfactory und allem drum und dran.
		int domsDayDeviceID = factory.createEntity(EntityType.DOOMSDAYDEVICE);
		Entity doomesdaydevice = factory.getEntity(domsDayDeviceID);
		add(doomesdaydevice);
		doomesdaydevice.setActive(true);
		doomesdaydevice.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
		doomesdaydevice.getData()[X] = level.getWidth() >> 1;
		doomesdaydevice.getData()[Y] = level.getHeight() >> 1;

		DoCreateEntity command = new DoCreateEntity(domsDayDeviceID,
				EntityType.DOOMSDAYDEVICE);
		NetworkComponent.getInstance().sendCommand(command);
	}
}
