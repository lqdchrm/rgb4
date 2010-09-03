package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class QueryLeave extends ProtocolCommand {

	private static final long serialVersionUID = -1974122582834444607L;

	private int playerId;
	
	public QueryLeave(int playerId) {
		super("QueryLeave");
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}

}
