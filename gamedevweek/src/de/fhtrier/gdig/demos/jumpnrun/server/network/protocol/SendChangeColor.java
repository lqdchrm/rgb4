package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class SendChangeColor extends ProtocolCommand {

	private static final long serialVersionUID = -8901533836906319995L;
	private int playerId;

	public SendChangeColor(int playerId) {
		super("SendChangeColor");

		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
}
