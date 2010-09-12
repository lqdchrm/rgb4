package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.PlayerNetworkAction;



public class QueryPlayerCondition extends QueryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 527402994442618237L;


	private int playerId;
	
	public QueryPlayerCondition(int id) {
		super(PlayerNetworkAction.QUERYPLAYERCONDITION);
		this.playerId = id;
	}
	
	public int getPlayerId() {
		return playerId;
	}
}
