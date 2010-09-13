package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.client.input.InputControl;
import de.fhtrier.gdig.demos.jumpnrun.client.network.ClientData;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryJoin;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryLeave;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryPlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.common.events.Event;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.WonGameEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.states.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.ServerData;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoPlaySound;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendChangeColor;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendChangeWeaponColor;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendKill;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendPlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendWon;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;
import de.fhtrier.gdig.engine.sound.SoundManager;

enum LocalState {
	JOINING, CREATINGPLAYER, PLAYING, DISCONNECTING, EXITING
}

public class ClientPlayingState extends PlayingState {

	private LocalState localState;

	private Queue<INetworkCommand> queue;

	private ServerData recv;
	private ClientData send;

	public ClientPlayingState() throws SlickException {
		this.queue = new LinkedList<INetworkCommand>();
		this.send = new ClientData();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);

		// ask server to join game
		NetworkComponent.getInstance().sendCommand(new QueryJoin());
		setState(LocalState.JOINING);

		// InputControl initialisieren
		InputControl.loadKeyMapping();
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

			SoundManager.playSound(Assets.Sounds.PlayerJoiningSoundID);
			SoundManager.loopMusic(Assets.Sounds.LevelSoundtrackId, 1.0f, 0f);
			SoundManager.fadeMusic(Assets.Sounds.LevelSoundtrackId, 50000,
					0.2f, false);

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

			// HACK special treatment for players
			if (e instanceof Player) {
				NetworkComponent.getInstance().sendCommand(
						new QueryPlayerCondition(id));
			}
			return true;
		}

		// DoRemoveEntity tells us to drop an Entity, e.g. because someone has
		// left
		if (cmd instanceof DoRemoveEntity) {
			DoRemoveEntity dre = (DoRemoveEntity) cmd;

			// Remove entity
			int id = dre.getEntityId();

			if (getLevel().getCurrentPlayer() != null
					&& id == getLevel().getCurrentPlayer().getId()) {
				getLevel().setCurrentPlayer(-1);
			}

			// robindi: Bugfix, removeEntity from CollisionManager!
			CollisionManager.removeEntity((CollidableEntity) getFactory().getEntity(id));
			
			
			Entity entityToRemove = getFactory().getEntity(id);				
			if (entityToRemove==null && Constants.Debug.networkDebug)
			{
				Log.error("Tried to remove Entity with id="+id+" but this id was not known to factory!!!");
			}
			else
			{
				getLevel().remove(entityToRemove);
				// remove Entity recursively from Factory
				getFactory().removeEntity(id, true);
			}

			return true;
		}

		// DoPlaySound... well it just does what it says
		if (cmd instanceof DoPlaySound) {
			DoPlaySound dps = (DoPlaySound) cmd;

			SoundManager.playSound(dps.getSoundAssetId());
			return true;
		}

		// AckCreatePlayer tells us which player is our's
		if (cmd instanceof AckCreateEntity) {
			if (localState != LocalState.CREATINGPLAYER) {
				throw new RuntimeException("Local state was "
						+ localState.name() + " but should be "
						+ LocalState.CREATINGPLAYER.name());
			}
			AckCreateEntity acp = (AckCreateEntity) cmd;
			int playerId = acp.getEntityId();

			Player player = (Player)getFactory().getEntity(playerId);

			this.getLevel().setCurrentPlayer(acp.getEntityId());
			player.respawn();

			// we got a player, now we can start :-)
			setState(LocalState.PLAYING);
			return true;
		}

		if (cmd instanceof SendKill) {
			SendKill killCommand = (SendKill) cmd;

			Player player = getLevel().getPlayer(killCommand.getPlayerId());
			player.die();

			return true;
		}

		if (cmd instanceof SendWon) {
			SendWon wonCommand = (SendWon) cmd;

			Event winEvent;
			if (wonCommand.getWinnerType() == SendWon.winnerType_Player) {
				winEvent = new WonGameEvent(getLevel().getPlayer(
						wonCommand.getWinnerId()));
			} else { // Team is Winner
				winEvent = new WonGameEvent(Team.getTeamById(wonCommand
						.getWinnerId()));
			}

			EventManager.addEvent(winEvent);
			return true;
		}

		if (cmd instanceof SendChangeColor) {
			SendChangeColor colorChange = (SendChangeColor) cmd;

			Player player = getLevel().getPlayer(colorChange.getPlayerId());
			player.nextColor();
			return true;
		}

		if (cmd instanceof SendChangeWeaponColor) {
			SendChangeWeaponColor colorChange = (SendChangeWeaponColor) cmd;

			Player player = getLevel().getPlayer(colorChange.getPlayerId());
			player.nextWeaponColor();
			return true;
		}

		if (cmd instanceof SendPlayerCondition) {
			SendPlayerCondition sspn = (SendPlayerCondition) cmd;

			Player player = getLevel().getPlayer(sspn.getPlayerId());
			player.setPlayerCondition(sspn.getPlayerCondition());
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

		if (Constants.Debug.networkDebug) {
			Log.debug("PlayingState: Changed state from "
					+ ((localState == null) ? "null" : localState.name())
					+ " to " + state.name());
		}
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