package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.PlayerNetworkAction;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class QueryAction extends ProtocolCommand {

	private static final long serialVersionUID = -2964677085138541256L;
	public PlayerNetworkAction action;

	public QueryAction(PlayerNetworkAction action) {
		super("Query Action " + action);

		this.action = action;
	}

	public PlayerNetworkAction getAction() {
		return action;
	}

}
