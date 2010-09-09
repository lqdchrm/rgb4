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

public class PlayerShootJumpingState extends PlayerAssetState {
	private Animation anim;

	public PlayerShootJumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerShootJumpingAnimId,
				Assets.PlayerShootJumpingImagePath, EntityOrder.Player, factory);
		AssetEntity e = getGfxEntity();
		
		anim = e.Assets().getAnimation(e.getAssetId());
		anim.setLooping(false);
	
	}

	@Override
	public void enter() {
		getPlayer().setOnGround(false);
		
		if (anim.isStopped()) {
			anim.restart();
		}
		SoundManager.loopSound(Assets.BulletSoundId, 1f, 0.2f);
	}

	@Override
	public void leave() {
		SoundManager.stopSound(Assets.BulletSoundId);
	}

	@Override
	public void update() {
		
		// check if landed
		if (getPlayer().isOnGround()) {
			getPlayer().applyAction(PlayerActions.Land);
		}
		
		if (anim.isStopped()) {
			getPlayer().applyAction(PlayerActions.StopShooting);
		}
	}
}
