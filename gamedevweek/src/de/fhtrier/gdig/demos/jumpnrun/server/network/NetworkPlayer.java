package de.fhtrier.gdig.demos.jumpnrun.server.network;

import java.io.Serializable;

public class NetworkPlayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4472027954814606273L;
	private int networkId;
	private String playerName;
	private int teamId=1;
	
	public NetworkPlayer(String playerName, int networkId) {
		this.playerName = playerName;
		this.networkId = networkId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getNetworkId() {
		return networkId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	
}
