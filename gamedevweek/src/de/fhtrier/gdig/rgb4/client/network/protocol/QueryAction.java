package de.fhtrier.gdig.rgb4.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActions;

public class QueryAction extends ProtocolCommand {

	private static final long serialVersionUID = -2964677085138541256L;
	public PlayerActions action;

	public QueryAction(PlayerActions action) {
		super("Query Action " + action);

		this.action = action;
	}

	public PlayerActions getAction() {
		return action;
	}

}
