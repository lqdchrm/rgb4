package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.*;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckJoin;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendKill;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class PlayerDiedEvent extends Event {
	
	private Player player;
	
	public PlayerDiedEvent (Player player) {
		this.player = player;
	}
	
	public void update () {
		NetworkComponent.getInstance().sendCommand(new SendKill(player.getId()));
		System.out.println("Player: " + player.getId());
	}

}
