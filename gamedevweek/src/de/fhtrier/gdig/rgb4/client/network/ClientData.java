package de.fhtrier.gdig.rgb4.client.network;

import java.io.Serializable;

import de.fhtrier.gdig.engine.network.impl.protocol.NetworkCommand;
import de.fhtrier.gdig.rgb4.common.network.NetworkData;

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
