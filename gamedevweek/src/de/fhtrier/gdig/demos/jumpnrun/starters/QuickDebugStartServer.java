package de.fhtrier.gdig.demos.jumpnrun.starters;

import java.net.InterfaceAddress;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.demos.jumpnrun.server.ServerGame;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;

public class QuickDebugStartServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final String serverName = "Testserver";
		final String host = "192.168.2.103";
		final int port = 49999;

		InterfaceAddress ni = NetworkHelper.getInterfaceByIp(host);
		ServerGame serverGame = new ServerGame(serverName, ni, port);

		// initialize (gfx) settings depending on game type
		if (serverGame != null) {

			try {

				AppGameContainer gc = new AppGameContainer(serverGame);
				gc.setDisplayMode(Settings.SCREENWIDTH, Settings.SCREENHEIGHT,
						false);
				gc.setVSync(false);
				gc.setSmoothDeltas(false);
				gc.setAlwaysRender(true);
				gc.setUpdateOnlyWhenVisible(false);
				gc.setTargetFrameRate(60);
				gc.setShowFPS(Constants.Debug.showDebugOverlay);
				gc.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
