package de.fhtrier.gdig.demos.jumpnrun.client;

import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientHostServerState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientLobbyState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientMenuState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientPlayingState;
import de.fhtrier.gdig.demos.jumpnrun.client.states.ClientSelectServerState;
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.ControlConfig;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.Debug;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.GamePlayConstants;
import de.fhtrier.gdig.engine.helpers.Configuration;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ClientGame extends RGB4Game
{
	public static int port = 49999;
	public static String nameOrIp = "localhost";
	public static boolean isSpectator = false;

	public ClientGame()
	{
		super(Assets.GameTitle);

		NetworkComponent.createClientInstance();
		NetworkComponent.getInstance().addListener(this);
		//
		// while (!NetworkComponent.getInstance().connect(nameOrIp, port))
		// {
		// try
		// {
		// Log.info("Waiting for Server");
		// Thread.sleep(5000);
		// } catch (InterruptedException e)
		// {
		// }
		// }

		GamePlayConstants gamePlayConstants = new Constants.GamePlayConstants();
		Debug debug = new Constants.Debug();
		ControlConfig controlConfig = new Constants.ControlConfig();
		Configuration.showEditor("ClientSettings", new JPanel[] {
				gamePlayConstants.getEdittingPanel(), debug.getEdittingPanel(),
				controlConfig.getEdittingPanel() });

	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException
	{
		addState(new ClientMenuState(this));
		addState(new ClientSelectServerState());
		addState(new ClientHostServerState(this));
		addState(new ClientLobbyState());
		addState(new ClientPlayingState());
	}
}
