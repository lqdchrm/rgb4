package de.fhtrier.gdig.demos.jumpnrun.starters;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;

public class JumpNRunClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ClientGame clientGame = new ClientGame();

		// initialize (gfx) settings depending on game type
		if (clientGame != null) {

			try {

				AppGameContainer gc = new AppGameContainer(clientGame);
				gc.setDisplayMode(Settings.SCREENWIDTH, Settings.SCREENHEIGHT,
						false);
				gc.setVSync(true);
				gc.setSmoothDeltas(true);
				gc.setAlwaysRender(true);
				gc.setUpdateOnlyWhenVisible(false);
				gc.setMaximumLogicUpdateInterval(30);
				gc.setTargetFrameRate(60);
				gc.setShowFPS(Constants.Debug.showDebugOverlay);
				gc.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

}
