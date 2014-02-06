package de.fhtrier.gdig.engine.network.impl.protocol;

/**
 * This command is send by the server to a client as answer to a
 * ClientQueryConnect-command. It contains the networkId, which the client
 * should take as identifier
 */
public class ServerAckConnect extends ProtocolCommand {

	private static final long serialVersionUID = -5457645181039165113L;
	private int networkId;

	public ServerAckConnect(int networkId) {
		super("ServerAckConnect");

		this.networkId = networkId;
	}

	public int getNetworkId() {
		return networkId;
	}
}
