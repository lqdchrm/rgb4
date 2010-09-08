package de.fhtrier.gdig.rgb4.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.Player;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.rgb4.identifiers.Assets;
import de.fhtrier.gdig.rgb4.identifiers.Constants;
import de.fhtrier.gdig.rgb4.identifiers.EntityOrder;

public class PlayerShootJumpingState extends PlayerAssetState {

	public PlayerShootJumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerShootJumpingAnimId,
				Assets.PlayerShootJumpingImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
		getPlayer().setOnGround(false);
	}

	@Override
	public void leave() {
	}

	@Override
	public void update() {
		
		// check if landed
		if (getPlayer().isOnGround()) {
			getPlayer().applyAction(PlayerActions.Land);
		}
	}
}
