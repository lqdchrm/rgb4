package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoRemovePlayer extends ProtocolCommand {

	private static final long serialVersionUID = -7514424748885675255L;

	private int playerId;
	
	public DoRemovePlayer(int playerId) {
		super("DoRemovePlayer");
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}

}
