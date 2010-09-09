package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryPlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.common.events.Event;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.PlayerDiedEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.events.WonGameEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckPlayerCondition;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendKill;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendWon;
import de.fhtrier.gdig.demos.jumpnrun.server.states.ServerPlayingState;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;

public class Bullet extends LevelCollidableEntity {

	public Player owner;
	public int color;
	public AnimationEntity bullet;
	public AssetMgr assets;

	public Bullet(int id, Factory factory) throws SlickException {
		super(id, EntityType.BULLET);

		assets = new AssetMgr();

		// gfx
		assets.storeAnimation(Assets.BulletAnimId, Assets.BulletAnimPath);
		bullet = factory.createAnimationEntity(EntityOrder.Bullet,
				Assets.BulletAnimId, assets);

		bullet.setVisible(true);
		add(bullet);
		
		// physics
		// X Y OX OY SX SY ROT
		initData(new float[] { 200, 200, 24, 24, 1, 1, 0 }); // pos +
																// center +
																// scale +
																// rot

		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // gravity

		setBounds(new Rectangle(10, 28, 8, 8)); // bounding box

		CollisionManager.addEntity(this);

		// setup
		setVisible(true);
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
				if (otherPlayer != owner && otherPlayer.getPlayerCondition().health > 0.01f) {
					if (otherPlayer.getPlayerCondition().color != this.color) {
						otherPlayer.getPlayerCondition().health -= owner
								.getPlayerCondition().damage;
						

						if (otherPlayer.getPlayerCondition().health <= 0.01f) {
							NetworkComponent.getInstance().sendCommand(new SendKill(otherPlayer.getId(),owner.getId()));
							
							Event dieEvent = new PlayerDiedEvent(otherPlayer,owner);
							dieEvent.update();
						}
						
						if (owner.getPlayerStats().getKills() >= Constants.winningKills) {
							NetworkComponent.getInstance().sendCommand(new SendWon(owner.getId()));
							
							Event wonEvent = new WonGameEvent (owner);
							EventManager.addEvent(wonEvent);
						}
					} else {
						// player gets stronger when hit by bullet of the same
						// color!
						otherPlayer.getPlayerCondition().health += owner
								.getPlayerCondition().damage;
					}
					
					NetworkComponent.getInstance().sendCommand(new AckPlayerCondition(otherPlayer.getId(), otherPlayer.getPlayerCondition()));
					
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
