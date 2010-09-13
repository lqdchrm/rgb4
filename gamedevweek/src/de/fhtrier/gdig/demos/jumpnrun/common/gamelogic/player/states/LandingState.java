package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class LandingState extends AbstractAssetState {
	
	private Animation anim;
	private Animation weaponAnim;
	
	public LandingState(int stateId, Player player, Factory factory)
			throws SlickException {
		super(stateId, player, Assets.Player.aLandAnimId, Assets.Player.bLandAnimId, Assets.Player.aLandAnimImagePath, Assets.Player.bLandAnimImagePath, Assets.Weapon.LandAnimId, Assets.Weapon.LandAnimImagePath, EntityOrder.Player, factory);
	
		AssetEntity e = getGfxEntity();
		
		anim = e.getAssetMgr().getAnimation(e.getAssetId());
		anim.setLooping(false);
		
		e = getWeaponGfxEntity();
		
		weaponAnim = e.getAssetMgr().getAnimation(e.getAssetId());
		weaponAnim.setLooping(false);
	}
	
	public void getAnim() {
		AssetEntity e = getGfxEntity();
		
		anim = e.getAssetMgr().getAnimation(e.getAssetId());
		anim.setLooping(false);
	}

	@Override
	public void enter() {	
		getAnim();
		anim.restart();
		weaponAnim.restart();
		SoundManager.playSound(Assets.Sounds.PlayerLandSoundId, 1f, 0.5f);
	}

	@Override
	public void leave() {		
		
	}

	@Override
	public void update() {	

		// check for anim end
		if (anim.isStopped()) {
			getPlayer().applyAction(PlayerActions.DoNothing);
		}
	}
}
