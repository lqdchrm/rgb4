package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class SendWon extends ProtocolCommand {
	
	private static final long serialVersionUID = 5765856843222222L;
	private int winnerId;

	public SendWon(int winnerId) {
		super("SendWon");
		
		this.winnerId = winnerId;
	}

	public int getWinnerId() {
		return winnerId;
	}
	
}
