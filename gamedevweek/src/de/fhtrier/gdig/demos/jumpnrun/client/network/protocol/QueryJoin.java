package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class QueryJoin extends ProtocolCommand {

	private static final long serialVersionUID = 4116389065107325261L;

	public QueryJoin() {
		super("QueryJoin");
	}
}
