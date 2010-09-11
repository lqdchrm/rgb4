package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoPlaySound;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class WonGameEvent extends Event {
	
	private Player winningPlayer;
	private Team winningTeam;
	
	public WonGameEvent (Player winningPlayer) {
		super();
		
		this.winningTeam = null;
		this.winningPlayer = winningPlayer;
	}
	
	public WonGameEvent (Team winningTeam) {
		super();
		
		this.winningPlayer = null;
		this.winningTeam = winningTeam;
	}
	
	@Override
	public void update () {
		if (winningPlayer != null) {
			System.out.println(winningPlayer.getPlayerCondition().name + " won.");
		}
		if (winningTeam != null) {
			System.out.println("Team " + winningTeam.id + " won.");
		}
		NetworkComponent.getInstance().sendCommand(new DoPlaySound(Assets.Sounds.DoomsdayDeviceSoundId));
	}

}