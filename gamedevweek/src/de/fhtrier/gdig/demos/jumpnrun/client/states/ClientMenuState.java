package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
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

		// play menu background Sound
		SoundManager.loopMusic(Assets.Sounds.MenuSoundtrackId, 1.0f, 0.2f);
		// SoundManager.fadeMusic(Assets.Sounds.MenuSoundtrackId, 50000, 0.2f, false);
	}

	public void bind(final Nifty newNifty, final Screen newScreen) {
		screen = newScreen;
	}
	
	public void onStartScreen() {
		if (screen.getScreenId().equals("start")) {
			nifty.gotoScreen("mainMenu");
		}
		screen.getFocusHandler().setKeyFocus(null);
	}

	public void onEndScreen() {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		try {
			MenuBackgroundRenderer.getInstance().render(container, game, g);
			super.render(container, game, g);
			MenuBackgroundRenderer.getInstance().renderMouseParticle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void joinGame() {
		screen.endScreen(new EndNotify() {
			public void perform() {
				game.enterState(GameStates.SERVER_SELECTION);
			}
		});
	}

	public void hostGame() {
		screen.endScreen(new EndNotify() {
			public void perform() {
				game.enterState(GameStates.SERVER_SETTINGS);
			}
		});
	}

	public void exit() {
		screen.endScreen(new EndNotify() {
			public void perform() {
				game.getContainer().exit();
			}
		});
	}

	public void credits() {
		screen.endScreen(new EndNotify() {
			public void perform() {
				game.enterState(GameStates.CLIENT_CREDITS);
			}
		});
	}

	@Override
	public void mouseMoved(final int oldx, final int oldy, final int newx,
			final int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public Nifty getNifty() {
		return nifty;
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		// set on transition-in-out-screen
		if (!nifty.getCurrentScreen().equals("mainMenu_with_transition"))
			nifty.gotoScreen("mainMenu_with_transition");
	}
}
