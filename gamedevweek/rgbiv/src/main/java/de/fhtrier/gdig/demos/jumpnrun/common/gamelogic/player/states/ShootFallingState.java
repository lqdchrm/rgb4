package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.management.Factory;

public class ShootFallingState extends AbstractShootState {
	
	public ShootFallingState(int stateId, Player player, Factory factory)
			throws SlickException {
		super(stateId, player, Assets.Player.aFallShootingAnimId, Assets.Player.bFallShootingAnimId, Assets.Player.aFallShootingImagePath, Assets.Player.bFallShootingImagePath, Assets.Weapon.FallShootingAnimId, Assets.Weapon.FallShootingImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter () {
		super.enter();
		getPlayer().getWeaponParticleEntity().getData()[Entity.Y] = 155;
	}
	
	@Override
	public void leave () {
		super.leave();
		getPlayer().getWeaponParticleEntity().getData()[Entity.Y] = 165;
	}
	
	@Override
	public void update() {	
		super.update();
		
		// check if vel < threshold --> stop falling
		if (getPlayer().isOnGround()) {
			getPlayer().applyAction(PlayerActions.Land);
		}
	}
}
