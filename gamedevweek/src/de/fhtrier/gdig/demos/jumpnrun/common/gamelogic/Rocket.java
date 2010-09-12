package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import quicktime.std.movies.NextTaskNeededSooner;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.events.Event;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.PlayerDiedEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.events.WonGameEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
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
	protected float targetLastX = -1;
	protected float targetLastY = -1;

	// current path the Rocket is flying along
	protected Path path = null;
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
		assets.storeAnimation(Assets.Bullet.AnimId, Assets.Bullet.AnimPath);
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

		setBounds(new Rectangle(10, 28, 8, 8)); // bounding box

		CollisionManager.addEntity(this);

		if (bulletGlow == null) {
			bulletGlow = new Image(assets
					.getPathRelativeToAssetPath(Assets.Bullet.GlowImagePath));
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
	}

	public void shootAtClosestPlayer(RocketStrategy strategy) {

		switch (strategy) {
		case NEXT_ENEMY_TEAM:
			targetPlayer = getClosestPlayer((getData()[X] + 16)
					/ this.map.getTileWidth(), (getData()[Y] + 16)
					/ this.map.getTileHeight(), ANY_COLOR, owner
					.getPlayerCondition().getTeamId());
			break;
		}

		if (targetPlayer == null) {
			die();
			System.out.println();
			return;
		}

		// calculate path from nearest player to nearest enemy
		path = map.calculatePath(
				(int) ((getData()[X] + getData()[CENTER_X]) / map.getTileWidth() ), 
				(int) ((getData()[Y] + getData()[CENTER_Y]) / map.getTileHeight()), 
				(int) ((targetPlayer.getData()[X] + targetPlayer.getData()[X]) / map.getTileWidth()), 
				(int) (((targetPlayer.getData()[Y] + targetPlayer.getData()[Y]) / map.getTileHeight())));
		
		updateRocketData();

	}


	/* 
	 * overrider for different behavior
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
			path = map.calculatePath(
					(int) ((getData()[X] + getData()[CENTER_X]) / map.getTileWidth() ), 
					(int) ((getData()[Y] + getData()[CENTER_Y]) / map.getTileHeight()), 
					(int) ((targetPlayer.getData()[X] + targetPlayer.getData()[X]) / map.getTileWidth()), 
					(int) (((targetPlayer.getData()[Y] + targetPlayer.getData()[Y]) / map.getTileHeight())));
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
			if (path.getLength() > 0) {
				getData()[X] = path.getStep(0).getX() * map.getTileWidth();
				getData()[Y] = path.getStep(0).getY() * map.getTileHeight();
			}
			pathCounter++;
		}

		if (nextPathStep == null || (pathCounter + 1) < path.getLength()) {
			nextPathStep = path.getStep(pathCounter++);
		} else {
			path = null;
			die();
		}

		targetStep.x = nextPathStep.getX() * map.getTileWidth();
		targetStep.y = nextPathStep.getY() * map.getTileHeight();
		direction.x = targetStep.x - getData()[X];
		direction.y = targetStep.y - getData()[Y];
		direction.normalise();
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

					this.die();
				}
			}
		}

		return result;
	}

}
