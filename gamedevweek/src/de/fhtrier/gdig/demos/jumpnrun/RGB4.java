package de.fhtrier.gdig.demos.jumpnrun;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.common.Lobby;
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.ControlConfig;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.Debug;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;

public class RGB4 {

	public static void main(String[] args) throws SlickException {

		// Parse Commandline in Constants
		Debug debug = new Constants.Debug();
		ControlConfig controlConfig = new Constants.ControlConfig();
		GamePlayConstants gamePlayConstants = new Constants.GamePlayConstants();
		debug.parseCommandLine(args);
		controlConfig.parseCommandLine(args);
		gamePlayConstants.parseCommandLine(args);

	
		if (Constants.Debug.forceNoFBO)
			GraphicsFactory.setUseFBO(false);
		

		// create game
		RGB4Game game = Lobby.createGameByArgs(args);

		// initialize (gfx) settings depending on game type
		if (game != null) {
			
			boolean fullscreen = false;

			if (game instanceof ClientGame && Settings.USE_NATIVE_FULLSCREEN) {
				DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getDefaultScreenDevice().getDisplayMode();
				
				if (dm.getWidth() >= dm.getHeight()*1.7f)
				{
					Settings.SCREENWIDTH = 1280;
					Settings.SCREENHEIGHT = 720;
				}
				else if (dm.getWidth() >= dm.getHeight()*1.5f)
				{
					Settings.SCREENWIDTH = 1280;
					Settings.SCREENHEIGHT = 800;
				}
				else if (dm.getWidth() == 1280 && dm.getHeight() == 1024)
				{
					Settings.SCREENWIDTH = 1280;
					Settings.SCREENHEIGHT = 1024;
				}
				else if (dm.getWidth() >= 1280)
				{
					Settings.SCREENWIDTH = 1280;
					Settings.SCREENHEIGHT = 960;
				}
				
				fullscreen = true;
			}

			try {

				AppGameContainer gc = new AppGameContainer(game);
				gc.setDisplayMode(Settings.SCREENWIDTH, Settings.SCREENHEIGHT,
						fullscreen);

				if (game instanceof ClientGame) {
					gc.setVSync(true);
					gc.setSmoothDeltas(true);
					gc.setAlwaysRender(true);
					gc.setUpdateOnlyWhenVisible(false);
					gc.setMaximumLogicUpdateInterval(30);
					gc.setTargetFrameRate(60);
				} else {
					gc.setVSync(false);
					gc.setSmoothDeltas(false);
					gc.setAlwaysRender(true);
					gc.setUpdateOnlyWhenVisible(false);
				}

				gc.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
