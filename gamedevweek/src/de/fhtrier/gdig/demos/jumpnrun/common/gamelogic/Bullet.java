package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.physics.entities.CollidableEntity;

public class Bullet extends LevelCollidableEntity
{

	public Player owner;
	public int color;
	private Level level;
	public AnimationEntity bullet;

	public Bullet(int id, Factory factory) throws SlickException
	{
		super(id, EntityType.BULLET);

		AssetMgr assets = factory.getAssetMgr();

		// gfx
		assets.storeAnimation(Assets.BulletAnimId, Assets.BulletAnimPath);
		bullet = factory.createAnimationEntity(EntityOrder.Bullet,
				Assets.BulletAnimId);

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

		setBounds(new Rectangle(0, 0, 48, 48)); // bounding box

		CollisionManager.addEntity(this);

		// setup
		setVisible(true);
	}

	@Override
	public boolean handleCollisions()
	{
		if (!isActive())
		{
			return false;
		}
		boolean result = super.handleCollisions();

		if (result)
		{
			die();
			return result;
		}

		List<CollidableEntity> iColideWith = CollisionManager
				.collidingEntities(this);

		for (CollidableEntity collidableEntity : iColideWith)
		{
			if (collidableEntity instanceof Player)
			{
				Player otherPlayer = (Player) collidableEntity;
				if (otherPlayer != owner)
				{
					otherPlayer.doDamage(this.color,
							owner.getPlayerCondition().damage);
					die();
				}
			}
		}

		return result;
	}

	private void die()
	{
		NetworkComponent.getInstance().sendCommand(
				new DoRemoveEntity(this.getId()));
		CollisionManager.removeEntity(this);
		level.remove(this);
		level.factory.removeEntity(this.getId(), true);
	}

	public void setLevel(Level level)
	{
		super.setLevel(level);
		this.level = level;
	}
}
