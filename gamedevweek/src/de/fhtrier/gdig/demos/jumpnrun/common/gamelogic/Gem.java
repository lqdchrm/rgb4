package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.graphics.entities.ImageEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.physics.CollisionManager;

public class Gem extends LevelCollidableEntity {

	public Gem(int id, Factory factory) throws SlickException {
		super(id, EntityType.GEM);

		AssetMgr assets = factory.getAssetMgr();

		// gfx
		assets.storeImage(Assets.GemImageId, "sprites/items/gem.png");
		ImageEntity gem = factory.createImageEntity(EntityOrder.Gem,
				Assets.GemImageId);
		gem.setVisible(true);
		add(gem);

		// physics
		// X Y OX OY SY SY ROT
		initData(new float[] { 200, 200, 32, 24, 1, 1, 0 }); // pos +		200 200 32 24 1 1 0
																// center +
																// scale +
																// rot
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, GamePlayConstants.gravity, 0, 0, 0, 0, 0 }); // gravity
		setBounds(new Rectangle(10, 5, 44, 38)); // bounding box

		CollisionManager.addEntity(this);
		
		// setup
		setVisible(true);

		// order
		setOrder(EntityOrder.Gem);
	}
}
