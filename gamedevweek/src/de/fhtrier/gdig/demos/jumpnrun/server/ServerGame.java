package de.fhtrier.gdig.demos.jumpnrun.server;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.NetworkBroadcastListener;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;

public class ServerGame extends JumpNRunGame {
	public int port = 49999;
	public String serverName = "My Server";
	private NetworkLobby networkLobby;
	private NetworkBroadcastListener netBroadCastListener;
	
	public ServerGame(String serverName, int port) {
		this.serverName = serverName;
		this.port = port;
		
		NetworkComponent.createServerInstance();
		NetworkComponent.getInstance().startListening(port);
		
		networkLobby = new NetworkLobby();
		
//		for (InterfaceAddress iA : networkLobby.getInterfaces())
		{

				netBroadCastListener = new NetworkBroadcastListener(serverName,"map1","1.0",port);				netBroadCastListener.start();
			
				
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//TODO krumholt thomas
		addState(new ServerLobbyState(this));
		addState(new ServerPlayingState());
	}
}
