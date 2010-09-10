package de.fhtrier.gdig.demos.jumpnrun;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.common.Lobby;
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;

public class RGB4 {

	public static void main(String[] args) {
		if (Constants.Debug.forceNoFBO)
			GraphicsFactory.setUseFBO(false);
		
		boolean fullscreen = false;

		if (Settings.USE_NATIVE_FULLSCREEN) {
			DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice().getDisplayMode();

			Settings.SCREENWIDTH = dm.getWidth();
			Settings.SCREENHEIGHT = dm.getHeight();
			
			fullscreen = true;
		}

		// create game
		RGB4Game game = Lobby.createGameByArgs(args);

		// initialize (gfx) settings depending on game type
		if (game != null) {

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
