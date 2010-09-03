package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class QueryCreatePlayer extends ProtocolCommand {

	private static final long serialVersionUID = 4687095068613647172L;

	public QueryCreatePlayer() {
		super("QueryCreatePlayer");
	}
}
