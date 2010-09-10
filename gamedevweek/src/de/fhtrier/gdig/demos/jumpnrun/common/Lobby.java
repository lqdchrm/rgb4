package de.fhtrier.gdig.demos.jumpnrun.common;

import java.net.InterfaceAddress;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientPlayingState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.debug.DebugNoMenuStarterState;
import de.fhtrier.gdig.demos.jumpnrun.server.ServerGame;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;
import de.fhtrier.gdig.engine.network.INetworkLobby;
import de.fhtrier.gdig.engine.network.NetworkServerObject;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;

public class Lobby extends JDialog {

	public static final int SERVER = 0;
	public static final int CLIENT = 1;
	public static final int SPECTATOR = 2;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8056874862301688643L;

	private static ServerGame createServer(String serverName,
			InterfaceAddress ni, int port) {
		return new ServerGame(serverName, ni, port);
	}

	public static ClientGame createClient(boolean debug, final String ip, final int port) {

		if (debug) {
			return new ClientGame()
			{

				@Override
				public void initStatesList(GameContainer container)
						throws SlickException {
					addState(new DebugNoMenuStarterState(ip, port));
					addState(new ClientPlayingState());
				}
				
			};

		}
		
		return new ClientGame();
	}

	public static RGB4Game createGameByArgs(String[] args) {

		return createGameViaDialog(); // via Dialog

	}

	public static RGB4Game createGameViaDialog() {

		// Ask whether we want to be server
		Object[] options = { "Server", "Client", "Spectator" };
		int result = JOptionPane.showOptionDialog(null,
				"Do you want to be Server or Client", "Lobby", 0,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		switch (result) {
		case SERVER: {
			return configServer();
		}
		case SPECTATOR:
			ClientGame.isSpectator = true;
		case CLIENT: {
			return createClient(false, "", 0);
		}
		default:
			return null;
		}
	}

	public static ClientGame configDebugClient() {
		
		List<InterfaceAddress> interfaces = NetworkHelper.getInterfaces();

		Object[] serverListe = new Object[interfaces.size()];

		for (int x = 0; x < interfaces.size(); x++) {
			serverListe[x] = interfaces.get(x).getAddress()
					.getHostAddress();
		}

		String Interface = (String) JOptionPane.showInputDialog(null,
				"Please select interface to scan for servers",
				"You are Client", JOptionPane.PLAIN_MESSAGE, null,
				serverListe, null);

		InterfaceAddress ni = null;

		for (int x = 0; x < interfaces.size(); x++) {
			if (Interface.contains(interfaces.get(x).getAddress()
					.getHostAddress()))
				ni = interfaces.get(x);
		}
		
		INetworkLobby networkLobby = new NetworkLobby();
		networkLobby.getServers(ni);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getLocalizedMessage());
		}

		List<NetworkServerObject> sList = networkLobby.getServerList();

		Object[] servers = new Object[sList.size()];

		for (int x = 0; x < sList.size(); x++) {
			servers[x] = sList.get(x).getIp().getHostAddress();
		}

		// if client ask for server ip and port number
		String strServer = (String) JOptionPane.showInputDialog(null,
				"Please select server", "You are Client",
				JOptionPane.PLAIN_MESSAGE, null, servers,
				ClientGame.nameOrIp);

		String ip = "127.0.0.1";
		int port = 49999;

		for (int x = 0; x < sList.size(); x++) {
			if (sList.get(x).getIp().getHostAddress().contains(strServer)) {
				ip = sList.get(x).getIp().getHostAddress();
				port = sList.get(x).getPort();
			}
		}

		networkLobby.stopGetServers();
		return createClient(true, ip, port);
	}

	public static ServerGame configServer() {
		List<InterfaceAddress> interfaces = NetworkHelper.getInterfaces();

		Object[] serverListe = new Object[interfaces.size()];

		for (int x = 0; x < interfaces.size(); x++) {
			serverListe[x] = interfaces.get(x).getAddress()
					.getHostAddress();
		}

		String Interface = (String) JOptionPane.showInputDialog(null,
				"Please select interface to host on", "You are Server",
				JOptionPane.PLAIN_MESSAGE, null, serverListe, null);

		InterfaceAddress ni = null;

		for (int x = 0; x < interfaces.size(); x++) {
			if (Interface.contains(interfaces.get(x).getAddress()
					.getHostAddress()))
				ni = interfaces.get(x);
		}

		String strPort = (String) JOptionPane.showInputDialog(null,

		"Please enter port number to host on", "You are Server",
				JOptionPane.PLAIN_MESSAGE, null, null, ServerGame.port);

		return createServer("My Server", ni, Integer.parseInt(strPort));
	}
}
