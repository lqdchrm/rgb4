package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class SendKill extends ProtocolCommand {

	private static final long serialVersionUID = -8904733636956319995L;
	private int playerId;
	private int killerId;

	public SendKill(int playerId, int killerId) {
		super("SendKill");

		this.playerId = playerId;
		this.killerId = killerId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getKillerId() {
		return killerId;
	}
}
