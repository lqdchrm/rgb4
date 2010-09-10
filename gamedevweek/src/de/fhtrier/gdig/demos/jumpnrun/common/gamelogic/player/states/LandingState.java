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
	
	public LandingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.Player.LandAnimId, Assets.Player.LandAnimImagePath, Assets.Weapon.LandAnimId, Assets.Weapon.LandAnimImagePath, EntityOrder.Player, factory);
	
		AssetEntity e = getGfxEntity();
		
		anim = e.Assets().getAnimation(e.getAssetId());
		anim.setLooping(false);
	}

	@Override
	public void enter() {		
		anim.restart();
		SoundManager.playSound(Assets.Sounds.PlayerLandSoundId, 1f, 0.1f);
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
