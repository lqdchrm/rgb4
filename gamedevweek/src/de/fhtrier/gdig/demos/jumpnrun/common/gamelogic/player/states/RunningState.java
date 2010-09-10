package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class RunningState extends AbstractAssetState {

	public RunningState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.Player.RunningAnimId,
				Assets.Player.RunningImagePath, Assets.Weapon.RunningAnimId, Assets.Weapon.RunningImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
		SoundManager.loopSound(Assets.Sounds.PlayerRunSoundId, 1f, 0.2f);
		getPlayer().getWeaponParticleEntity().getData()[Entity.Y] = 155;
	}

	@Override
	public void leave() {
		SoundManager.stopSound(Assets.Sounds.PlayerRunSoundId);
		getPlayer().getWeaponParticleEntity().getData()[Entity.Y] = 165;
	}

	@Override
	public void update() {	
		
		// check if vel < threshold --> stop running
		if (Math.abs(getPlayer().getVel()[Entity.X]) < Constants.GamePlayConstants.playerIdleTriggerSpeed) {
			getPlayer().applyAction(PlayerActions.StopRunning);
		}
		
		// check if currentPos < prevPos --> start falling
		if (getPlayer().getVel()[Entity.Y] > Constants.GamePlayConstants.playerFallingTriggerSpeed) {
			getPlayer().applyAction(PlayerActions.Fall);
		}
	}
}
