package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.management.Factory;

public class ShootFallingState extends AbstractShootState {
	
	public ShootFallingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.Player.FallShootingAnimId, Assets.Player.aFallShootingImagePath, Assets.Player.bFallShootingImagePath, Assets.Weapon.FallShootingAnimId, Assets.Weapon.FallShootingImagePath, EntityOrder.Player, factory);
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
