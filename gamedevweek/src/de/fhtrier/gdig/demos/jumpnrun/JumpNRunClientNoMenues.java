package de.fhtrier.gdig.demos.jumpnrun;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.client.ClientLobbyState;
import de.fhtrier.gdig.demos.jumpnrun.client.ClientMenuState;
import de.fhtrier.gdig.demos.jumpnrun.client.ClientPlayingState;
import de.fhtrier.gdig.demos.jumpnrun.client.ClientSelectServerState;
import de.fhtrier.gdig.demos.jumpnrun.client.DebugNoMenuStarterState;
import de.fhtrier.gdig.demos.jumpnrun.client.ServerSettingsState;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryStartGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Settings;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class JumpNRunClientNoMenues {


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ClientGame clientGame = new ClientGame()
		{

			@Override
			public void initStatesList(GameContainer container)
					throws SlickException {
				addState(new DebugNoMenuStarterState());
				addState(new ClientPlayingState());
			}
			
		};

		// initialize (gfx) settings depending on game type
		if (clientGame != null) {

			// make game network aware
			NetworkComponent.getInstance().addListener(clientGame);

			try {
				AppGameContainer gc = new AppGameContainer(clientGame);
				gc.setDisplayMode(Settings.SCREENWIDTH, Settings.SCREENHEIGHT, false);
				gc.setVSync(true);
				gc.setSmoothDeltas(true);
				gc.setAlwaysRender(true);
				gc.setUpdateOnlyWhenVisible(false);
				gc.setMaximumLogicUpdateInterval(30);
				gc.setTargetFrameRate(60);
				gc.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

}
