package de.fhtrier.gdig.demos.jumpnrun.server.states;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.client.network.ClientData;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryAction;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryJoin;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryLeave;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryPlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Bullet;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Level;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.PlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.states.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.ServerData;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreatePlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckPlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendChangeColor;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendChangeWeaponColor;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class ServerPlayingState extends PlayingState {

	private Queue<INetworkCommand> queue;
	private ServerData send;
	public static HashMap<Integer, Integer> networkId2Player = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> player2NetworkId = new HashMap<Integer, Integer>();

	public ServerPlayingState() throws SlickException {
		this.queue = new LinkedList<INetworkCommand>();
		this.send = new ServerData();
		
		if(Constants.GamePlayConstants.serverSound)
		{
			SoundManager.init();
			SoundManager.playSound(Assets.Sounds.PlayerJoiningSoundID);
			SoundManager.loopMusic(Assets.Sounds.LevelSoundtrackId, 1.0f, 0f);
			SoundManager.fadeMusic(Assets.Sounds.LevelSoundtrackId, 50000, 0.2f, false);
		}		
		
	}

	private boolean handlePlayerActions(QueryAction actionCmd) {
		Entity e;
		int playerId = networkId2Player.get(actionCmd.getSender());
		Player player = (Player) getFactory().getEntity(playerId);

		switch (actionCmd.getAction()) {
		// case DROPGEM:
		// e = createEntity(EntityType.GEM);
		//
		// // set values
		// MoveableEntity gem = (MoveableEntity) e;
		//
		// // set player pos as gem pos
		// gem.getData()[Entity.X] = player.getData()[Entity.X];
		// gem.getData()[Entity.Y] = player.getData()[Entity.Y];
		// gem.getVel()[Entity.X] = player.getVel()[Entity.X];
		// gem.getVel()[Entity.Y] = player.getVel()[Entity.Y] - 50.0f;
		//
		// return true;
		case SHOOT:
			e = createEntity(EntityType.BULLET);

			// set values
			Bullet bullet = (Bullet) e;
			bullet.owner = player;

			PlayerCondition state = player.getPlayerCondition();

			bullet.color = state.weaponColor;
			// set player pos as gem pos
			bullet.getData()[Entity.X] =
				(player.getData()[Entity.X] + player.getData()[Entity.CENTER_X]) +
				(bullet.getData()[Entity.CENTER_X] - Assets.Weapon.weaponXOffset) * player.getData()[Entity.SCALE_X];

			bullet.getData()[Entity.Y] =
			player.getData()[Entity.Y] + player.getData()[Entity.CENTER_Y] -
			bullet.getData()[Entity.CENTER_Y] + Assets.Weapon.weaponYOffset;
			
			bullet.getVel()[Entity.X] = player.getVel()[Entity.X]
			        + (player.getData()[Entity.SCALE_X] == -1 ? Constants.GamePlayConstants.shotSpeed
			                                            	: -Constants.GamePlayConstants.shotSpeed);
			
			if(player.getData()[Entity.SCALE_X] == -1) // Right
			bullet.getData()[Entity.SCALE_X] = -1;
			
			else if(player.getData()[Entity.SCALE_X] == 1) // Left
			bullet.getData()[Entity.SCALE_X] = 1;

			return true;
		case PLAYERCOLOR:
			player.nextColor();
			NetworkComponent.getInstance().sendCommand(
					new SendChangeColor(player.getId()));

			return true;
		case WEAPONCOLOR:
			player.nextWeaponColor();
			NetworkComponent.getInstance().sendCommand(
					new SendChangeWeaponColor(player.getId()));

			return true;
		case UPDATECONDITION:
			// HACK query for any player
			if (actionCmd instanceof QueryPlayerCondition) {

				QueryPlayerCondition tmpCmd = (QueryPlayerCondition) actionCmd;

				int tmpId = tmpCmd.getPlayerId();
				Player tmpPlayer = (Player) getFactory().getEntity(tmpId);

				NetworkComponent.getInstance().sendCommand(
						actionCmd.getSender(),
						new AckPlayerCondition(tmpId, tmpPlayer
								.getPlayerCondition()));

				return true;
			}
			return false;
		}

		return false;
	}

	private Entity createEntity(EntityType type) {
		int id = this.getFactory().createEntity(type);

		Entity e = getFactory().getEntity(id);

		e.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
		e.setActive(true);

		getLevel().add(e);

		// send command to all clients to create gem
		NetworkComponent.getInstance()
				.sendCommand(new DoCreateEntity(id, type));
		return e;
	}

	private boolean handleProtocolCommands(INetworkCommand cmd) {

		// QueryJoin
		if (cmd instanceof QueryJoin) {

			// create every (player) entity from server on client
			for (Entity e : getFactory().getEntities()) {
				if (e.getUpdateStrategy() == EntityUpdateStrategy.ServerToClient) {
					NetworkComponent.getInstance().sendCommand(cmd.getSender(),
							new DoCreateEntity(e.getId(), e.getType()));
				}
			}
			NetworkComponent.getInstance().sendCommand(cmd.getSender(),
					new AckJoin());
			return true;
		}

		// QueryLeave
		if (cmd instanceof QueryLeave) {
			int playerId = ((QueryLeave) cmd).getPlayerId();

			NetworkComponent.getInstance().sendCommand(
					new DoRemoveEntity(playerId));
			getLevel().remove(getFactory().getEntity(playerId));
			getFactory().removeEntity(playerId, true);
			NetworkComponent.getInstance().sendCommand(cmd.getSender(),
					new AckLeave());
		}

		// QueryCreatePlayer
		if (cmd instanceof QueryCreateEntity) {

			EntityType type = ((QueryCreateEntity) cmd).getType();

			// currently, only client creation of player is allowed
			if (type == EntityType.PLAYER) {
				int id = this.getFactory().createEntity(type);

				Player e = (Player)getFactory().getEntity(id);
				e.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
				getLevel().add(e);

				// send command to all clients to create entity
				NetworkComponent.getInstance().sendCommand(
						new DoCreateEntity(id, EntityType.PLAYER));

				// if query requested new player assume that's the one to be
				// controlled by client
				NetworkComponent.getInstance().sendCommand(cmd.getSender(),
						new AckCreatePlayer(id));

				// remember, which networkId identifies which player
				networkId2Player.put(cmd.getSender(), id);
				player2NetworkId.put(id, cmd.getSender());
				
				String name = ServerLobbyState.players.get(cmd.getSender()).getPlayerName();

				int teamID = ServerLobbyState.players.get(cmd.getSender()).getTeamId();
				
				e.getPlayerCondition().name = name;
				e.getPlayerCondition().teamId = teamID;
				
			} else {
				throw new RuntimeException(
						"Client side entity creation only allowed for type PLAYER");
			}
			return true;
		}

		// QueryAction
		if (cmd instanceof QueryAction) {
			return handlePlayerActions((QueryAction) cmd);
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
						Entity e = getFactory()
								.getEntity(d.getNetworkData().id);
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

		// TODO remove only handled items from queue
		this.queue.clear();

		super.update(container, game, deltaInMillis);

		this.send.clear();

		// send entity data every frame
		for (Entity e : this.getFactory().getEntities()) {
			if (e.getUpdateStrategy() == EntityUpdateStrategy.ServerToClient) {

				NetworkData data = e.getNetworkData();
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

	@Override
	public void onExitKey(GameContainer container, StateBasedGame game) {
		cleanup(container, game);
	}
}
