package de.fhtrier.gdig.demos.jumpnrun.common;

import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoRemoveEntity;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.entities.physics.CollidableEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.NetworkCommand;

public class Bullet extends LevelCollidableEntity{

	public Player owner;
	public int color;
	private Level level;
	
	public Bullet(int id, Factory factory) throws SlickException {
		super(id);
		

		AssetMgr assets = factory.getAssetMgr();

		// gfx 
		assets.storeImage(Assets.BulletImage, "sprites/items/gem.png");
		ImageEntity bullet = factory.createImageEntity(EntityOrder.Bullet, Assets.BulletImage);
		bullet.setVisible(true);
		add(bullet);
		
		// physics
		// X Y OX OY SY SY ROT
		initData(new float[] { 200, 200, 32, 24, 1, 1, 0 }); // pos +
																	// center +
																	// scale +
																	// rot
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // gravity
		setBounds(new Rectangle(0, 0, 64, 48)); // bounding box

		// setup
		setVisible(true);

		// order
		setOrder(EntityOrder.Bullet);
	}
	
	@Override
	public boolean handleCollisions() {
		if (!isActive())
		{
			return false;
		}
		boolean result = super.handleCollisions();
		
		List<CollidableEntity> iColideWith = CollisionManager.iColideWith(this);
		
		for (CollidableEntity collidableEntity : iColideWith) {
			if (collidableEntity instanceof Player)
			{
				Player otherPlayer = (Player) collidableEntity;
				if (otherPlayer != owner)
				{
					//TODO: damage player
					
					NetworkComponent.getInstance().sendCommand(new DoRemoveEntity(this.getId()));
					level.remove(this);
					level.factory.removeEntity(this.getId(), true);
				}
			}
		}
		
		return result;
	}
	
	@Override
	public void setLevel(Level level) {
		super.setLevel(level);
		this.level = level;
	}
}
