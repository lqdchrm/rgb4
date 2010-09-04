package de.fhtrier.gdig.demos.jumpnrun.client.network;

import java.io.Serializable;

import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.engine.network.impl.protocol.NetworkCommand;

public class ClientData extends NetworkCommand implements Serializable {

	private static final long serialVersionUID = 6507211207331141555L;

	private NetworkData networkData;
	
	public void setNetworkData(NetworkData data) {
		this.networkData = data;
	}

	public NetworkData getNetworkData() {
		return this.networkData;
	}
}
