package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkLevel;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

/**
 * 
 * called by the master to change the current map
 * 
 * @author ttrocha
 *
 */
public class QuerySetLevel extends ProtocolCommand {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1183245979954716420L;
	
	
	private NetworkLevel networkLevel;
	
	public QuerySetLevel(NetworkLevel level)
	{
		super("QuerySetLevel");
		this.networkLevel = level;
	}

	public NetworkLevel getNetworkLevel() {
		return networkLevel;
	}
	
	
	
}
