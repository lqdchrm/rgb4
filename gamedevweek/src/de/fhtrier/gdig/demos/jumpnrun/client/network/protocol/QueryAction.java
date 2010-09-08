package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

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
