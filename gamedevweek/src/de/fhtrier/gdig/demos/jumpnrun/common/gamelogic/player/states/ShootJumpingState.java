package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.management.Factory;

public class ShootJumpingState extends AbstractShootState {

	public ShootJumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.Player.ShootJumpingAnimId,
				Assets.Player.ShootJumpingImagePath, EntityOrder.Player, factory);
	}


	@Override
	public void update() {
		
		// check if landed
		if (getPlayer().isOnGround()) {
			getPlayer().applyAction(PlayerActions.Land);
		}
	}
}
