package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class AbstractShootState extends AbstractAssetState {
	
	public AbstractShootState(int stateId, Player player, int aPlayerAnimAssetId, int bPlayerAnimAssetId,
			String aPlayerAnimAssetPath, String bPlayerAnimAssetPath, int weaponAnimAssetId, String weaponAnimAssetPath, int entityOrder, Factory factory)
			throws SlickException {
		super(stateId, player, aPlayerAnimAssetId, bPlayerAnimAssetId, aPlayerAnimAssetPath, bPlayerAnimAssetPath, weaponAnimAssetId, weaponAnimAssetPath, entityOrder, factory);
	}
	
	@Override
	protected void initAnim(Animation anim) {
		anim.setLooping(false);
		anim.setAutoUpdate(true);
	}
	
	@Override
	public void enter() {
		
		// get animation
		Animation animPlayer = getGfxEntity().getAnimationAsset();
		Animation animWeapon = getWeaponGfxEntity().getAnimationAsset();
		
		if (animPlayer.isStopped()) {
			animPlayer.restart();
			animWeapon.restart();
		}
		SoundManager.playSound(Assets.Sounds.PlayerShootSoundId, 1f, 0.5f);
	}

	@Override
	public void update() {

		Animation animPlayer = getGfxEntity().getAnimationAsset();

		if (animPlayer.isStopped()) {
			getPlayer().applyAction(PlayerActions.StopShooting);
		}
	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub
		
	}
}
