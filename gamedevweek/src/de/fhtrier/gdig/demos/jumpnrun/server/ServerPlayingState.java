package de.fhtrier.gdig.demos.jumpnrun.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.network.ClientData;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryAction;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryCreatePlayer;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryJoin;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryLeave;
import de.fhtrier.gdig.demos.jumpnrun.common.Level;
import de.fhtrier.gdig.demos.jumpnrun.common.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.ServerData;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreatePlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.physics.MoveableEntity;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class ServerPlayingState extends PlayingState {

	private Queue<INetworkCommand> queue;
	private ServerData send;
	private HashMap<Integer, Integer> networkId2Player;

	public ServerPlayingState() {
		this.queue = new LinkedList<INetworkCommand>();
		this.send = new ServerData();
		networkId2Player = new HashMap<Integer, Integer>();
	}

	private boolean handlePlayerActions(QueryAction actionCmd) {
				
		switch(actionCmd.getAction()) {
		case DROPGEM:
			int id = this.getFactory().createEntity(EntityType.GEM);
			getLevel().add(getFactory().getEntity(id));

			// send command to all clients to create bullet
			NetworkComponent.getInstance().sendCommand(
					new DoCreateEntity(id, EntityType.GEM));
			
			// set values
			MoveableEntity gem = (MoveableEntity)getFactory().getEntity(id);
			int playerId = networkId2Player.get(actionCmd.getSender());
			MoveableEntity player = (MoveableEntity)getFactory().getEntity(playerId);
			gem.getData()[Entity.X] = player.getData()[Entity.X];
			gem.getData()[Entity.Y] = player.getData()[Entity.Y];
			gem.getVel()[Entity.X] = player.getVel()[Entity.X];
			gem.getVel()[Entity.Y] = player.getVel()[Entity.Y]-50.0f;
			
			return true;
		}
		
		return false;
	}
	
	private boolean handleProtocolCommands(INetworkCommand cmd) {

		// QueryJoin
		if (cmd instanceof QueryJoin) {
			
			// create every (player) entity from server on client
			for (Entity e : getFactory().getEntities()) {
				if (e instanceof Player) {
					NetworkComponent.getInstance().sendCommand(cmd.getSender(), new DoCreateEntity(e.getId(), EntityType.PLAYER));
				}
			}
			NetworkComponent.getInstance().sendCommand(cmd.getSender(), new AckJoin());
			return true;
		}
		
		// QueryLeave
		if (cmd instanceof QueryLeave) {
			int playerId = ((QueryLeave)cmd).getPlayerId();

			NetworkComponent.getInstance().sendCommand(new DoRemoveEntity(playerId));
			getLevel().remove(getFactory().getEntity(playerId));
			getFactory().removeEntity(playerId, true);
			NetworkComponent.getInstance().sendCommand(cmd.getSender(), new AckLeave());
		}
		
		// QueryCreatePlayer
		if (cmd instanceof QueryCreatePlayer) {

			// create player on server
			int playerId = this.getFactory().createEntity(EntityType.PLAYER);
			getLevel().add(getFactory().getEntity(playerId));

			// send command to all clients to create player
			NetworkComponent.getInstance().sendCommand(
					new DoCreateEntity(playerId, EntityType.PLAYER));
			
			// tell client that this is his player
			NetworkComponent.getInstance().sendCommand(cmd.getSender(), new AckCreatePlayer(playerId));
			
			// remember, which networkId has which playerId
			networkId2Player.put(cmd.getSender(), playerId);
			
			return true;
		}
		
		// QueryAction
		if (cmd instanceof QueryAction) {
			return handlePlayerActions((QueryAction)cmd);
		}
		
		return false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int deltaInMillis) throws SlickException {

		// recv and execute items in queue
		for (INetworkCommand data : this.queue) {
			if (data != null && !data.isHandled()) {

				// handle client data
				if (data instanceof ClientData) {
					ClientData d = (ClientData) data;
					Level level = getLevel();
					if (level != null) {
						Entity e = getFactory().getEntity(d.getNetworkData().id);
						if (e != null) {
							e.applyNetworkData(d.getNetworkData());
						}
					}
					data.setHandled(true);
				}

				// handle other commands
				if (data instanceof ProtocolCommand) {
					if (handleProtocolCommands(data)) {
						data.setHandled(true);
					}
				}
			}
		}

		this.queue.clear();

		super.update(container, game, deltaInMillis);

		this.send.clear();

		for (Entity e : this.getFactory().getEntities()) {
			NetworkData data = e.getNetworkData();
			this.send.put(data.id, data);
		}

		NetworkComponent.getInstance().sendCommand(this.send);
		NetworkComponent.getInstance().update();
	}

	@Override
	public void notify(INetworkCommand cmd) {
		this.queue.add(cmd);
	}

	@Override
	public void cleanup(GameContainer container, StateBasedGame game) {
		container.exit();
	}

	@Override
	public void onExitKey(GameContainer container, StateBasedGame game) {
		cleanup(container, game);
	}
}
