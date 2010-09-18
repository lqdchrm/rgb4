package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.entities.ParticleEntity;
import de.fhtrier.gdig.engine.graphics.shader.Shader;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public abstract class AbstractAssetState {

	private int stateId;
	private Player player;
	private AnimationEntity aGfxEntity;
	private AnimationEntity bGfxEntity;
	
	private AnimationEntity weaponGfxEntity;
	private ParticleEntity weaponParticles;
	
	public AbstractAssetState(int stateId, Player player, int playerAAnimAssetId, int playerBAnimAssetId, String aPlayerAnimAssetPath, String bPlayerAnimAssetPath, int weaponAnimAssetId, String weaponAnimAssetPath, int entityOrder, Factory factory) throws SlickException {

		this.stateId = stateId;
		
		AssetMgr assets = player.getAssetMgr();
		this.player = player;
		this.weaponParticles = player.getWeaponParticleEntity();
		
		Animation animAssetA = assets.storeAnimation(playerAAnimAssetId, aPlayerAnimAssetPath);
		Animation animAssetB = assets.storeAnimation(playerBAnimAssetId, bPlayerAnimAssetPath);
		
		initAnim(animAssetA);
		initAnim(animAssetB);
		
		AnimationEntity animA = factory.createAnimationEntity(Assets.Weapon.WeaponRenderOrder+1, playerAAnimAssetId, assets);
		AnimationEntity animB = factory.createAnimationEntity(Assets.Weapon.WeaponRenderOrder+1, playerBAnimAssetId, assets);
		
		animA.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		animA.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		animB.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		animB.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		Animation animWeaponAsset = assets.storeAnimation(weaponAnimAssetId, weaponAnimAssetPath);
		AnimationEntity weaponAnim = factory.createAnimationEntity(Assets.Weapon.WeaponRenderOrder, weaponAnimAssetId, assets);
		
		weaponAnim.getData()[Entity.CENTER_X] = player.getData()[Entity.CENTER_X];
		weaponAnim.getData()[Entity.CENTER_Y] = player.getData()[Entity.CENTER_Y];
		
		initAnim(animWeaponAsset);
		
		animA.setVisible(true);
		animB.setVisible(true);
		weaponAnim.setVisible(true);
	
		setGfxEntities(animA, animB, weaponAnim);
	}

	/**
	 * Used to initialize the animation, e.g. setting looping, auto-update etc. This is usually
	 * called directly by the constructor and should not be called by others
	 * 
	 * @param anim
	 */
	protected void initAnim(Animation anim) {
		
	}
	
	public abstract void enter();

	public abstract void leave();
	
	public abstract void update();

	public void render(Graphics g, Image frameBuffer) {

		weaponParticles.render(g, frameBuffer);

		Shader.pushShader(Player.getColorGlowShader());
//		Player.getColorGlowShader().setValue("playercolor", player.getWeaponColor());
		weaponGfxEntity.render(g, frameBuffer);
		Shader.popShader();
		
		getGfxEntity().render(g, frameBuffer);
	}
	
	public AnimationEntity getGfxEntity() {
		if (player.getPlayerCondition().getTeamId() == 1) {
			return aGfxEntity;
		}

		return bGfxEntity;
	}
		
	public AnimationEntity getWeaponGfxEntity() {
		return weaponGfxEntity;
	}
	
	public void setGfxEntities(AnimationEntity playerAGfxEntity, AnimationEntity playerBGfxEntity, AnimationEntity weaponGfxEntity) {
		this.aGfxEntity = playerAGfxEntity;
		this.bGfxEntity = playerBGfxEntity;
		this.weaponGfxEntity = weaponGfxEntity;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ParticleEntity getWeaponParticles () {
		return weaponParticles;
	}
	
	public int getStateId() {
		return stateId;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
