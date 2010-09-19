package de.fhtrier.gdig.demos.jumpnrun.common.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Level;
import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputCommands;
import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.physics.CollisionManager;


public abstract class PlayingState extends BasicGameState implements
		INetworkCommandListener {
	protected GameFactory factory;
	protected int levelId;
	
	/**
	 * has to be something like winningKills_Deathmatch in Constants.GamePlayConstants
	 */
	public static int gameType;
	// TODO Only activate for postprocessing
	//private static Image frameBuffer;
		
	public abstract void cleanup(GameContainer container, StateBasedGame game);

	public GameFactory getFactory() {
		return this.factory;
	}

	@Override
	public int getID() {
		return GameStates.PLAYING;
	}

	public Level getLevel() {
		final Entity level = this.factory.getEntity(this.levelId);
		if (level instanceof Level) {
			return (Level) level;
		}
		return null;
	}

	@Override
	public void init(final GameContainer arg0, final StateBasedGame arg1)
			throws SlickException {
		gameType = Constants.GameTypes.teamDeathMatch; // TODO: change it!

		GameInputController.init(arg0.getInput());
		GameInputController.getInstance().initKeyboard();
		GameInputController.getInstance().initGamePad();
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		
		// Factory
		this.factory = new GameFactory();
		
		// Level
		this.levelId = factory.createEntity(EntityType.LEVEL);

		// TODO FrameBuffer - only activate for postprocessing
		//frameBuffer = new Image(RGB4.SCREENWIDTH, RGB4.SCREENHEIGHT);
	}

	@Override
	public void render(final GameContainer container,
			final StateBasedGame game, final Graphics graphicContext)
			throws SlickException
	{
		Level level = getLevel();
		
		if (level != null)
		{
			if (!Constants.Debug.shadersActive)
			{
				// TODO Only use draw to texture if post processing is
				// implemented in Level.java
				// frameBuffer.getGraphics().clear();
				//level.render(frameBuffer.getGraphics(), frameBuffer);
				//graphicContext.drawImage(frameBuffer, 0, 0);
				level.render(graphicContext, null);
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
			throws SlickException {

		final GameInputController input = GameInputController.getInstance();
		input.update();
		
		if (input.isKeyPressed(GameInputCommands.FULLSCREEN)) {
			container.setPaused(true);
			try {
				container.setFullscreen(!container.isFullscreen());
			} catch (final SlickException e) {
				Log.error(e);
			}
			container.setPaused(false);
		}

		if (input.isKeyPressed(GameInputCommands.BACK)) {
			onExitKey(container, game);
		}

		final Level level = this.getLevel();

		if (level != null) {
			level.handleInput(input);
			level.update(deltaInMillis);
			
			
			// Sorgt dafür dass 1. Collisionnen neu berechnet werden, 2. Zeile
			// Den Objekten gesagt wird die Kollision zu behandeln.
			CollisionManager.update();
			level.handleCollisions();
		
			EventManager.update();
		}
	}

	public abstract void onExitKey(GameContainer container, StateBasedGame game);

}
