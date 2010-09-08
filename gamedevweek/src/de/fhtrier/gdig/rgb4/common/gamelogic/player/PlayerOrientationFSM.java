package de.fhtrier.gdig.rgb4.common.gamelogic.player;

import de.fhtrier.gdig.engine.helpers.FiniteStateMachine;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActionState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActions;

public class PlayerOrientationFSM extends FiniteStateMachine<Integer, PlayerActions> {

	public PlayerOrientationFSM() {
		super(PlayerActionState.Right);
	
		add(PlayerActionState.Right, PlayerActions.Left, PlayerActionState.Left);
		add(PlayerActionState.Right, PlayerActions.Right, PlayerActionState.Right);
		add(PlayerActionState.Left, PlayerActions.Right, PlayerActionState.Right);
		add(PlayerActionState.Left, PlayerActions.Left, PlayerActionState.Left);
	}
}
