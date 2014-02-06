package de.fhtrier.gdig.engine.network;

import java.net.InterfaceAddress;
import java.util.List;

public interface INetworkLobby {
	public List<NetworkServerObject> getServerList();

	public void getServers(InterfaceAddress networkInterface);

	public void stopGetServers();
}
