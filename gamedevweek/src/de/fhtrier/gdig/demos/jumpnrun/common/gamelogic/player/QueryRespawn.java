package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryAction;

public class QueryRespawn extends QueryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8875095539403429598L;

	private int playerId;
	
	public QueryRespawn(int playerId) {
		super(PlayerNetworkAction.RESPAWN);
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
}
