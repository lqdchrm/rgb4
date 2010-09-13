package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class SendWon extends ProtocolCommand {

	private static final long serialVersionUID = 5765856843222222L;
	private int winnerId;
	private int winnerType;
	public static final int winnerType_Player = 0;
	public static final int winnerType_Team = 1;

	public SendWon(int winnerId, int winnerType) {
		super("SendWon");

		this.winnerId = winnerId;
		this.winnerType = winnerType;
	}

	public int getWinnerId() {
		return winnerId;
	}

	public int getWinnerType() {
		return winnerType;
	}
	
}
