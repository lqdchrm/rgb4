package de.fhtrier.gdig.demos.jumpnrun.client;

import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

<<<<<<< HEAD
import de.fhtrier.gdig.demos.jumpnrun.common.Constants;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.ControlConfig;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.Debug;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
=======
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
>>>>>>> roessgri/master
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.support.Configuration;


public class ClientGame extends RGB4Game {
	public static int port = 49999;
	public static String nameOrIp = "localhost";
	public static boolean isSpectator = false;

	public ClientGame() {
		super(Assets.GameTitle);

		NetworkComponent.createClientInstance();

		while (!NetworkComponent.getInstance().connect(nameOrIp, port))
		{
			try
			{
				Log.info("Waiting for Server");
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
			}
		}

<<<<<<< HEAD
		GamePlayConstants gamePlayConstants = new Constants.GamePlayConstants();
		Debug debug = new Constants.Debug();
		ControlConfig controlConfig = new Constants.ControlConfig();
		Configuration.showEditor("ClientSettings", new JPanel[] {
				gamePlayConstants.getEdittingPanel(), debug.getEdittingPanel(),
				controlConfig.getEdittingPanel() });

=======
		Constants.GamePlayConstants c1 = new Constants.GamePlayConstants();

		Constants.ControlConfig c2 = new Constants.ControlConfig();
		c1.showEditor("ClientSettings", new JPanel[] { c1.getEdittingPanel(),
				c2.getEdittingPanel() });
>>>>>>> roessgri/master
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException
	{
		addState(new ClientMenuState(GameStates.MENU, container, this));
		addState(new ClientPlayingState());
	}
}
