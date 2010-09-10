package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.PlayerCondition;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class AckPlayerCondition extends ProtocolCommand {

	private static final long serialVersionUID = 7134864961774169991L;
	private int playerId;
	private PlayerCondition condition;

	public AckPlayerCondition(int playerId, PlayerCondition cond) {
		super("AckPlayerCondition");

		this.playerId = playerId;
		this.condition = cond;
	}

	public int getPlayerId() {
		return playerId;
	}

	public PlayerCondition getPlayerCondition() {
		return condition;
	}
}
