package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.management.Factory;

public class PlayerShootStandingState extends PlayerAssetState {

	public PlayerShootStandingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerShootStandingAnimId,
				Assets.PlayerShootStandingImagePath, EntityOrder.Player,
				factory);
	}

	@Override
	public void enter() {
	}

	@Override
	public void leave() {
	}

	@Override
	public void update() {
	}
}
