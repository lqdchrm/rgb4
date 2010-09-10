package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkLevel;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

/**
 * 
 * server informs clients about level-change
 * 
 * @author ttrocha
 *
 */
public class AckSetLevel extends ProtocolCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5307269963598089376L;

	private NetworkLevel networkLevel;
	
	public AckSetLevel(NetworkLevel networkLevel)
	{
		super("AckSetLevel");
		this.networkLevel = networkLevel;
	}

	public NetworkLevel getNetworkLevel() {
		return networkLevel;
	}

	
}
