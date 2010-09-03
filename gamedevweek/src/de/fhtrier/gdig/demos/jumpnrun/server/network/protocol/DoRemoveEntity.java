package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoRemoveEntity extends ProtocolCommand {

	private static final long serialVersionUID = -7514424748885675255L;

	private int playerId;
	
	public DoRemoveEntity(int playerId) {
		super("DoRemoveEntity");
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}

}
