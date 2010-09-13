package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.events.Event;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.PlayerDiedEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.events.WonGameEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.BulletData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.states.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendKill;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendWon;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.shader.Shader;
import de.fhtrier.gdig.engine.helpers.AStarTiledMap;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;

public class Rocket extends CollidableEntity {

	public enum RocketStrategy {
		NEXT_ENEMY_TEAM
	}

	public static final int ANY_COLOR = -1;
	public static final int ANY_TEAM = -1;

	// fields set after entity-creation
	public AStarTiledMap map = null;
	public Player owner = null;
	
	// data about the target
	protected Player targetPlayer = null;

	// current path the Rocket is flying along
	protected Path path = null;
	protected Path newPathFromThread = null;
	protected Step nextPathStep = null;
	protected int pathCounter = 0;
	protected Vector2f direction = new Vector2f();
	protected Vector2f targetStep = new Vector2f();

	protected Factory factory;

	// used for checking distance
	private Vector2f tmpVec2 = new Vector2f();
	private Vector2f tmp2Vec2 = new Vector2f();
	public AnimationEntity bullet;
	public AssetMgr assets;
	private static Image bulletGlow;
	public int color;
	

	public Rocket(int id, GameFactory factory) throws SlickException {
		super(id, EntityType.ROCKET);
		this.factory = factory;
		assets = new AssetMgr();

		// gfx
		assets.storeAnimation(Assets.Bullet.AnimId, Assets.Rocket.AnimPath);
		bullet = factory.createAnimationEntity(EntityOrder.Bullet,
				Assets.Bullet.AnimId, assets);

		bullet.setVisible(true);
		add(bullet);

		// physics
		// X Y OX OY SX SY ROT
		initData(new float[] { 200, 200, 14, 32, 1, 1, 0 }); // pos +
		// center +
		// scale +
		// rot

		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // gravity

		setBounds(new Rectangle(16, 16, 32, 32)); // bounding box

		CollisionManager.addEntity(this);

		if (bulletGlow == null) {
			bulletGlow = new Image(assets
					.getPathRelativeToAssetPath(Assets.Rocket.GlowImagePath));
		}

		// setup
		setVisible(true);
	}

