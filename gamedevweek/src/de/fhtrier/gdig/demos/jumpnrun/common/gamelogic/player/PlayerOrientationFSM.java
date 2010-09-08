package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.engine.helpers.FiniteStateMachine;

public class PlayerOrientationFSM extends FiniteStateMachine<Integer, PlayerActions> {

	public PlayerOrientationFSM() {
		super(PlayerActionState.Right);
	
		add(PlayerActionState.Right, PlayerActions.Left, PlayerActionState.Left);
		add(PlayerActionState.Right, PlayerActions.Right, PlayerActionState.Right);
		add(PlayerActionState.Left, PlayerActions.Right, PlayerActionState.Right);
		add(PlayerActionState.Left, PlayerActions.Left, PlayerActionState.Left);
	}
}
