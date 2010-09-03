package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoCreatePlayer extends ProtocolCommand {

	private static final long serialVersionUID = -2494668970339654827L;

	private int playerId;
	
	public DoCreatePlayer(int playerId) {
		super("DoCreatePlayer");
		
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
}
