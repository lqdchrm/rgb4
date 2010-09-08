package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.JumpNRun;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;

public abstract class PlayingState extends BasicGameState implements
		INetworkCommandListener
{

	private AssetMgr assets;
	private GameFactory factory;
	private int levelId;
	private static Image frameBuffer;
		
	public abstract void cleanup(GameContainer container, StateBasedGame game);

	public GameFactory getFactory()
	{
		return this.factory;
	}

	@Override
	public int getID()
	{
		return GameStates.PLAYING;
	}

	public Level getLevel()
	{
		final Entity level = this.factory.getEntity(this.levelId);
		if (level instanceof Level)
		{
			return (Level) level;
		}
		return null;
	}

	@Override
	public void init(final GameContainer arg0, final StateBasedGame arg1)
			throws SlickException
	{

		// create assetmgr
		this.assets = new AssetMgr();
		this.assets.setAssetPathPrefix("content/jumpnrun/test/");
		this.assets.setAssetFallbackPathPrefix("content/jumpnrun/default/");

		// Factory
		this.factory = new GameFactory(this.assets);

		// Level
		this.levelId = factory.createEntity(EntityType.LEVEL);
		
		// FrameBuffer
		frameBuffer = new Image(JumpNRun.SCREENWIDTH, JumpNRun.SCREENHEIGHT);
	}

	@Override
	public void render(final GameContainer container,
			final StateBasedGame game, final Graphics graphicContext)
			throws SlickException
	{		
		Level level = getLevel();
		
		if (level != null)
		{
			if (Constants.Debug.shadersActive)
			{
				level.render(frameBuffer.getGraphics(), frameBuffer);
				graphicContext.drawImage(frameBuffer, 0, 0);
			}
			else
			{
				level.render(graphicContext, null);
			}
		}
	}
	
	public abstract void notify(INetworkCommand cmd);

	@Override
	public void update(final GameContainer container,
			final StateBasedGame game, final int deltaInMillis)
			throws SlickException
	{
		final Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_F1))
		{
			container.setPaused(true);
			try
			{
				container.setFullscreen(!container.isFullscreen());
			} catch (final SlickException e)
			{

			}
			container.setVSync(true);
			container.setSmoothDeltas(true);
			container.setMaximumLogicUpdateInterval(17);
			container.setPaused(false);
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE))
		{
			onExitKey(container, game);
		}

		final Level level = this.getLevel();

		if (level != null)
		{
			level.handleInput(input);
			level.update(deltaInMillis);
			
			// Sorgt dafür dass 1. Collisionnen neu berechnet werden, 2. Zeile
			// Den Objekten gesagt wird die Kollision zu behandeln.
			CollisionManager.update();
			level.handleCollisions();
		}
	}

	public abstract void onExitKey(GameContainer container, StateBasedGame game);

}
