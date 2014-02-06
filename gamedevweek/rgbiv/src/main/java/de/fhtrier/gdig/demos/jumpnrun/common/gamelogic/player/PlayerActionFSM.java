package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.engine.helpers.FiniteStateMachine;

public class PlayerActionFSM extends FiniteStateMachine<PlayerActionState, PlayerActions> {

	public PlayerActionFSM() {
		super(PlayerActionState.Standing);

		// register: standing running combinations
		add(PlayerActionState.Standing, PlayerActions.StartRunning, PlayerActionState.Running);
		add(PlayerActionState.Running, PlayerActions.StopRunning, PlayerActionState.Standing);
		add(PlayerActionState.Running, PlayerActions.Jump, PlayerActionState.Jumping);
		
		// register standing running while shooting
		add(PlayerActionState.ShootStanding, PlayerActions.StartRunning, PlayerActionState.ShootRunning);
		add(PlayerActionState.ShootRunning, PlayerActions.StopRunning, PlayerActionState.ShootStanding);

		// register standing shooting
		add(PlayerActionState.Standing, PlayerActions.StartShooting, PlayerActionState.ShootStanding);
		add(PlayerActionState.ShootStanding, PlayerActions.StopShooting, PlayerActionState.Standing);

		// register running shooting
		add(PlayerActionState.Running, PlayerActions.StartShooting, PlayerActionState.ShootRunning);
		add(PlayerActionState.ShootRunning, PlayerActions.StopShooting, PlayerActionState.Running);
		
		// register: jumping landing
		add(PlayerActionState.Standing, PlayerActions.Jump, PlayerActionState.Jumping);
		add(PlayerActionState.Jumping,PlayerActions.Land, PlayerActionState.Landing);
		add(PlayerActionState.Landing, PlayerActions.DoNothing, PlayerActionState.Standing);
		add(PlayerActionState.Landing, PlayerActions.Jump, PlayerActionState.Jumping);
		add(PlayerActionState.Landing, PlayerActions.StartShooting, PlayerActionState.ShootStanding);
		add(PlayerActionState.Landing, PlayerActions.StartRunning, PlayerActionState.Running);
		
		// register: jumping shooting
		add(PlayerActionState.Jumping, PlayerActions.StartShooting, PlayerActionState.ShootJumping);
		add(PlayerActionState.ShootJumping, PlayerActions.StopShooting, PlayerActionState.Jumping);
		add(PlayerActionState.ShootStanding, PlayerActions.Jump, PlayerActionState.Jumping);
		add(PlayerActionState.ShootRunning, PlayerActions.Jump, PlayerActionState.Jumping);
		add(PlayerActionState.ShootJumping, PlayerActions.Land, PlayerActionState.ShootStanding);
		
		// register: running falling
		add(PlayerActionState.Running, PlayerActions.Fall, PlayerActionState.Falling);
		add(PlayerActionState.ShootRunning, PlayerActions.Fall, PlayerActionState.ShootFalling);
		add(PlayerActionState.Falling, PlayerActions.Land, PlayerActionState.Landing);
		add(PlayerActionState.ShootFalling, PlayerActions.Land, PlayerActionState.Landing);
		add(PlayerActionState.Standing, PlayerActions.Fall, PlayerActionState.Falling);
		add(PlayerActionState.ShootStanding, PlayerActions.Fall, PlayerActionState.ShootFalling);
		
		// dying
		add(PlayerActionState.Falling, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.ShootFalling, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.Jumping, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.Landing, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.Running, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.ShootJumping, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.ShootRunning, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.ShootStanding, PlayerActions.Die, PlayerActionState.Dying);
		add(PlayerActionState.Standing, PlayerActions.Die, PlayerActionState.Dying);
		
		// recover
		add(PlayerActionState.Dying, PlayerActions.Revive, PlayerActionState.Reviving);
		add(PlayerActionState.Reviving, PlayerActions.DoNothing, PlayerActionState.Standing);
		
//		add(PlayerActionState.Standing, PlayerActions.Fall, PlayerActionState.Falling);
//		add(PlayerActionState.Running, PlayerActions.Jump, PlayerActionState.Jumping);
//		add(PlayerActionState.Running, PlayerActions.Fall, PlayerActionState.Falling);
//		add(PlayerActionState.Falling, PlayerActions.Land, PlayerActionState.Standing);
//		add(PlayerActionState.Jumping, PlayerActions.StartShooting, PlayerActionState.JumpShooting);
//		add(PlayerActionState.JumpShooting, PlayerActions.StopShooting, PlayerActionState.Jumping);
//		add(PlayerActionState.JumpShooting, PlayerActions.Fall, PlayerActionState.FallShooting);
//		add(PlayerActionState.FallShooting, PlayerActions.StopShooting, PlayerActionState.Falling);
//		add(PlayerActionState.Falling, PlayerActions.StartShooting, PlayerActionState.FallShooting);
//		add(PlayerActionState.Jumping, PlayerActions.Fall, PlayerActionState.Falling);
//		add(PlayerActionState.FallShooting, PlayerActions.Up, PlayerActionState.JumpShooting);
//		add(PlayerActionState.Falling, PlayerActions.Up, PlayerActionState.Jumping);
//		add(PlayerActionState.RunningShooting, PlayerActions.Fall, PlayerActionState.FallShooting);
//		add(PlayerActionState.RunningShooting, PlayerActions.Jump, PlayerActionState.JumpShooting);
//		add(PlayerActionState.StandingShooting, PlayerActions.Fall, PlayerActionState.FallShooting);
//		add(PlayerActionState.StandingShooting, PlayerActions.Jump, PlayerActionState.JumpShooting);
	}

}
