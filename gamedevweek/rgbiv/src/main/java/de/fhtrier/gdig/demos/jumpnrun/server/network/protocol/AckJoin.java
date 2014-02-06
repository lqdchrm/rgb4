package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class AckJoin extends ProtocolCommand {

	private static final long serialVersionUID = -5890473363698319990L;

	public AckJoin() {
		super("AckJoin");
	}
}
