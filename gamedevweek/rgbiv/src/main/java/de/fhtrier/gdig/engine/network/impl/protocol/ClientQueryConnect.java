package de.fhtrier.gdig.engine.network.impl.protocol;

/**
 * This command is used by the client to query the server for a networkId. The
 * server should answer with a ServerAckConnect-command
 */
public class ClientQueryConnect extends ProtocolCommand {

	private static final long serialVersionUID = 3875956562836359694L;

	public ClientQueryConnect() {
		super("ClientQueryConnect");
	}

}
