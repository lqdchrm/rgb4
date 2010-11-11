package de.fhtrier.gdig.demos.jumpnrun.server;

import java.net.InterfaceAddress;
import java.util.List;

import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.GameSoundManager;
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.Debug;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;
import de.fhtrier.gdig.demos.jumpnrun.server.states.ServerLobbyState;
import de.fhtrier.gdig.demos.jumpnrun.server.states.ServerPlayingState;
import de.fhtrier.gdig.engine.helpers.Configuration;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.NetworkBroadcastListener;

public class ServerGame extends RGB4Game {
	public static int port = 49999;
	public static InterfaceAddress networkInterface = null;
	public String serverName = "My Server";
	private NetworkBroadcastListener netBroadCastListener;

	public ServerGame(String serverName, InterfaceAddress ni, int port)
			throws SlickException {
		super(Assets.Config.GameTitle + " (" + serverName + ")");
		// do network stuff

		this.serverName = serverName;
		ServerGame.networkInterface = ni;
		ServerGame.port = port;

		// TODO read in interface from somewhere
		if (networkInterface == null) {
			List<InterfaceAddress> networkInterfaces = NetworkHelper
					.getInterfaces();

			if (networkInterfaces.size() > 0) {
				networkInterface = networkInterfaces.get(0);
			} else {
				System.out.println("No network interfaces detected");
				return;
			}
		}

		NetworkComponent.createServerInstance();
		NetworkComponent.getInstance().addListener(this);
		NetworkComponent.getInstance().startListening(networkInterface, port);

		netBroadCastListener = new NetworkBroadcastListener(serverName, "map1",
				"1.0", networkInterface.getAddress(), port);
		netBroadCastListener.start();

		// create SoundManager
		GameSoundManager.init(false);

		Constants.GamePlayConstants c = new Constants.GamePlayConstants();
		Constants.Debug d = new Debug();
		Constants.SoundConfig s = new Constants.SoundConfig();
		Constants.DoomsDayDeviceConfig doom = new Constants.DoomsDayDeviceConfig();
		Configuration.showEditor(
				"Server",
				new JPanel[] { d.getEdittingPanel(), c.getEdittingPanel(),
						s.getEdittingPanel(), doom.getEdittingPanel() });
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ServerLobbyState(this));
		addState(new ServerPlayingState());
	}
}
