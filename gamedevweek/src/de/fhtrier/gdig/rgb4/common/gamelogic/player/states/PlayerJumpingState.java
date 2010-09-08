package de.fhtrier.gdig.rgb4.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.Player;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.rgb4.identifiers.Assets;
import de.fhtrier.gdig.rgb4.identifiers.Constants;
import de.fhtrier.gdig.rgb4.identifiers.EntityOrder;

public class PlayerJumpingState extends PlayerAssetState {

	public PlayerJumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerJumpAnimId,
				Assets.PlayerJumpAnimImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
		getPlayer().getVel()[Entity.Y] = -Constants.GamePlayConstants.playerJumpSpeed;
	}

	@Override
	public void leave() {
		getPlayer().getVel()[Entity.Y] = 0.0f;
	}

	@Override
	public void update() {

		// check if falling
		if (getPlayer().getVel()[Entity.Y] < Constants.GamePlayConstants.playerFallTriggerSpeed) {
			getPlayer().applyAction(PlayerActions.Fall);
		}
	}

}
