package de.fhtrier.gdig.demos.jumpnrun;

import java.net.InterfaceAddress;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.Lobby;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.demos.jumpnrun.server.ServerGame;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;

public class JumpNRunServer {

	public static void main(String[] args) {
		// create game
		ServerGame serverGame = null;

		switch(args.length) {
		case 0:
			serverGame = Lobby.configServer();
			break;
		case 1:
			// port only
			serverGame = new ServerGame("my-server", null, Integer.parseInt(args[0]));
			break;
		case 2:
			// name and port
			serverGame = new ServerGame(args[0], null,  Integer.parseInt(args[1]));
			break;
		case 3:
			// name, interfaceip, port
			InterfaceAddress ni = NetworkHelper.getInterfaceByIp(args[1]);
			serverGame = new ServerGame(args[0], ni, Integer.parseInt(args[2]));
			break;
		}

		// initialize (gfx) settings depending on game type
		if (serverGame != null) {

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
