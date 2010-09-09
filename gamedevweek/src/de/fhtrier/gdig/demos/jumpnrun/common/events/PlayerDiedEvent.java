package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;

public class PlayerDiedEvent extends Event {
	
	private Player player;
	private Player killer;
	
	public PlayerDiedEvent (Player player, Player killer) {
		super();
		
		this.player = player;
		this.killer = killer;
	}
	
	public void update () {
		player.die();
		killer.getPlayerStats().increaseKills();
		Team.getTeamById(killer.getPlayerCondition().teamId).increaseKills();
	}

}
