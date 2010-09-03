package de.fhtrier.gdig.demos.jumpnrun.client;

import java.util.LinkedList;
import java.util.Map.Entry;
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
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

enum LocalState {
	JOINING, CREATINGPLAYER, PLAYING, DISCONNECTING, EXITING
}

public class ClientPlayingState extends PlayingState {

	private LocalState localState;

	private Queue<INetworkCommand> queue;

	private ServerData recv;
	private ClientData send;

	public ClientPlayingState() {
		this.queue = new LinkedList<INetworkCommand>();
		this.send = new ClientData();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		super.init(arg0, arg1);

		// ask server to join game
		NetworkComponent.getInstance().sendCommand(new QueryJoin());
		setState(LocalState.JOINING);
		
		// HACK load and play sound
		// getFactory().getAssetMgr().storeSound(Assets.LevelSoundtrack, "sounds/kaliba.ogg").loop();
	}

	private boolean handleProtocolCommands(INetworkCommand cmd) {

		// AckJoin tells us, we have successfully joined the game
		if (cmd instanceof AckJoin) {
			if (localState != LocalState.JOINING) {
				throw new RuntimeException("Local state was "
						+ localState.name() + " but should be "
						+ LocalState.JOINING.name());
			}

			if (!ClientGame.isSpectator) {
			// Joining successful -> create player
			// ask for player to be created by the server
			NetworkComponent.getInstance().sendCommand(new QueryCreatePlayer());
			setState(LocalState.CREATINGPLAYER); 
			} else {
				// we are only spectator -> don't create player
				setState(LocalState.PLAYING);
			}
			return true;
		}

		if (cmd instanceof AckLeave) {
			if (localState != LocalState.DISCONNECTING) {
				throw new RuntimeException("Local state was "
						+ localState.name() + " but should be "
						+ LocalState.DISCONNECTING.name());
			}

			// Disconnecting successful -> close game
			NetworkComponent.getInstance().disconnect();
			setState(LocalState.EXITING);
			return true;
		}

		// DoCreatePlayer tells us to create a player, e.g. because someone has
		// joined
		if (cmd instanceof DoCreateEntity) {
			DoCreateEntity dcp = (DoCreateEntity) cmd;

			// Create Player
			int playerId = this.getFactory().createEntity(dcp.getPlayerId(),
					EntityType.PLAYER);
			getLevel().add(getFactory().getEntity(playerId));
			return true;
		}

		// DoRemovePlayer tells us to drop a Player, e.g. because someone has
		// left
		if (cmd instanceof DoRemoveEntity) {
			DoRemoveEntity drp = (DoRemoveEntity) cmd;

			// Remove Player
			int playerId = drp.getPlayerId();

			if (getLevel().getCurrentPlayer() != null && playerId == getLevel().getCurrentPlayer().getId()) {
				getLevel().setCurrentPlayer(-1);
			}
			getLevel().remove(getFactory().getEntity(playerId));
			
			// remove Player recursively from Factory
			getFactory().removeEntity(playerId, true);

			return true;
		}

		// AckCreatePlayer tells us which player is our's
		if (cmd instanceof AckCreatePlayer) {
			if (localState != LocalState.CREATINGPLAYER) {
				throw new RuntimeException("Local state was "
						+ localState.name() + " but should be "
						+ LocalState.CREATINGPLAYER.name());
			}
			AckCreatePlayer acp = (AckCreatePlayer) cmd;
			this.getLevel().setCurrentPlayer(acp.getPlayerId());

			// we got a player, now we can start :-)
			setState(LocalState.PLAYING);
			return true;
		}

		return false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int deltaInMillis) throws SlickException {

		// apply protocol commands
		for (INetworkCommand cmd : queue) {
			if (!cmd.isHandled()) {
				if (cmd instanceof ProtocolCommand) {
					if (handleProtocolCommands(cmd)) {
						cmd.setHandled(true);
					}
				}
			}
		}
		queue.clear();
		
		// apply game data received from server
		if (localState == LocalState.PLAYING) {

			Level level = getLevel();

			if (level != null) {

				Player player = level.getCurrentPlayer();

				// do stuff with received data
				if (this.recv != null) {
					for (Entry<Integer, EntityData> e : this.recv.entrySet()) {

						if (player == null
								|| (!e.getKey().equals(player.getId()))) {

							Player p = level.getPlayer(e.getKey());
							if (p != null) {
								p.setData(e.getValue().data);
								p.setState(e.getValue().state);
							}
						}
					}
				}
			}
		}

		// update local data
		super.update(container, game, deltaInMillis);

		// send local data to server
		if (localState == LocalState.PLAYING) {
			// if we have a player, send updates to the server
			Level level = getLevel();
			if (level != null) {
				Player player = level.getCurrentPlayer();
				if (player != null) {
					this.send.setPlayerData(player.getPlayerData());
					NetworkComponent.getInstance().sendCommand(this.send);
				}
			}
		}

		// trigger sending and receiving
		NetworkComponent.getInstance().update();

		if (localState == LocalState.EXITING) {
			container.exit();
		}
	}

	@Override
	public void notify(INetworkCommand cmd) {

		if (cmd instanceof ServerData) {
			// PlayerData is continuously overwritten
			this.recv = (ServerData) cmd;
		} else {
			// ProtocolCommands have to be queued -> no loss
			this.queue.add(cmd);
		}
	}

	void setState(LocalState state) {
		if (state == null) {
			throw new IllegalArgumentException("state must not be null");
		}

		System.out.println("PlayingState: Changed state from "
				+ ((localState == null) ? "null" : localState.name()) + " to "
				+ state.name());
		localState = state;
	}

	@Override
	public void cleanup(GameContainer container, StateBasedGame game) {
		NetworkComponent.getInstance().sendCommand(
				new QueryLeave(getLevel().getCurrentPlayer().getId()));
		setState(LocalState.DISCONNECTING);
	}
}
