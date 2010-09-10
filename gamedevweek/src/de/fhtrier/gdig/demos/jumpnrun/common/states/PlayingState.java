package de.fhtrier.gdig.demos.jumpnrun.common.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Level;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.demos.jumpnrun.server.states.ServerPlayingState;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.physics.CollisionManager;

public abstract class PlayingState extends BasicGameState implements
		INetworkCommandListener {
	protected GameFactory factory;
	protected int levelId;
	private static Image frameBuffer;

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

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);
		// <<<<<<< HEAD
		//
		// // create assetmgr
		// this.assets = new AssetMgr();
		// this.assets.setAssetPathPrefix(Assets.AssetManagerPath);
		// this.assets.setAssetFallbackPathPrefix(Assets.AssetManagerFallbackPath);
		//
		// =======
		//
		// >>>>>>> roessgro/master
		// Factory
		this.factory = new GameFactory();

		// Level
		this.levelId = factory.createEntity(EntityType.LEVEL);

		
		// FrameBuffer
		frameBuffer = new Image(Settings.SCREENWIDTH, Settings.SCREENHEIGHT);
	}

	@Override
	public void render(final GameContainer container,
			final StateBasedGame game, final Graphics graphicContext)
			throws SlickException {
		if (Constants.Debug.doNotRender)
			return;
		Level level = getLevel();

		if (level != null) {
			if (Constants.Debug.shadersActive) {
				// Other only used for post Processing which is not used
				level.render(graphicContext, null);
				// level.render(frameBuffer.getGraphics(), frameBuffer);
				// graphicContext.drawImage(frameBuffer, 0, 0);
			} else {
				level.render(graphicContext, null);
			}
		}
	}

	public abstract void notify(INetworkCommand cmd);

	@Override
	public void update(final GameContainer container,
			final StateBasedGame game, final int deltaInMillis)
			throws SlickException {
		final Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_F1)) {
			container.setPaused(true);
			try {
				container.setFullscreen(!container.isFullscreen());
			} catch (final SlickException e) {
				Log.error(e);
			}
			// container.setVSync(true);
			// container.setSmoothDeltas(true);
			// container.setMaximumLogicUpdateInterval(17);
			container.setPaused(false);
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			onExitKey(container, game);
		}

		final Level level = this.getLevel();

		if (level != null) {
			level.handleInput(input);
			level.update(deltaInMillis);

			EventManager.update();

			// Sorgt dafür dass 1. Collisionnen neu berechnet werden, 2. Zeile
			// Den Objekten gesagt wird die Kollision zu behandeln.
			CollisionManager.update();
			level.handleCollisions();
		}
	}

	public abstract void onExitKey(GameContainer container, StateBasedGame game);

}
