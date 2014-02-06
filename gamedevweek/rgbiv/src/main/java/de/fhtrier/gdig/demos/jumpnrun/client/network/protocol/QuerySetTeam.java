package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

/**
 * 
 * client-request to choose a new team
 * 
 * @author ttrocha
 *
 */
public class QuerySetTeam extends ProtocolCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2993220870450700943L;
	private int teamID;
	
	public QuerySetTeam(int teamID)
	{
		super("QuerySetTeam");
		this.teamID = teamID;
	}

	public int getTeamID() {
		return teamID;
	}

}
