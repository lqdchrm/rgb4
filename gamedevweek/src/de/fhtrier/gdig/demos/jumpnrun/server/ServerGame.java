package de.fhtrier.gdig.demos.jumpnrun.server;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ServerGame extends JumpNRunGame {
	public static int port = 49999;

	public ServerGame() {
		NetworkComponent.createServerInstance();
		NetworkComponent.getInstance().startListening(port);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ServerPlayingState());
	}
}
