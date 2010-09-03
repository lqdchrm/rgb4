package de.fhtrier.gdig.demos.jumpnrun.server;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.network.ClientData;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryCreatePlayer;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryJoin;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryLeave;
import de.fhtrier.gdig.demos.jumpnrun.common.Level;
import de.fhtrier.gdig.demos.jumpnrun.common.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.common.network.EntityData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.ServerData;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreatePlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class ServerPlayingState extends PlayingState {

	private Queue<INetworkCommand> queue;
	private ServerData send;

	public ServerPlayingState() {
		this.queue = new LinkedList<INetworkCommand>();
		this.send = new ServerData();
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
			return true;
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
						Player player = level.getPlayer(d.getPlayerData().id);
						if (player != null) {
							player.setData(d.getPlayerData().data);
							player.setState(d.getPlayerData().state);
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

		for (Entity p : this.getFactory().getEntities()) {
			if (p instanceof Player) {
				EntityData data = ((Player) p).getPlayerData();
				this.send.put(data.id, data);
			}
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
}
