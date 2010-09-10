package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import javax.swing.text.AbstractDocument.Content;

//import org.GNOME.Accessibility.ContentStream;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public abstract class AbstractAssetState {

	private Factory factory;
	private Player player;
	private AssetEntity aGfxEntity;
	private AssetEntity bGfxEntity;
	
	private AssetEntity weaponGfxEntity;
	
	public AbstractAssetState(Player player, int playerAAnimAssetId, int playerBAnimAssetId, String aPlayerAnimAssetPath, String bPlayerAnimAssetPath, int weaponAnimAssetId, String weaponAnimAssetPath, int entityOrder, Factory factory) throws SlickException {

		AssetMgr assets = player.getAssetMgr();
		this.player = player;
		this.factory = factory;
		
		assets.storeAnimation(playerAAnimAssetId, aPlayerAnimAssetPath);
		assets.storeAnimation(playerBAnimAssetId, bPlayerAnimAssetPath);
		
		AnimationEntity animA = getFactory().createAnimationEntity(entityOrder, playerAAnimAssetId, assets);
		AnimationEntity animB = getFactory().createAnimationEntity(entityOrder, playerBAnimAssetId, assets);
		
		animA.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		animA.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		animB.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		animB.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		assets.storeAnimation(weaponAnimAssetId, weaponAnimAssetPath);
		AnimationEntity weaponAnim = getFactory().createAnimationEntity(entityOrder, weaponAnimAssetId, assets);
		
		weaponAnim.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		weaponAnim.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		animA.setOrder(Assets.Weapon.WeaponRenderOrder+1);
		animA.setVisible(true);
		animB.setOrder(Assets.Weapon.WeaponRenderOrder+1);
		animB.setVisible(true);
		weaponAnim.setVisible(true);
		weaponAnim.setOrder(Assets.Weapon.WeaponRenderOrder);
		
		
		setGfxEntity(animA, animB, weaponAnim);
	}

	public abstract void enter();

	public abstract void leave();
	
	public abstract void update();

	public Factory getFactory() {
		return factory;
	}

	public void render(Graphics g, Image frameBuffer) {
		weaponGfxEntity.render(g, frameBuffer);
		getGfxEntity().render(g, frameBuffer);
	}
	
	public AssetEntity getGfxEntity() {
		if (player.getPlayerCondition().teamId == 1)
			return aGfxEntity;
		return bGfxEntity;
	}
	
	public AssetEntity getAGfxEntity() {
		return aGfxEntity;
	}
	
	public AssetEntity getBGfxEntity() {
		return bGfxEntity;
	}
	
	public AssetEntity getWeaponGfxEntity() {
		return weaponGfxEntity;
	}
	
	public void setGfxEntity(AssetEntity playerAGfxEntity, AssetEntity playerBGfxEntity, AssetEntity weaponGfxEntity) {
		this.aGfxEntity = playerAGfxEntity;
		this.bGfxEntity = playerBGfxEntity;
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
