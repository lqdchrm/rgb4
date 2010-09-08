package de.fhtrier.gdig.demos.jumpnrun.client;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.input.InputControl;
import de.fhtrier.gdig.demos.jumpnrun.client.network.ClientData;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryJoin;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryLeave;
import de.fhtrier.gdig.demos.jumpnrun.common.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.ServerData;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreatePlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.engine.sound.SoundManager;

enum LocalState {
	JOINING, CREATINGPLAYER, PLAYING, DISCONNECTING, EXITING
}

public class ClientPlayingState extends PlayingState {

	private LocalState localState;

	private Queue<INetworkCommand> queue;

	private ServerData recv;
	private ClientData send;

	private SoundManager smgr;

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

		// InputControl initialisieren
		InputControl.loadKeyMapping();

		// Load SoundManager
		smgr = new SoundManager(this.getFactory().getAssetMgr());
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
				NetworkComponent.getInstance().sendCommand(
						new QueryCreateEntity(EntityType.PLAYER));
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

		// Client must have joined
		if (localState != LocalState.JOINING) {
			// DoCreatePlayer tells us to create a player, e.g. because someone
			// has
			// joined
			if (cmd instanceof DoCreateEntity) {
				DoCreateEntity dce = (DoCreateEntity) cmd;

				// Create Entity
				int id = this.getFactory().createEntityById(dce.getEntityId(),
						dce.getType());
				Entity e = this.getFactory().getEntity(id);
				e.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);

				getLevel().add(getFactory().getEntity(id));
				return true;
			}

			// DoRemoveEntity tells us to drop an Entity, e.g. because someone
			// has left
			if (cmd instanceof DoRemoveEntity) {
				DoRemoveEntity dre = (DoRemoveEntity) cmd;

				// Remove entity
				int id = dre.getEntityId();

				if (getLevel().getCurrentPlayer() != null
						&& id == getLevel().getCurrentPlayer().getId()) {
					getLevel().setCurrentPlayer(-1);
				}
				getLevel().remove(getFactory().getEntity(id));

				// remove Entity recursively from Factory
				getFactory().removeEntity(id, true);

				return true;
			}
		}

		// AckCreatePlayer tells us which player is our's
		if (cmd instanceof AckCreatePlayer) {
			if (localState != LocalState.CREATINGPLAYER) {
				throw new RuntimeException("Local state was "
						+ localState.name() + " but should be "
						+ LocalState.CREATINGPLAYER.name());
			}
			AckCreatePlayer acp = (AckCreatePlayer) cmd;
			int playerId = acp.getPlayerId();

			Entity player = getFactory().getEntity(playerId);
			player.setUpdateStrategy(EntityUpdateStrategy.ClientToServer);

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

		// TODO remove only handled commands
		queue.clear();

		// apply game data received from server
		if (localState == LocalState.PLAYING) {

			// do stuff with received data
			if (this.recv != null) {
				for (Entry<Integer, NetworkData> e : this.recv.entrySet()) {

					Entity ent = this.getFactory().getEntity(e.getKey());
					if (ent != null
							&& ent.getUpdateStrategy() == EntityUpdateStrategy.ServerToClient) {
						ent.applyNetworkData(e.getValue());
					}
				}
			}

			if (InputControl.isRefKeyPressed(InputControl.REFJUMP)) {
				smgr.playSound(Assets.PlayerJumpSoundId);
			}
		}

		// nur zu DEBUG-Zwecken
		InputControl.loadKeyMapping();

		// update InputControl
		InputControl.updateInputControl(container.getInput());

		// update local data
		super.update(container, game, deltaInMillis);

		// send local data to server
		if (localState == LocalState.PLAYING) {
			// // if we have a player, send updates to the server
			// Level level = getLevel();
			// if (level != null) {
			// Player player = level.getCurrentPlayer();
			// if (player != null) {
			// this.send.setNetworkData(player.getNetworkData());
			// NetworkComponent.getInstance().sendCommand(this.send);
			// }

			// for all entites with strategy clienttoserver do
			for (Entity e : this.getFactory().getEntities()) {
				if (e.getUpdateStrategy() == EntityUpdateStrategy.ClientToServer) {
					this.send.setNetworkData(e.getNetworkData());
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

		Log.debug("PlayingState: Changed state from "
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

	@Override
	public void onExitKey(GameContainer container, StateBasedGame game) {
		game.enterState(GameStates.MENU, new FadeOutTransition(),
				new FadeInTransition());
	}
}
