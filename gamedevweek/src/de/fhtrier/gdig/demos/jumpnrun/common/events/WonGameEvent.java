package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;

public class WonGameEvent extends Event {
	
	private Player winningPlayer;
	//private Team winningTeam;
	
	public WonGameEvent (Player winningPlayer) {
		super();
		
		this.winningPlayer = winningPlayer;
	}
	
	@Override
	public void update () {
		System.out.println(winningPlayer.getPlayerCondition().name + " won.");
		System.exit(1);
		// TODO: what happens after the end?
	}

}