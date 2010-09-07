package de.fhtrier.gdig.demos.jumpnrun;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.demos.jumpnrun.common.Lobby;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class JumpNRun {

	public static void main(String[] args) {

		// create game
		JumpNRunGame game = Lobby.createGameByArgs(args);

		// initialize (gfx) settings depending on game type
		if (game != null) {

			// make game network aware
			NetworkComponent.getInstance().addListener(game);

			try {

				AppGameContainer gc = new AppGameContainer(game);
				gc.setDisplayMode(Settings.SCREENWIDTH, Settings.SCREENHEIGHT, false);

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
