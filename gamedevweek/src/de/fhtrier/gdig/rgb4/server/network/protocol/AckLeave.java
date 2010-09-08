package de.fhtrier.gdig.rgb4.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class AckLeave extends ProtocolCommand {

	private static final long serialVersionUID = -3308077356053564768L;

	public AckLeave() {
		super("AckLeave");
	}

}
