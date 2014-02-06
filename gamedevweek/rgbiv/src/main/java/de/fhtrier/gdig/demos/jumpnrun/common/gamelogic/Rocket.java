package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.RocketDiedEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.BulletData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.shader.Shader;
import de.fhtrier.gdig.engine.helpers.AStarTiledMap;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.physics.CollisionManager;

public class Rocket extends Projectile {

	public enum RocketStrategy {
		NEXT_ENEMY_TEAM
	}

	public static final int ANY_COLOR = -1;
	public static final int ANY_TEAM = -1;

	// fields set after entity-creation
	public AStarTiledMap astarmap = null;

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
			bulletGlow = new Image(
					assets.getPathRelativeToAssetPath(Assets.Rocket.GlowImagePath));
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

	public void shootAtClosestPlayer(RocketStrategy strategy) {

		switch (strategy) {
		case NEXT_ENEMY_TEAM:
			targetPlayer = getClosestPlayer(
					(getData()[X] + getData()[CENTER_X])
							/ this.astarmap.getTileWidth(),
					(getData()[Y] + getData()[CENTER_Y])
							/ this.astarmap.getTileHeight(), ANY_COLOR, owner
							.getPlayerCondition().getTeamId());
			break;
		}

		if (targetPlayer == null) {
			die();
			return;
		}

		calculatePathToCurrentTarget();
		updateRocketData();
	}

	public void calculatePathToCurrentTarget() {
		path = astarmap
				.calculatePath(
						(int) ((getData()[X] + bulletGlow.getWidth() / 2.0f) / astarmap
								.getTileWidth()),
						(int) ((getData()[Y] + bulletGlow.getHeight() / 2.0f) / astarmap
								.getTileHeight()),
						(int) ((targetPlayer.getData()[X] + targetPlayer
								.getData()[CENTER_X]) / astarmap.getTileWidth()),
						(int) ((targetPlayer.getData()[Y] + targetPlayer
								.getData()[CENTER_Y]) / astarmap.getTileHeight()));
	}

	/*
	 * overrider for different behaviors here: after reaching each path-steprocket
	 * check the target players position and recalculate path. following always
	 * the SAME target player
	 */
	public void doStrategy() {
		float targetX = targetPlayer.getData()[X]
				+ targetPlayer.getData()[CENTER_X];
		float targetY = targetPlayer.getData()[Y]
				+ targetPlayer.getData()[CENTER_Y];
		int tileTargetX = (int) (targetX / astarmap.getTileWidth());
		int tileTargetY = (int) (targetY / astarmap.getTileHeight());

		// did the target leave the path?
		if (!path.contains(tileTargetX, tileTargetY)) {
			// TODO: Outsource this to a special low-priortiy calculation thread
			// if performance is bad
			calculatePathToCurrentTarget();
			pathCounter = 0;
			nextPathStep = null;
		}
	}

	// get next step from path
	public void updateRocketData() {
		// did the target move?
		doStrategy();

		if (path == null)
			return;

		if (nextPathStep == null) {
			pathCounter = 1;
		}

		if ((nextPathStep == null || (pathCounter < path.getLength()))) {
			nextPathStep = path.getStep(pathCounter);
			pathCounter++;
		} else {
			path = null;
			EventManager.addEvent(new RocketDiedEvent(this));
		}

		targetStep.x = nextPathStep.getX() * astarmap.getTileWidth();
		targetStep.y = nextPathStep.getY() * astarmap.getTileHeight();
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
	}

	@Override
	protected void postRender(Graphics graphicContext) {
		super.postRender(graphicContext);
		if (Constants.Debug.showDebugOverlay) {
			if (path != null) {
				for (int i = 0; i < path.getLength(); i++) {
					Step step = path.getStep(i);
					graphicContext.draw(new Rectangle(step.getX()
							* astarmap.getTileWidth(), step.getY()
							* astarmap.getTileHeight(), 32, 32));
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

	@Override
	protected void preRender(Graphics graphicContext) {
		super.preRender(graphicContext);

		Color bulletCol = StateColor.constIntoColor(this.color);

		if (Constants.Debug.shadersActive) {
			Shader.pushShader(Player.getColorGlowShader());
			Player.getColorGlowShader().setValue("playercolor", bulletCol);
		}

		graphicContext.setColor(Color.white);
		graphicContext.drawImage(bulletGlow, 0, 0);

		if (Constants.Debug.shadersActive) {
			Shader.popShader();
		}
	}
	
	@Override
	public void die() {
		super.die();
	}
}
