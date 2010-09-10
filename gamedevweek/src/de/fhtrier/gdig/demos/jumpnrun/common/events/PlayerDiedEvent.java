package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;

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
		player.die();
		if (killer != null) {
			killer.getPlayerStats().increaseKills();
			Team.getTeamById(killer.getPlayerCondition().teamId)
					.increaseKills();
		}
	}

}
