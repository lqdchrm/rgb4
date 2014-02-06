package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class DyingState extends AbstractAssetState {

	public DyingState(int stateId, Player player, Factory factory)
			throws SlickException {
		super(stateId, player,
				Assets.Player.aTodAnimId, Assets.Player.bTodAnimId,
				Assets.Player.aTodImagePath, Assets.Player.bTodImagePath,
				Assets.Weapon.DyingAnimId, Assets.Weapon.DyingImagePath,
				EntityOrder.Player, factory);
	}
	
	@Override
	protected void initAnim(Animation anim) {
		super.initAnim(anim);

		anim.setLooping(false);
		anim.setAutoUpdate(true);
	}
	
	@Override
	public void enter() {
		SoundManager.playSound(Assets.Sounds.PlayerDyingSoundId, 1.0f, 0.3f);
	}

	@Override
	public void leave() {

	}

	@Override
	public void update() {
		
		Animation anim = getGfxEntity().getAnimationAsset();
		
		if (anim.isStopped()) {
			getPlayer().applyAction(PlayerActions.Revive);
		}
	}
}
