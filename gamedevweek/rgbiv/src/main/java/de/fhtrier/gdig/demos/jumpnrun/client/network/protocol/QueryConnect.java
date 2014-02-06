package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

/**
 * Command send from client to the server to request a connect with a specific player name
 * 
 * @author roessgro
 *
 */
public class QueryConnect extends ProtocolCommand {

	private static final long serialVersionUID = -1998003358429013861L;

	private String playerName;

	public QueryConnect(String playerName) {
		super();
		
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}
}
