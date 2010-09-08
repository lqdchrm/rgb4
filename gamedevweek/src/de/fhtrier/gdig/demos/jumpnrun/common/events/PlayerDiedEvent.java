package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.*;

public class PlayerDiedEvent extends Event {
	
	private Player player;
	
	public PlayerDiedEvent (Player player) {
		this.player = player;
	}
	
	public void update () {
		System.out.println("Player: " + player.getId());
		player.getState().health = 1.0f;
	}

}
