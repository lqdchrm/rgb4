package de.fhtrier.gdig.demos.jumpnrun;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.ControlConfig;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.Debug;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.demos.jumpnrun.common.Lobby;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class JumpNRun
{

	public static final int SCREENWIDTH = 1024;
	public static final int SCREENHEIGHT = 768;

	public static void main(String[] args)
	{
		if (Constants.Debug.forceNoFBO)
			GraphicsFactory.setUseFBO(false);

		// Parse Commandline in Constants
		Debug debug = new Constants.Debug();
		ControlConfig controlConfig = new Constants.ControlConfig();
		GamePlayConstants gamePlayConstants = new Constants.GamePlayConstants();
		debug.parseCommandLine(args);
		controlConfig.parseCommandLine(args);
		gamePlayConstants.parseCommandLine(args);

		// create game
		JumpNRunGame game = Lobby.createGameByArgs(args);

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
