package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public class Teleporter extends Entity {

	public AssetMgr assets;
	public AnimationEntity teleporterAnimation;

	public Teleporter(int id, Factory factory) throws SlickException {
		super(id, EntityType.TELEPORTER);
		assets = new AssetMgr();

		// gfx
		assets.storeAnimation(Assets.Level.Teleporter.TeleporterId,
				Assets.Level.Teleporter.TeleporterAnimationPath);
		teleporterAnimation = factory.createAnimationEntity(
				EntityOrder.LevelObject, Assets.Level.Teleporter.TeleporterId,
				assets);
		teleporterAnimation.getData()[Entity.X] = -teleporterAnimation
				.getAssetMgr()
				.getAnimation(Assets.Level.Teleporter.TeleporterId).getImage(0)
				.getWidth() / 2.0f;
		teleporterAnimation.getData()[Entity.Y] = -teleporterAnimation
				.getAssetMgr()
				.getAnimation(Assets.Level.Teleporter.TeleporterId).getImage(0)
				.getHeight() / 2.0f;

		teleporterAnimation.setVisible(true);
		add(teleporterAnimation);

		// setup
		setVisible(true);
	}

}
