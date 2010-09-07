package de.fhtrier.gdig.demos.jumpnrun.client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.lessvoid.nifty.slick.NiftyGameState;


public class ClientSelectServerState extends NiftyGameState {

	private boolean connecting = false;
	
	public ClientSelectServerState() {
		super(GameStates.SERVER_SELECTION);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		super.update(container, game, d);
		if(container.getInput().isKeyPressed(Input.KEY_ENTER)
				&& !connecting) {
			NetworkComponent.getInstance().connect("localhost", 49999);
			connecting = true;
		}
		if(connecting) {
			NetworkComponent.getInstance().update();
		}
		if(NetworkComponent.getInstance().getNetworkId() != -1) {
			NetworkComponent.getInstance().sendCommand(new QueryConnect("holycrap"));
			game.enterState(GameStates.CLIENT_LOBBY);
		}
		
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);	
	}

}
