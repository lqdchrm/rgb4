package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.sound.SoundManager;

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
		SoundManager.playSound(Assets.DoomsdayDeviceSoundId);
		// TODO: what happens after the end?
	}

}