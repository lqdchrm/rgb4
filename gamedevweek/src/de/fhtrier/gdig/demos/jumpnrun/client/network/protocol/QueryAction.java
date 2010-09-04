package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.PlayerAction;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class QueryAction extends ProtocolCommand {

	private static final long serialVersionUID = -2964677085138541256L;
	public PlayerAction action;
	
	public QueryAction(PlayerAction action) {
		super("Query Action " + action.name());
		
		this.action = action;
	}

	public PlayerAction getAction() {
		return action;
	}

}
