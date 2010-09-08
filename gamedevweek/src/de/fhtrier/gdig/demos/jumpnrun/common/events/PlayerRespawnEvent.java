package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.StateColor;
import de.fhtrier.gdig.engine.entities.Entity;

public class PlayerRespawnEvent extends Event {
	
	private Player player;
	
	public PlayerRespawnEvent (Player player) {
		this.player = player;
	}
	
	@Override
	public void update () {
		player.getState().health = 1.0f; // TODO: Constants
		player.getData()[Entity.X] = 200;
		player.getData()[Entity.Y] = 200;
		player.getState().ammo = 1.0f;
		player.getState().color = StateColor.RED;
		player.getState().weaponColor = StateColor.RED;
		
		player.setState(PlayerActionState.RunRight);
		player.setState(PlayerActionState.Idle);
	}
}
