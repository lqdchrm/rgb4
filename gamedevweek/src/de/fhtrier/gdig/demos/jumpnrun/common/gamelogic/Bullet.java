package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.events.Event;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.PlayerDiedEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.events.WonGameEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.BulletData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
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
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;

public class Bullet extends LevelCollidableEntity {

	public Player owner;
	public AnimationEntity bullet;
	public AssetMgr assets;
	private static Image bulletGlow;
	public int color;
	private int flightTime;
	private final static float AMPLITUDE = 40;
	private final static float FREQUENCY = 0.8f;
	private float offset;
	private double startValue;

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public Bullet(int id, Factory factory) throws SlickException {
		super(id, EntityType.BULLET);

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
			bulletGlow = new Image(
					assets.getPathRelativeToAssetPath(Assets.Bullet.GlowImagePath));
		}

		// setup
		setVisible(true);
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
	public void update(int deltaInMillis) {
		getData()[Entity.Y] = getData()[Entity.Y] + offset * AMPLITUDE;
		super.update(deltaInMillis);
		offset = ((float)-Math.cos((Math.PI*2*(startValue + flightTime/1000.0f))*FREQUENCY)) + 1.0f;
		flightTime += deltaInMillis;
		getData()[Entity.Y] = getData()[Entity.Y] - offset * AMPLITUDE;
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
		Shader.activateAdditiveBlending();

		graphicContext.drawImage(bulletGlow, this.getData(CENTER_X)
				- bulletGlow.getWidth() / 2.0f, this.getData(CENTER_Y)
				- bulletGlow.getHeight() / 2.0f);

		Shader.activateDefaultBlending();

		if (Constants.Debug.shadersActive) {
			Shader.popShader();
		}
	}

	@Override
	protected void postRender(Graphics graphicContext) {
		super.postRender(graphicContext);
	}

	@Override
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

	private void die() {
		NetworkComponent.getInstance().sendCommand(
				new DoRemoveEntity(this.getId()));
		CollisionManager.removeEntity(this);
		level.remove(this);
		level.factory.removeEntity(this.getId(), true);
	}
}
