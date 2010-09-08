package de.fhtrier.gdig.rgb4.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.Player;
import de.fhtrier.gdig.rgb4.identifiers.Assets;
import de.fhtrier.gdig.rgb4.identifiers.EntityOrder;

public class PlayerJumpDownState extends PlayerAssetState {
	
	public PlayerJumpDownState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerJumpDownAnimId, Assets.PlayerJumpDownAnimImagePath, EntityOrder.Player, factory);
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
