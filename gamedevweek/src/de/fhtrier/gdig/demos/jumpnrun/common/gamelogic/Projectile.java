package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.List;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;

public class Projectile extends LevelCollidableEntity {

	public Player owner;
	public int color;

	public Projectile(int id, EntityType type) {
		super(id, type);
	}
	
	protected void die() {
		NetworkComponent.getInstance().sendCommand(
				new DoRemoveEntity(this.getId()));
		CollisionManager.removeEntity(this);
		level.remove(this);
		level.factory.removeEntity(this.getId(), true);
	}
	
	@Override
	public boolean handleCollisions() {
		if (!isActive()) {
			return false;
		}
		
		// check for tile collision
		boolean result = super.handleCollisions();

		if (result) {
			die();
			return result;
		}

		// check for entity collision
		List<CollidableEntity> iColideWith = CollisionManager
				.collidingEntities(this);

		// flag telling if bullet should be removed
		boolean doRemoveBullet = false;
		
		for (CollidableEntity collidableEntity : iColideWith) {
			if (collidableEntity instanceof Player) {
				Player hitPlayer = (Player) collidableEntity;
				
				// don't collide with owner and teammates
				if (hitPlayer != owner &&
					(Constants.GamePlayConstants.friendlyFire == true ||
					 owner.getPlayerCondition().getTeamId() != hitPlayer.getPlayerCondition().getTeamId())) {
					hitPlayer.doDamage(color, owner.getPlayerCondition().getDamage(), owner);
					doRemoveBullet = true;
				}
			}
		}
		
		if (doRemoveBullet) {
			this.die();
		}

		return result;
	}
}
