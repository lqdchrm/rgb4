package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.entities.physics.CollidableEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public class Gem extends CollidableEntity {

	public Gem(int id, Factory factory) throws SlickException {
		super(id);

		AssetMgr assets = factory.getAssetMgr();

		// gfx
		assets.storeImage(Assets.GemImage, "sprites/items/gem.png");
		ImageEntity gem = factory.createImageEntity(Assets.GemImage, Assets.GemImage);
		
		add(gem);
		
		// physics
		// X Y OX OY SY SY ROT
		initData(new float[] { 200, 200, 32, 24, 1, 1, 0 }); // pos +
																	// center +
																	// scale +
																	// rot
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 981, 0, 0, 0, 0, 0 }); // gravity
		setBounds(new Rectangle(-32, 24, 64, 48)); // bounding box

		setVisible(true);
		setActive(true);

		// order
		setOrder(EntityOrder.Gem);
	}
}
