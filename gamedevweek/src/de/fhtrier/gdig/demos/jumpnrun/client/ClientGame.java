package de.fhtrier.gdig.demos.jumpnrun.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ClientGame extends JumpNRunGame {
	public static int port = 49999;
	public static String nameOrIp = "localhost";
	public static boolean isSpectator = false;

	public ClientGame() {
		NetworkComponent.createClientInstance();
		
		while (!NetworkComponent.getInstance().connect(nameOrIp, port)) {
			try {
				System.out.println("Waiting for Server");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ClientMenuState(GameStates.MENU,container, this));
		addState(new ClientPlayingState());
	}
}
