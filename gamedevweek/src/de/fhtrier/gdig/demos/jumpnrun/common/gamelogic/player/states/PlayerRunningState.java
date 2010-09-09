package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.management.Factory;

public class PlayerRunningState extends PlayerAssetState {

	public PlayerRunningState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerRunningAnimId,
				Assets.PlayerRunningImagePath, EntityOrder.Player, factory);
	}

	@Override
	public void enter() {
	}

	@Override
	public void leave() {
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
			System.out.println("State: Falling");
		}
	}
}
