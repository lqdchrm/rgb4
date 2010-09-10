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
	private AssetEntity weaponGfxEntity;
	
	public AbstractAssetState(Player player, int playerAnimAssetId, String aPlayerAnimAssetPath, String bPlayerAnimAssetPath, int weaponAnimAssetId, String weaponAnimAssetPath, int entityOrder, Factory factory) throws SlickException {

		AssetMgr assets = player.getAssetMgr();
		this.player = player;
		this.factory = factory;
		
		// gfx
		if (player.getPlayerCondition().teamId == 1)
			assets.storeAnimation(playerAnimAssetId, aPlayerAnimAssetPath);
		else
			assets.storeAnimation(playerAnimAssetId, bPlayerAnimAssetPath);
		
		AnimationEntity anim = getFactory().createAnimationEntity(entityOrder, playerAnimAssetId, assets);
		
		anim.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		anim.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		assets.storeAnimation(weaponAnimAssetId, weaponAnimAssetPath);
		AnimationEntity weaponAnim = getFactory().createAnimationEntity(entityOrder, weaponAnimAssetId, assets);
		
		weaponAnim.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		weaponAnim.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		anim.setVisible(true);
		weaponAnim.setVisible(true);
		setGfxEntity(anim, weaponAnim);
	}

	public abstract void enter();

	public abstract void leave();
	
	public abstract void update();

	public Factory getFactory() {
		return factory;
	}

	public void render(Graphics g, Image frameBuffer) {
		gfxEntity.render(g, frameBuffer);
		weaponGfxEntity.render(g, frameBuffer);
	}
	
	public AssetEntity getGfxEntity() {
		return gfxEntity;
	}
	
	public AssetEntity getWeaponGfxEntity() {
		return weaponGfxEntity;
	}
	
	public void setGfxEntity(AssetEntity playerGfxEntity, AssetEntity weaponGfxEntity) {
		this.gfxEntity = playerGfxEntity;
		this.weaponGfxEntity = weaponGfxEntity;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
