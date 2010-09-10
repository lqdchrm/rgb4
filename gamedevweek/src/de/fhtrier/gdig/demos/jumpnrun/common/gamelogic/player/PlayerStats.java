package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

public class PlayerStats {
	private int kills;
	private int deaths;

	public PlayerStats() {
		kills = 0;
		deaths = 0;
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
