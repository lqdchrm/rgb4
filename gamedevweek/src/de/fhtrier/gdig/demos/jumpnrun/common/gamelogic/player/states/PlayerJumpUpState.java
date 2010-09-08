package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.management.Factory;

public class PlayerJumpUpState extends PlayerAssetState {

	public PlayerJumpUpState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerJumpUpAnimId,
				Assets.PlayerJumpUpAnimImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
		getPlayer().getVel()[Entity.Y] = -Constants.GamePlayConstants.playerJumpVel;
	}

	@Override
	public void leave() {
		getPlayer().getVel()[Entity.Y] = 0.0f;
	}

	@Override
	public void update() {

	}

}
