package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class PlayerShootJumpingState extends PlayerAssetState {

	public PlayerShootJumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerShootJumpingAnimId,
				Assets.PlayerShootJumpingImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
		getPlayer().setOnGround(false);
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
	}
}
