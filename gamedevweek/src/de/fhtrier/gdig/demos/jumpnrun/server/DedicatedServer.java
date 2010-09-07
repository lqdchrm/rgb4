package de.fhtrier.gdig.demos.jumpnrun.server;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class DedicatedServer {

	String name;
	int port;
	
	public DedicatedServer(String name, int port) {
		this.name = name;
		this.port = port;
	}

	public static void main(String[] args) {
		// create game
		ServerGame serverGame = new ServerGame(args[0], Integer.parseInt(args[1]));

		// initialize (gfx) settings depending on game type
		if (serverGame != null) {

			// make game network aware
			NetworkComponent.getInstance().addListener(serverGame);

			try {

				AppGameContainer gc = new AppGameContainer(serverGame);
				gc.setDisplayMode(Settings.SCREENWIDTH, Settings.SCREENHEIGHT, false);
				gc.setVSync(false);
				gc.setSmoothDeltas(false);
				gc.setAlwaysRender(true);
				gc.setUpdateOnlyWhenVisible(false);
				gc.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

}
