package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public abstract class AbstractAssetState {

	private Factory factory;
	private Player player;
	private AssetEntity gfxEntity;

	public AbstractAssetState(Player player, int animAssetId,
			String animAssetPath, int entityOrder, Factory factory)
			throws SlickException {

		AssetMgr assets = player.getAssetMgr();
		this.player = player;
		this.factory = factory;

		// gfx
		assets.storeAnimation(animAssetId, animAssetPath);
		AnimationEntity anim = getFactory().createAnimationEntity(entityOrder,
				animAssetId, assets);

		anim.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		anim.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];

		anim.setVisible(true);
		setGfxEntity(anim);
	}

	public abstract void enter();

	public abstract void leave();

	public abstract void update();

	public Factory getFactory() {
		return factory;
	}

	public void render(Graphics g, Image frameBuffer) {
		gfxEntity.render(g, frameBuffer);
	}

	public AssetEntity getGfxEntity() {
		return gfxEntity;
	}

	public void setGfxEntity(AssetEntity gfxEntity) {
		this.gfxEntity = gfxEntity;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
