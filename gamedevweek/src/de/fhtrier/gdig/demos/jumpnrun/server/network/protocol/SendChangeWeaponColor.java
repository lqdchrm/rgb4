package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class SendChangeWeaponColor extends ProtocolCommand {

	private static final long serialVersionUID = -892343383645346995L;
	private int playerId;

	public SendChangeWeaponColor(int playerId) {
		super("SendChangeColor");

		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
}
