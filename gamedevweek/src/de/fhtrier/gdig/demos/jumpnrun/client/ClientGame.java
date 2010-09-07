package de.fhtrier.gdig.demos.jumpnrun.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ClientGame extends JumpNRunGame {
	public static int port = 49999;
	public static String nameOrIp = "localhost";
	public static boolean isSpectator = false;

	public ClientGame() {
		NetworkComponent.createClientInstance();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ClientMenuState(this));
		addState(new ClientSelectServerState());
		addState(new ServerSettingsState(this));
		addState(new ClientLobbyState());
		addState(new ClientPlayingState());
	}
}
