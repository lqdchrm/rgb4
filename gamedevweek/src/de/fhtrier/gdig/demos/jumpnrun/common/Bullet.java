package de.fhtrier.gdig.demos.jumpnrun.common;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.common.events.*;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.entities.gfx.AnimationEntity;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.entities.physics.CollidableEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;

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
		//sch�sse als bild
			/*assets.storeImage(Assets.BulletAnim, "sprites/items/stern.png");					
			ImageEntity bullet = factory.createImageEntity(EntityOrder.Bullet,
					Assets.BulletAnim);
			*/
		
		//sch�sse als animation
		Animation animation = assets.storeAnimation(Assets.BulletAnimId, Assets.BulletAnimPath);
		bullet = factory.createAnimationEntity(Assets.BulletAnimId,
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

		// order
		setOrder(EntityOrder.Bullet);
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
					if (otherPlayer.getState().color != this.color)
					{
						otherPlayer.getState().health -= owner.getState().damage;
						
						System.out.println(otherPlayer.getState().health);
						
						if (otherPlayer.getState().health <= 0.0f) {
							System.out.println("Player died!!!!!!!!!!!!!!!");
							Event dieEvent = new PlayerDiedEvent (otherPlayer);
							EventManager.addEvent(dieEvent);
						}
					}
					else
					{
						otherPlayer.getState().health += owner.getState().damage; 
						// player becomes stronger when hit by bullet of the same color!
					}
					die();
				}
			}
		}

		return result;
	}

	@Override
	public void update(int deltaInMillis)
	{
		// TODO Auto-generated method stub
		super.update(deltaInMillis);
	}

	private void die()
	{
		NetworkComponent.getInstance().sendCommand(
				new DoRemoveEntity(this.getId()));
		CollisionManager.removeEntity(this);
		level.remove(this);
		level.factory.removeEntity(this.getId(), true);
	}

	@Override
	public void setLevel(Level level)
	{
		super.setLevel(level);
		this.level = level;
	}
}
