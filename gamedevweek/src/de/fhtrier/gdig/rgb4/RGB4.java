package de.fhtrier.gdig.rgb4;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.rgb4.client.ClientGame;
import de.fhtrier.gdig.rgb4.common.RGB4Game;
import de.fhtrier.gdig.rgb4.common.Lobby;

public class RGB4 {

	public static final int SCREENWIDTH = 1024;
	public static final int SCREENHEIGHT = 768;

	public static void main(String[] args) {

		// create game
		RGB4Game game = Lobby.createGameByArgs(args);

		// initialize (gfx) settings depending on game type
		if (game != null) {

			// make game network aware
			NetworkComponent.getInstance().addListener(game);

			try {

				AppGameContainer gc = new AppGameContainer(game);
				gc.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, false);

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
