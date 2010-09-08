package de.fhtrier.gdig.demos.jumpnrun.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryStartGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkPlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckConnect;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckNewPlayerList;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckStartGame;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.lessvoid.nifty.slick.NiftyGameState;

public class ClientLobbyState extends NiftyGameState implements INetworkCommandListener {

	private Queue<INetworkCommand> queue;
	private ArrayList<NetworkPlayer> players;
	private StateBasedGame game;
	private boolean isGameCreator = false;
	
	public boolean isGameCreator() {
		return isGameCreator;
	}

	public void setGameCreator(boolean isGameCreator) {
		this.isGameCreator = isGameCreator;
	}

	public ClientLobbyState() {
		super(GameStates.CLIENT_LOBBY);
		queue = new LinkedList<INetworkCommand>();
		players = new ArrayList<NetworkPlayer>();
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		this.game = game;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		super.render(container, game, g);
		float offset = 30;
		g.drawString("Client Currently connected: ", 10.0f, offset);
		for (NetworkPlayer player : players) {
			g.drawString(player.getPlayerName(), 10.0f, 20.0f + offset);
			offset += 10.0f;
		}
		g.drawString("PRESS ENTER TO START", 200, 400);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		// TODO Auto-generated method stub
		super.update(container, game, d);
		
		
		// recv and execute items in queue
		for (INetworkCommand data : this.queue) {
			if (data != null && !data.isHandled()) {

				if (data instanceof ProtocolCommand) {
					handleProtocolCommands(data);
					data.setHandled(true);
				}
			}
		}
		
		if(container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			if(isGameCreator) {
				Log.debug("trying to start game");
				NetworkComponent.getInstance().sendCommand(new QueryStartGame());
			} else {
				Log.debug("You tried to start the game but you are not the GameCreator.");
			}
		}
		// clear all commands even if not handled
		queue.clear();
		
		NetworkComponent.getInstance().update();
	}
	
	private void handleProtocolCommands(INetworkCommand cmd) {
		if (cmd instanceof AckConnect) {
			Log.debug("Client connected to serverlobby");
		}
		if (cmd instanceof AckNewPlayerList) {
			players = ((AckNewPlayerList) cmd).getPlayerList();
		}
		if (cmd instanceof AckStartGame) {
			game.enterState(GameStates.PLAYING);
		}
	}

	@Override
	public void notify(INetworkCommand cmd) {
		queue.add(cmd);
	}

}
