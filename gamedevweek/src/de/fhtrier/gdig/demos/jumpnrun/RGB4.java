package de.fhtrier.gdig.demos.jumpnrun;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.common.Lobby;
import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.ControlConfig;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.Debug;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.GamePlayConstants;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class RGB4
{

	public static final int SCREENWIDTH = 1024;
	public static final int SCREENHEIGHT = 768;

	public static void main(String[] args)
	{

		// Parse Commandline in Constants
		Debug debug = new Constants.Debug();
		ControlConfig controlConfig = new Constants.ControlConfig();
		GamePlayConstants gamePlayConstants = new Constants.GamePlayConstants();
		debug.parseCommandLine(args);
		controlConfig.parseCommandLine(args);
		gamePlayConstants.parseCommandLine(args);

		if (Constants.Debug.forceNoFBO)
			GraphicsFactory.setUseFBO(false);

		// create game
		RGB4Game game = Lobby.createGameByArgs(args);

		// initialize (gfx) settings depending on game type
		if (game != null)
		{

			// make game network aware
			NetworkComponent.getInstance().addListener(game);

			try
			{

				AppGameContainer gc = new AppGameContainer(game);
				gc.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, false);

				if (game instanceof ClientGame)
				{
					gc.setVSync(true);
					gc.setSmoothDeltas(true);
					gc.setAlwaysRender(true);
					gc.setUpdateOnlyWhenVisible(false);
					gc.setMaximumLogicUpdateInterval(30);
					gc.setTargetFrameRate(60);
				} else
				{
					gc.setVSync(false);
					gc.setSmoothDeltas(false);
					gc.setAlwaysRender(true);
					gc.setUpdateOnlyWhenVisible(false);
				}

				gc.start();
			} catch (SlickException e)
			{
				e.printStackTrace();
			}
		}
	}
}
