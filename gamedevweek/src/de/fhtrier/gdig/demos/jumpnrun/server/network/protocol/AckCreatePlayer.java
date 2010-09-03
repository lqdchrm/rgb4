package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class AckCreatePlayer extends ProtocolCommand {

	private static final long serialVersionUID = -4983502199487412022L;
	private int playerId;
	
	public AckCreatePlayer(int playerId) {
		super("AckCreatePlayer");
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
}
