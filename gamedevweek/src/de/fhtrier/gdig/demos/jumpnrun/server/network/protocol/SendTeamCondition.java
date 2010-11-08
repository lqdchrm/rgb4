package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class SendTeamCondition extends ProtocolCommand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8701777107570289377L;
	private int teamId;
	private int kills;
	private int deaths;
	
	public SendTeamCondition (Team team) {
		super("AckTeamCondition");
		
		this.teamId = team.id;
		this.kills = team.getKills();
		this.deaths = team.getDeaths();
	}

	public int getTeamId() {
		return teamId;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
}
