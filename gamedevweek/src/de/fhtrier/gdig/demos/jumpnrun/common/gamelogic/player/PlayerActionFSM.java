package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.engine.helpers.FiniteStateMachine;

public class PlayerActionFSM extends FiniteStateMachine<Integer, PlayerActions> {

	public PlayerActionFSM() {
		super(PlayerActionState.Standing);

		// register: standing running shooting combinations
		add(PlayerActionState.Standing, PlayerActions.StartShooting, PlayerActionState.StandingShooting);
		add(PlayerActionState.StandingShooting, PlayerActions.StopShooting, PlayerActionState.Standing);
		add(PlayerActionState.Standing, PlayerActions.StartRunning, PlayerActionState.Running);
		add(PlayerActionState.Running, PlayerActions.StopRunning, PlayerActionState.Standing);
		add(PlayerActionState.Running, PlayerActions.StartShooting, PlayerActionState.RunningShooting);
		add(PlayerActionState.RunningShooting, PlayerActions.StopShooting, PlayerActionState.Running);
		add(PlayerActionState.RunningShooting, PlayerActions.StopRunning, PlayerActionState.StandingShooting);
		add(PlayerActionState.StandingShooting, PlayerActions.StartRunning, PlayerActionState.Running);
		
		// register: jumping falling shooting
		add(PlayerActionState.Standing, PlayerActions.Jump, PlayerActionState.Jumping);
		add(PlayerActionState.Standing, PlayerActions.Fall, PlayerActionState.Falling);
		add(PlayerActionState.Running, PlayerActions.Jump, PlayerActionState.Jumping);
		add(PlayerActionState.Running, PlayerActions.Fall, PlayerActionState.Falling);
		add(PlayerActionState.Falling, PlayerActions.Land, PlayerActionState.Standing);
		add(PlayerActionState.Jumping, PlayerActions.StartShooting, PlayerActionState.JumpShooting);
		add(PlayerActionState.JumpShooting, PlayerActions.StopShooting, PlayerActionState.Jumping);
		add(PlayerActionState.JumpShooting, PlayerActions.Fall, PlayerActionState.FallShooting);
		add(PlayerActionState.FallShooting, PlayerActions.StopShooting, PlayerActionState.Falling);
		add(PlayerActionState.Falling, PlayerActions.StartShooting, PlayerActionState.FallShooting);
		add(PlayerActionState.Jumping, PlayerActions.Fall, PlayerActionState.Falling);
		add(PlayerActionState.FallShooting, PlayerActions.Up, PlayerActionState.JumpShooting);
		add(PlayerActionState.Falling, PlayerActions.Up, PlayerActionState.Jumping);
		add(PlayerActionState.RunningShooting, PlayerActions.Fall, PlayerActionState.FallShooting);
		add(PlayerActionState.RunningShooting, PlayerActions.Jump, PlayerActionState.JumpShooting);
		add(PlayerActionState.StandingShooting, PlayerActions.Fall, PlayerActionState.FallShooting);
		add(PlayerActionState.StandingShooting, PlayerActions.Jump, PlayerActionState.JumpShooting);
		
	}

}
