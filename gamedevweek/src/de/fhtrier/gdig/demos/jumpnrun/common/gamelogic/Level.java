package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
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

	private int currentPlayerId;

	public Level(int id, GameFactory factory) throws SlickException
	{
		super(id, EntityType.LEVEL);

		this.currentPlayerId = -1;

		this.factory = factory;
		AssetMgr assets = factory.getAssetMgr();

		// Load Images
		Image tmp = assets.storeImage(Assets.LevelBackgroundImageId,
				"backgrounds/background.png");
		assets.storeImage(Assets.LevelBackgroundImageId,
				tmp.getScaledCopy(1350, 800));
		tmp = assets.storeImage(Assets.LevelMiddlegroundImageId,
				"backgrounds/middleground.png");
		assets.storeImage(Assets.LevelMiddlegroundImageId,
				tmp.getScaledCopy(1850, 800));
		this.groundMap = assets.storeTiledMap(Assets.LevelTileMapId,
				"tiles/blocks.tmx");

		// gfx
		this.backgroundImage = factory.createImageEntity(
				Assets.LevelBackgroundImageId, Assets.LevelBackgroundImageId);
		this.backgroundImage.setVisible(true);
		add(this.backgroundImage);

		this.middlegroundImage = factory.createImageEntity(
				Assets.LevelMiddlegroundImageId,
				Assets.LevelMiddlegroundImageId);
		this.middlegroundImage.setVisible(true);
		add(this.middlegroundImage);

		this.ground = factory.createTiledMapEntity(Assets.LevelTileMapId,
				Assets.LevelTileMapId);
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
	}

	@Override
	protected void postRender(Graphics graphicContext)
	{
		super.postRender(graphicContext);

		graphicContext.setColor(Constants.Debug.overlayColor);
		graphicContext.drawString("NetworkID: "
				+ NetworkComponent.getInstance().getNetworkId() + "\n"
				+ factory.size() + " entities", 20, 50);

		Entity e = this;
		graphicContext.drawString("Level\n" + "ID: " + e.getId() + "\n"
				+ " X: " + e.getData()[X] + "  Y: " + e.getData()[Y] + "\n"
				+ "OX: " + e.getData()[CENTER_X] + " OY: "
				+ e.getData()[CENTER_Y] + "\n" + "FX: " + "SX: "
				+ e.getData()[SCALE_X] + " SY: " + e.getData()[SCALE_Y] + "\n"
				+ "ROT: " + e.getData()[ROTATION], 20, 100);

		e = getCurrentPlayer();
		if (e != null)
		{
			graphicContext.drawString(
					"Player\n" + "ID: " + e.getId() + "\n" + " X: "
							+ e.getData()[X] + "  Y: " + e.getData()[Y] + "\n"
							+ "OX: " + e.getData()[CENTER_X] + " OY: "
							+ e.getData()[CENTER_Y] + "SX: "
							+ e.getData()[SCALE_X] + " SY: "
							+ e.getData()[SCALE_Y] + "\n" + "ROT: "
							+ e.getData()[ROTATION] + "\n" + "STATE: "
							+ ((Player) e).getState().toString(), 20, 250);
		}
	}

	@Override
	public void update(int deltaInMillis)
	{

		super.update(deltaInMillis); // calculate physics

		if (isActive())
		{
			focusOnPlayer();

			checkLevelBordersScrolling();

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
			}
			this.currentPlayerId = playerId;
			player = getCurrentPlayer();
			if (player != null)
			{
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
		Entity result = super.add(e);

		// tell player that he belongs to level
		if (e instanceof LevelCollidableEntity)
		{
			((LevelCollidableEntity) e).setLevel(this);
		} else if (e instanceof DomsDayDeviceBigExplosion)
		{
			((DomsDayDeviceBigExplosion) e).setLevel(this);
		}

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

	public void serverInit()
	{
		// TODO Auto-generated method stub

		int domsDayDeviceID = factory.createEntity(EntityType.DOMSDAYDEVICE);
		Entity doomesdaydevice = factory.getEntity(domsDayDeviceID);
		add(doomesdaydevice);
		doomesdaydevice.setActive(true);
		doomesdaydevice.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);

		DoCreateEntity command = new DoCreateEntity(domsDayDeviceID,
				EntityType.DOMSDAYDEVICE);
		NetworkComponent.getInstance().sendCommand(command);
	}
}
