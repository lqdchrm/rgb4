﻿package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendTeamCondition;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class PlayerDiedEvent extends Event {
	
	private Player player;
	private Player killer;
	
	public PlayerDiedEvent(Player player, Player killer) {
		super();
		
		this.player = player;
		this.killer = killer;
	}
	
	@Override
	public void update() {

		player.getPlayerCondition().setDeaths(player.getPlayerCondition().getDeaths() + 1);
		
		if (killer != null) {
			killer.getPlayerCondition().setKills(killer.getPlayerCondition().getKills() + 1);

			Team team = Team.getTeamById(killer.getPlayerCondition().getTeamId());
			team.increaseKills();
			
			// Update Team statistics
			NetworkComponent.getInstance().sendCommand(
					new SendTeamCondition(team));
		}
	}
}