	/**
	 * 
	 * gets the player that is closest to the given point (x,y)
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param color
	 *            the color the player have to have (-1 for any)
	 * @param teamId
	 *            the teamId the player must NOT have (-1 for any)
	 * @return
	 */
	public Player getClosestPlayer(float x, float y, int color, int teamId) {
		Player closestPlayer = null;
		float nearestDistance = -1;
		tmpVec2.set(x, y);

		for (Entity entity : factory.getEntities()) {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				if (color == -1 || player.getPlayerColor() == color) {
					if (teamId == -1
							|| player.getPlayerCondition().getTeamId() != teamId) {
						tmp2Vec2.set(player.getData()[X], player.getData()[Y]);
						float distance = tmpVec2.distanceSquared(tmp2Vec2);
						if (closestPlayer == null || nearestDistance > distance) {
							nearestDistance = distance;
							closestPlayer = player;
						}
					}
				}
			}
		}
		return closestPlayer;
	}

	private void die() {
		NetworkComponent.getInstance().sendCommand(
				new DoRemoveEntity(this.getId()));
		CollisionManager.removeEntity(this);
		Log.debug("ROCKET DIED");
	}

	public void shootAtClosestPlayer(RocketStrategy strategy) {

		switch (strategy) {
		case NEXT_ENEMY_TEAM:
			targetPlayer = getClosestPlayer((getData()[X] + getData()[CENTER_X])
					/ this.map.getTileWidth(), (getData()[Y] + getData()[CENTER_Y])
					/ this.map.getTileHeight(), ANY_COLOR, owner
					.getPlayerCondition().getTeamId());
			break;
		}

		if (targetPlayer == null) {
			die();
			System.out.println();
			return;
		}

		calculatePathToCurrentTarget();
		updateRocketData();

	}


	public void calculatePathToCurrentTarget()
	{
		path = map.calculatePath(
				(int) ((getData()[X] + bulletGlow.getWidth()/2) / map.getTileWidth() ), 
				(int) ((getData()[Y] + bulletGlow.getHeight()/2) / map.getTileHeight()), 
				(int) ((targetPlayer.getData()[X] + targetPlayer.getData()[CENTER_X]) / map.getTileWidth()), 
				(int) ((targetPlayer.getData()[Y] + targetPlayer.getData()[CENTER_Y]) / map.getTileHeight()));
	}
	
	/* 
	 * overrider for different behaviors
	 * here: after reaching each path-step check the target players position
	 * and recalculate path. following always the SAME target player
	 */
	public void doStrategy()
	{
		float targetX = targetPlayer.getData()[X] + targetPlayer.getData()[CENTER_X];   
		float targetY = targetPlayer.getData()[Y] + targetPlayer.getData()[CENTER_Y];
		int tileTargetX = (int)(targetX / map.getTileWidth());
		int tileTargetY = (int)(targetY / map.getTileHeight());
		
		// did the target leave the path? 
		if (!path.contains(tileTargetX,tileTargetY))
		{
			// TODO: Outsource this to a special low-priortiy calculation thread if performance is bad
			calculatePathToCurrentTarget();
			pathCounter=0;
			nextPathStep=null;
		}
	}
	
	// get next step from path
	public void updateRocketData() {
		// did the target move?
		doStrategy();
		
		if (path == null)
			return;

		if (nextPathStep == null) {
			pathCounter=1;
		}

		if (nextPathStep == null || (pathCounter < path.getLength())) {
			nextPathStep = path.getStep(pathCounter);
			pathCounter++;
		} else {
			path = null;
			die();
		}

		targetStep.x = nextPathStep.getX() * map.getTileWidth();
		targetStep.y = nextPathStep.getY() * map.getTileHeight();
		direction.x = targetStep.x - getData()[X];
		direction.y = targetStep.y - getData()[Y];
		direction.normalise();
		getVel()[X] = Constants.GamePlayConstants.shotSpeed * direction.x / 10;
		getVel()[Y] = Constants.GamePlayConstants.shotSpeed * direction.y / 10;

	}

	@Override
	public void update(int deltaInMillis) {
		super.update(deltaInMillis);

		if (path == null)
			return;

		if (direction.x > 0 && getData()[X] > targetStep.x) {
			updateRocketData();
		} else if (direction.x < 0 && getData()[X] < targetStep.x) {
			updateRocketData();
		}

		if (direction.y > 0 && getData()[Y] > targetStep.y) {
			updateRocketData();
		} else if (direction.y < 0 && getData()[Y] < targetStep.y) {
			updateRocketData();
		}
		
//		handleCollisions();
	}

	@Override
	protected void postRender(Graphics graphicContext) {
		super.postRender(graphicContext);
		if (Constants.Debug.showDebugOverlay) {
			if (path != null) {
				for (int i = 0; i < path.getLength(); i++) {
					Step step = path.getStep(i);
					graphicContext.draw(new Rectangle(step.getX()
							* map.getTileWidth(), step.getY()
							* map.getTileHeight(), 32, 32));
				}
			}
		}
	}
	
	@Override
	protected NetworkData _createNetworkData() {
		return new BulletData(getId());
	}

	@Override
	public NetworkData getNetworkData() {
		BulletData result = (BulletData) super.getNetworkData();
		result.bulletColor = this.color;

		return result;
	}	

	@Override
	public void applyNetworkData(NetworkData networkData) {
		super.applyNetworkData(networkData);

		if (networkData instanceof BulletData) {
			this.color = ((BulletData) networkData).getColor();
		} else {
			throw new RuntimeException("Wrong package received");
		}
	}
	
	// this is for the moment more or less the logic of the bullet but without checking for 
	// level-collisions which somehow prevent the pathfinder to work properly. (and as long
	// as the pathfinder is working that should(<---) be fine
	public boolean handleCollisions() {
		if (!isActive()) {
			return false;
		}
		boolean result = super.handleCollisions();

		if (result) {
			die();
			return result;
		}

		List<CollidableEntity> iColideWith = CollisionManager
				.collidingEntities(this);

		for (CollidableEntity collidableEntity : iColideWith) {
			if (collidableEntity instanceof Player) {
				Player otherPlayer = (Player) collidableEntity;
				if (otherPlayer != owner
						&& otherPlayer.getPlayerCondition().getHealth() > Constants.EPSILON
						&& (Constants.GamePlayConstants.friendyFire == true || // Friendly
																				// Fire
																				// or
						owner.getPlayerCondition().getTeamId() != otherPlayer
								.getPlayerCondition().getTeamId())) // Enemy
				{
					if (otherPlayer.getPlayerColor() != this.color) {
						otherPlayer.getPlayerCondition().setHealth(
								otherPlayer.getPlayerCondition().getHealth() - owner
										.getPlayerCondition().getDamage());

						if (otherPlayer.getPlayerCondition().getHealth() <= Constants.EPSILON) {
							NetworkComponent.getInstance().sendCommand(
									new SendKill(otherPlayer.getId(), owner
											.getId()));

							Event dieEvent = new PlayerDiedEvent(otherPlayer,
									owner);
							EventManager.addEvent(dieEvent);
						}

						if (PlayingState.gameType == Constants.GameTypes.deathMatch) {
							if (owner.getPlayerCondition().getKills() >= Constants.GamePlayConstants.winningKills_Deathmatch) {
								NetworkComponent.getInstance().sendCommand(
										new SendWon(owner.getId(),
												SendWon.winnerType_Player));

								Event wonEvent = new WonGameEvent(owner);
								EventManager.addEvent(wonEvent);
							}
						} else if (PlayingState.gameType == Constants.GameTypes.teamDeathMatch) {
							// TODO: do it not hardcoded
							if (Team.team1.getKills() >= Constants.GamePlayConstants.winningKills_TeamDeathmatch) {
								NetworkComponent.getInstance().sendCommand(
										new SendWon(Team.team1.id,
												SendWon.winnerType_Team));

								Event wonEvent = new WonGameEvent(Team.team1);
								EventManager.addEvent(wonEvent);
							} else if (Team.team2.getKills() >= Constants.GamePlayConstants.winningKills_TeamDeathmatch) {
								NetworkComponent.getInstance().sendCommand(
										new SendWon(Team.team2.id,
												SendWon.winnerType_Team));

								Event wonEvent = new WonGameEvent(Team.team1);
								EventManager.addEvent(wonEvent);
							}
						}
					} else {
						// player gets stronger when hit by bullet of the same
						// color!
						otherPlayer.getPlayerCondition().setHealth(
								otherPlayer.getPlayerCondition().getHealth() + (owner
										.getPlayerCondition().getDamage() / 2));
						if (otherPlayer.getPlayerCondition().getHealth() > Constants.GamePlayConstants.maxPlayerHealth)
							otherPlayer.getPlayerCondition().setHealth(Constants.GamePlayConstants.maxPlayerHealth);
					}


				}
			}
			this.die();
		}

		return result;
	}
	
	@Override
	protected void preRender(Graphics graphicContext) {
		super.preRender(graphicContext);

		Color bulletCol = StateColor.constIntoColor(this.color);

		if (Constants.Debug.shadersActive) {
			Shader.pushShader(Player.getColorGlowShader());
			Player.getColorGlowShader().setValue("playercolor", bulletCol);
		}

		graphicContext.setColor(Color.white);
		graphicContext.drawImage(bulletGlow, 0,0);

		if (Constants.Debug.shadersActive) {
			Shader.popShader();
		}
	}
	

}
