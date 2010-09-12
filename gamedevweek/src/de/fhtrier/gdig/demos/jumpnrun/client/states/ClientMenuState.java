package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.states.gui.MenuBackground;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.sound.SoundManager;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientMenuState extends NiftyGameState implements ScreenController {

	private static final String CROSSHAIR_PNG = "crosshair.png";
	public static String menuNiftyXMLFile = "mainmenu.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;

	private StateBasedGame game;
	private Screen screen;

	public ClientMenuState(final StateBasedGame newGame) throws SlickException {
		super(GameStates.MENU);
		game = newGame;

		// add asset-folder to the ResourceLocators of nifty and slick2d
		ResourceLoader.addResourceLocation(new FileSystemLocation(new File(
				menuAssetPath)));

		org.newdawn.slick.util.ResourceLoader
			.addResourceLocation(new org.newdawn.slick.util.FileSystemLocation(
				new File(menuAssetPath)));
		
		// read the nifty-xml-file
		fromXml(menuNiftyXMLFile,
				ResourceLoader.getResourceAsStream(menuNiftyXMLFile), this);
		
		
		// show the mouse
		enableMouseImage(new Image(
				ResourceLoader.getResourceAsStream(CROSSHAIR_PNG),
				CROSSHAIR_PNG, false));

		// init Sound
		SoundManager.loopMusic(Assets.Sounds.MenuSoundtrackId, 1.0f, 0f);
		SoundManager.fadeMusic(Assets.Sounds.MenuSoundtrackId, 50000, 0.2f,
				false);
		
	}

	public void bind(final Nifty newNifty, final Screen newScreen) {
		screen = newScreen;
	}

	public void onStartScreen() {
		if (screen.getScreenId().equals("start")) {
			nifty.gotoScreen("mainMenu");
		} else if (screen.getScreenId().equals("newGame")) {
			// screen.findElementByName("newGame").setFocus();
		}
		screen.getFocusHandler().setKeyFocus(null);
	}

	public void onEndScreen() {
		SoundManager
				.fadeMusic(Assets.Sounds.MenuSoundtrackId, 50000, 0f, false);
		SoundManager.stopMusic(Assets.Sounds.MenuSoundtrackId);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		try {
			MenuBackground.getInstance().render(container, game, g);
			super.render(container, game, g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void joinGame() {
		game.enterState(GameStates.SERVER_SELECTION, new FadeOutTransition(),
				new FadeInTransition());
	}

	public void hostGame() {
		game.enterState(GameStates.SERVER_SETTINGS, new FadeOutTransition(),
				new FadeInTransition());
	}

	public void exit() {
		screen.endScreen(new EndNotify() {
			public void perform() {
				game.getContainer().exit();
			}
		});
	}

	public void credits() {
		game.enterState(GameStates.CLIENT_CREDITS, new FadeOutTransition(),
				new FadeInTransition());
	}

	public void mouseMoved(final int oldx, final int oldy, final int newx,
			final int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);

		if (Constants.Debug.guiDebug) {
			Log.debug(oldx + ", " + oldy + ", " + newx + ", " + newy);
		}
	}

	public Nifty getNifty() {
		return nifty;
	}
}
