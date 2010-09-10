package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.ArrayList;
import java.util.List;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;

public class Team {
	
	public int id;
	private static int currentId;
	public List <Integer> playerIdList;
	private int kills;
	private int deaths;
	
	public static Team Team1 = new Team();
	public static Team Team2 = new Team();
	
	public Team () throws IllegalArgumentException {
		playerIdList = new ArrayList<Integer> ();
		kills = 0;
		
		/*if ((currentId+1) >= 2) {
			throw new IllegalArgumentException ("Too many teams!");
		}*/
		
		id = ++currentId;
	}
	
	public static Team getTeamById (int teamId) {
		switch (teamId) {
		case 1: return Team1;
		case 2: return Team2;
		}
		return null;
	}
	
	public void addPlayer (int playerId) {
		playerIdList.add (new Integer(playerId));
	}
	
	public void addPlayer (Player player) {
		playerIdList.add (new Integer(player.getId()));
	}
	
	public void removePlayer (int playerId) {
		playerIdList.remove (playerId);
	}

	public void increaseKills() {
		kills++;
	}

	public int getKills() {
		return kills;
	}

	public void increaseDeaths() {
		deaths++;
	}

	public int getDeaths() {
		return deaths;
	}
}