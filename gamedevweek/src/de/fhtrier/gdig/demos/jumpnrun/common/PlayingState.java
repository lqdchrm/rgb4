package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;

public abstract class PlayingState extends BasicGameState implements
		INetworkCommandListener {

	private AssetMgr assets;
	private GameFactory factory;
	private int levelId;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		// create assetmgr
		this.assets = new AssetMgr();
		this.assets.setAssetPathPrefix("content/jumpnrun/test/");
		this.assets.setAssetFallbackPathPrefix("content/jumpnrun/default/");

		// Factory
		this.factory = new GameFactory(assets);

		// Level
		this.levelId = factory.createEntity(EntityType.LEVEL);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics graphicsContext) throws SlickException {
		
		Level level = getLevel();
		
		if (level != null) {
			level.render(graphicsContext);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int deltaInMillis) throws SlickException {
		Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_F1)) {
			container.setPaused(true);
			try {
			container.setFullscreen(!container.isFullscreen());
			} catch(SlickException e) {
				
			}
			container.setVSync(true);
			container.setSmoothDeltas(true);
			container.setMaximumLogicUpdateInterval(17);
			container.setPaused(false);
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			cleanup(container, game);
		}

		Level level = getLevel();

		if (level != null) {
			level.handleInput(input);
			level.update(deltaInMillis);
			
			// HACK
			level.handleCollisions();
		}
	}

	@Override
	public int getID() {
		return GameStates.PLAYING;
	}

	@Override
	public abstract void notify(INetworkCommand cmd);
	
	public abstract void cleanup(GameContainer container, StateBasedGame game);

	public Level getLevel() {
		Entity level = factory.getEntity(this.levelId);
		if (level instanceof Level) {
			return (Level)level;
		}
		return null;
	}
	
	public GameFactory getFactory() {
		return factory;
	}
}
