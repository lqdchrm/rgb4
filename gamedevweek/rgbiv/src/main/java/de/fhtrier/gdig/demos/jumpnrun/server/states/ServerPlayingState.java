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
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Rocket;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Rocket.RocketStrategy;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.QueryRespawn;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.states.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.NetworkConfig;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.ServerData;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendPlayerCondition;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.helpers.AStarTiledMap;
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

		SoundManager.playSound(Assets.Sounds.PlayerJoiningSoundID);
		SoundManager.loopMusic(Assets.Sounds.LevelSoundtrackId, 1.0f, 0f);
		SoundManager.fadeMusic(Assets.Sounds.LevelSoundtrackId, 5000, 0.2f,
				false);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		
		// Level
		getLevel().init(true);
		
		// HACK 
		NetworkConfig.isServer = true;
	}

	private boolean handlePlayerActions(QueryAction actionCmd) {
		Entity e;
		switch (actionCmd.getAction()) {
		case SHOOT:
			for(int i = 0; i < 2 ; i++) {
			e = createEntity(EntityType.BULLET, this.levelId);

			// set values
			Bullet bullet = (Bullet) e;
			int playerId = networkId2Player.get(actionCmd.getSender());
			Player player = (Player) getFactory().getEntity(playerId);

			bullet.owner = player;
			bullet.color = player.getWeaponColor();

			// set player pos as gem pos
			bullet.getData()[Entity.X] = (player.getData()[Entity.X] + player
					.getData()[Entity.CENTER_X])
					+ (bullet.getData()[Entity.CENTER_X] - Assets.Weapon.weaponXOffset)
					* player.getData()[Entity.SCALE_X];

			bullet.getData()[Entity.Y] = player.getData()[Entity.Y]
					+ player.getData()[Entity.CENTER_Y]
					- bullet.getData()[Entity.CENTER_Y]
					+ Assets.Weapon.weaponYOffset;

			bullet.getVel()[Entity.X] = player.getVel()[Entity.X]
					+ (player.getData()[Entity.SCALE_X] == -1 ? Constants.GamePlayConstants.shotSpeed
							: -Constants.GamePlayConstants.shotSpeed);
			
			if (player.getData()[Entity.SCALE_X] == -1) // Right
				bullet.getData()[Entity.SCALE_X] = -1;


			else if (player.getData()[Entity.SCALE_X] == 1) // Left
				bullet.getData()[Entity.SCALE_X] = 1;
	
	if (i == 0) {
				bullet.setStartValue(0.0);
			} else {
				bullet.setStartValue(Math.PI);
			}
			}

			return true;
		case SHOOT_ROCKET:
			e = createEntity(EntityType.ROCKET, this.levelId);

			// set values
			Rocket rocket = (Rocket) e;
			int playerId = networkId2Player.get(actionCmd.getSender());
			Player player = (Player) getFactory().getEntity(playerId);
			rocket.owner = player;
			rocket.astarmap = (AStarTiledMap)getLevel().getMap();
			
			rocket.color = player.getWeaponColor();
			// set player pos as gem pos

			rocket.getData()[Entity.X] = player.getData()[Entity.X] + player.getData()[Entity.CENTER_X];
			rocket.getData()[Entity.Y] = player.getData()[Entity.Y] + player.getData()[Entity.CENTER_Y];
			rocket.getData()[Entity.X] =
				(player.getData()[Entity.X] + player.getData()[Entity.CENTER_X]) +
				(rocket.getData()[Entity.CENTER_X] - Assets.Weapon.weaponXOffset) * player.getData()[Entity.SCALE_X];

			rocket.getData()[Entity.Y] =
			player.getData()[Entity.Y] + player.getData()[Entity.CENTER_Y] -
			rocket.getData()[Entity.CENTER_Y] + Assets.Weapon.weaponYOffset;
			

			
			rocket.shootAtClosestPlayer(RocketStrategy.NEXT_ENEMY_TEAM);
			return true;
		case RESPAWN:
			if (actionCmd instanceof QueryRespawn) {
				QueryRespawn tmpCmd = (QueryRespawn) actionCmd;

				int tmpId = tmpCmd.getPlayerId();
				Player tmpPlayer = (Player) getFactory().getEntity(tmpId);

				tmpPlayer.getPlayerCondition().setHealth(
						Constants.GamePlayConstants.initialPlayerHealth);
			}
			break;
		case QUERYPLAYERCONDITION:
			// HACK query for any player
			if (actionCmd instanceof QueryPlayerCondition) {

				QueryPlayerCondition tmpCmd = (QueryPlayerCondition) actionCmd;

				int tmpId = tmpCmd.getPlayerId();
				Player tmpPlayer = (Player) getFactory().getEntity(tmpId);

				NetworkComponent.getInstance().sendCommand(
						actionCmd.getSender(),
						new SendPlayerCondition(tmpId, tmpPlayer
								.getPlayerCondition()));

				return true;
			}
			return false;
		}

		return false;
	}

	private Entity createEntity(EntityType type, int parentId) {
		int id = this.getFactory().createEntity(type);

		Entity e = getFactory().getEntity(id);

		e.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
		e.setActive(true);

		//
		if (parentId > -1) {
			Entity parent = getFactory().getEntity(parentId);
			parent.add(e);
		}

		// send command to all clients to create gem
		NetworkComponent.getInstance()
				.sendCommand(new DoCreateEntity(id, parentId, type, false));
		return e;
	}

	private boolean handleProtocolCommands(INetworkCommand cmd) {

		// QueryJoin
		if (cmd instanceof QueryJoin) {

			// create every (player) entity from server on client
			for (Entity e : getFactory().getEntities()) {
				if (e.getUpdateStrategy() == EntityUpdateStrategy.ServerToClient) {
					NetworkComponent.getInstance().sendCommand(cmd.getSender(),
							new DoCreateEntity(e.getId(), this.levelId, e.getType(), true));
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

				Player e = (Player) getFactory().getEntity(id);
				e.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
				getLevel().add(e);

				// send command to all clients to create entity
				NetworkComponent.getInstance().sendCommand(
						new DoCreateEntity(id, this.levelId, EntityType.PLAYER, false));

				// if query requested new player assume that's the one to be
				// controlled by client
				NetworkComponent.getInstance().sendCommand(cmd.getSender(),
						new AckCreateEntity(id));

				// remember, which networkId identifies which player
				networkId2Player.put(cmd.getSender(), id);
				player2NetworkId.put(id, cmd.getSender());

				String name = ServerLobbyState.players.get(cmd.getSender())
						.getPlayerName();

				int teamID = ServerLobbyState.players.get(cmd.getSender())
						.getTeamId();

				e.getPlayerCondition().setName(name);
				e.getPlayerCondition().setTeamId(teamID);

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
