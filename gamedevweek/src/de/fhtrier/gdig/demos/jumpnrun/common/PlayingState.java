package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.JumpNRun;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.graphics.BlurShader;
import de.fhtrier.gdig.engine.graphics.Shader;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;

public abstract class PlayingState extends BasicGameState implements
		INetworkCommandListener {

	private AssetMgr assets;
	private GameFactory factory;
	private int levelId;
	
	/*
	 * Nur zum Testen von Bloom
	 * 
	 */
	private Image screenBuffer;
	private Graphics screen1Graphics;
	private BlurShader blur1D;
	private Shader lowpass;
	private Player player;
	public static float factor = 2;
	
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
		
		screenBuffer = new Image(JumpNRun.SCREENWIDTH, JumpNRun.SCREENHEIGHT);
		screen1Graphics = screenBuffer.getGraphics();
		
		blur1D = new BlurShader();
		lowpass = new Shader("content/jumpnrun/shader/simple.vert", "content/jumpnrun/shader/lowpass.frag");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics graphicsContext) throws SlickException {
		
		Level level = getLevel();
		
		if (level != null && player == null)
		{
			player = ((Level)this.factory.getEntity(this.levelId)).getCurrentPlayer();
		}
		
		if (player != null)
		{
			float px = player.getData()[Player.X] + player.getData()[Player.CENTER_X] + level.getData(Level.X);
			float py = JumpNRun.SCREENHEIGHT - player.getData()[Player.Y] - player.getData()[Player.CENTER_Y] - level.getData(Level.Y);
			
			screen1Graphics.clear();
			level.render(screen1Graphics);
			screen1Graphics.flush();
			graphicsContext.drawImage(screenBuffer, 0, 0);
			
			if (factor > 0) factor -= 0.02;
			
			Shader.setActiveShader(blur1D);
			blur1D.initialize(JumpNRun.SCREENWIDTH, JumpNRun.SCREENHEIGHT);
			screen1Graphics.drawImage(screenBuffer, 0, 0);
			screen1Graphics.flush();
			
			blur1D.setVertical();
			screen1Graphics.drawImage(screenBuffer, 0, 0);
			
			Shader.setActiveShader(lowpass);
			lowpass.setValue("factor", factor);
			lowpass.setValue("target", px, py);
			graphicsContext.setColor(Color.white);
			Shader.activateAdditiveBlending();
			graphicsContext.drawImage(screenBuffer, 0, 0);
			
			Shader.activateDefaultBlending();
			Shader.setActiveShader(null);
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
