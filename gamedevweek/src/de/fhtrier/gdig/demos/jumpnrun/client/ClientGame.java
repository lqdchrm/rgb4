package de.fhtrier.gdig.demos.jumpnrun.client;

import java.io.IOException;
import java.util.logging.LogManager;

import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientCreditsState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientHostServerState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientLobbyState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientMenuState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientPlayingState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientSelectServerState;
import de.fhtrier.gdig.demos.jumpnrun.common.GameSoundManager;
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.engine.helpers.Configuration;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ClientGame extends RGB4Game {
	public static int port = 49999;
	public static String nameOrIp = "localhost";
	public static boolean isSpectator = false;

	public ClientGame() throws SlickException {
		super(Assets.Config.GameTitle);
		
		System.setProperty("java.util.logging.config.file", "content/logging.properties");
		try {
			LogManager.getLogManager().readConfiguration();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NetworkComponent.createClientInstance();
		NetworkComponent.getInstance().addListener(this);

		GameSoundManager.init(true);

		Constants.GamePlayConstants c1 = new Constants.GamePlayConstants();
		
		Constants.Debug c3 = new Constants.Debug();
		
		Constants.SoundConfig c4 = new Constants.SoundConfig();
		
		Configuration.showEditor("ClientSettings",
				new JPanel[] {
					c1.getEdittingPanel(),
					c3.getEdittingPanel(),
					c4.getEdittingPanel()}
		);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// container.setMouseCursor(Assets.Config.AssetGuiPath+"/gui-cursor.png", 16,16);
		addState(new ClientMenuState(this));
		addState(new ClientSelectServerState());
		addState(new ClientHostServerState(this));
		addState(new ClientLobbyState());
		addState(new ClientPlayingState());
		addState(new ClientCreditsState());
	}
}
