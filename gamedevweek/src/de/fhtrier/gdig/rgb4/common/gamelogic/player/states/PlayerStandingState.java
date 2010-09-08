package de.fhtrier.gdig.rgb4.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.Player;
import de.fhtrier.gdig.rgb4.identifiers.Assets;
import de.fhtrier.gdig.rgb4.identifiers.EntityOrder;

public class PlayerStandingState extends PlayerAssetState {

	public PlayerStandingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerStandingAnimId, Assets.PlayerStandingAnimImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
		getPlayer().getAcc()[Entity.X] = 0.0f;
	}

	@Override
	public void leave() {
	}

	@Override
	public void update() {

	}
}
