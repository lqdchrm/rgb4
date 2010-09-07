package de.fhtrier.gdig.demos.jumpnrun.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.common.Constants;
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
				Log.info("Waiting for Server");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
		
		Constants.GamePlayConstants c = new Constants.GamePlayConstants();
		c.showEditor("ClientSettings");		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ClientMenuState(GameStates.MENU, container, this));
		addState(new ClientPlayingState());
	}
}
