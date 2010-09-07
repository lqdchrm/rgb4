package de.fhtrier.gdig.demos.jumpnrun.server;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ServerGame extends JumpNRunGame {
	public int port = 49999;
	public String serverName = "My Server";
	
	public ServerGame(String serverName, int port) {
		this.serverName = serverName;
		this.port = port;
		NetworkComponent.createServerInstance();
		NetworkComponent.getInstance().startListening(port);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//TODO krumholt thomas
		addState(new ServerLobbyState(this));
		addState(new ServerPlayingState());
	}
}
