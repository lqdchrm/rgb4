package de.fhtrier.gdig.demos.jumpnrun.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkPlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckConnect;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckNewPlayerList;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class ServerLobbyState extends BasicGameState  implements
	INetworkCommandListener {

	private ServerGame serverGame;
	private ArrayList<NetworkPlayer> players;
	private Queue<INetworkCommand> queue;	
	
	public ServerLobbyState(ServerGame serverGame) {
		this.serverGame = serverGame;
		players = new ArrayList<NetworkPlayer>();
		queue = new LinkedList<INetworkCommand>();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		float offset = 30;
		g.drawString("Currently connected: ", 10.0f, offset);
		for (NetworkPlayer player : players) {
			g.drawString(player.getPlayerName(), 10.0f, 20.0f + offset);
			offset += 10.0f;
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// recv and execute items in queue
		for (INetworkCommand data : this.queue) {
			if (data != null && !data.isHandled()) {

				if (data instanceof ProtocolCommand) {
					handleProtocolCommands(data);
					data.setHandled(true);
				}
			}
		}
		
		// clear all commands even if not handled
		queue.clear();
		
		// Networkcomponent updaten
		NetworkComponent.getInstance().update();
	}
	
	
	
	private void handleProtocolCommands(INetworkCommand data) {
		if (data instanceof QueryConnect)
		{
			String name = ((QueryConnect)data).getPlayerName();
			NetworkComponent.getInstance().sendCommand(data.getSender(), new AckConnect());
			players.add(new NetworkPlayer(name, data.getSender()));
			NetworkComponent.getInstance().sendCommand(new AckNewPlayerList(players));
		}
		
		if (data instanceof QueryStartGame) {
			serverGame.enterState(GameStates.PLAYING);
			NetworkComponent.getInstance().sendCommand(new AckStartGame());
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.leave(container, game);
		NetworkComponent.getInstance().removeListener(this);
	}

	@Override
	public int getID() {
		return GameStates.SERVER_LOBBY;
	}

	@Override
	public void notify(INetworkCommand cmd) {
		queue.add(cmd);
	}

}
