package de.fhtrier.gdig.demos.jumpnrun.client.states.debug;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryStartGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckStartGame;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class DebugNoMenuStarterState extends BasicGameState implements INetworkCommandListener {


	private final int NOT_CONNECTED = 1;
	private final int CONNECTING = 2;
	private final int CONNECTED = 3;
	private final int REQUESTED_GAME_START = 4;
	
	private int status = NOT_CONNECTED;
	
	private Queue<INetworkCommand> queue;
	
	private String ip;
	private int port;
	
	public static boolean isMaster = false;
	
	
	public DebugNoMenuStarterState(String ip, int port) {
		super();	
		queue = new LinkedList<INetworkCommand>();
		this.ip = ip;
		this.port = port;
	}

	@Override
	public int getID() {
		return 99;
	}

	@Override
	public void notify(INetworkCommand cmd) {
		queue.add(cmd);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void update(GameContainer container, StateBasedGame game, int arg2)
			throws SlickException {
		
		// connect to standard-server
		if (status == NOT_CONNECTED) {
			NetworkComponent.getInstance().connect(ip, port);
			status = CONNECTING;
		}
		
		// wait for Network-ID
		if (status >= CONNECTING) {
			NetworkComponent.getInstance().update();
		}
		
		// if received network-id register player to server
		if(NetworkComponent.getInstance().getNetworkId() != -1) {
			NetworkComponent.getInstance().sendCommand(new QueryConnect("Player"));
			status = CONNECTED;
		}
		
		if (status==CONNECTED && isMaster)
		{
			NetworkComponent.getInstance().sendCommand(new QueryStartGame());
			status = REQUESTED_GAME_START;
		}
		
		for (INetworkCommand cmd : queue)
		{
			if (cmd instanceof AckStartGame) {
				game.enterState(GameStates.PLAYING);
			}
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	
	
}
