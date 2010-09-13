package de.fhtrier.gdig.engine.network;

import java.net.InterfaceAddress;
import java.net.Socket;
import java.util.List;

import de.fhtrier.gdig.engine.network.impl.ClientHandler;
import de.fhtrier.gdig.engine.network.impl.NetworkComponentClient;
import de.fhtrier.gdig.engine.network.impl.NetworkComponentServer;

public abstract class NetworkComponent {

	/* common */
	private static NetworkComponent netComp;

	protected List<INetworkCommandListener> listeners;
	protected List<INetworkCommand> commands;

	/* server */

	/* common */
	public static void createServerInstance() {
		if (netComp == null) {
			netComp = new NetworkComponentServer();
		} else {
			throw new RuntimeException("instance was already created");
		}
	}

	public static void createClientInstance() {
		if (netComp == null) {
			netComp = new NetworkComponentClient();
		} else {
			throw new RuntimeException("instance was already created");
		}
	}

	public static NetworkComponent getInstance() {
		if (netComp == null) {
			throw new RuntimeException(
					"no instance available. you should create one before");
		}
		return netComp;
	}

	public abstract void sendCommand(INetworkCommand command);

	public abstract void sendCommand(int clientNetworkId,
			INetworkCommand command);

	public abstract void update();

	public void addListener(INetworkCommandListener l) {
		if (!this.listeners.contains(l)) {
			this.listeners.add(l);
		}
	}

	public void removeListener(INetworkCommandListener l) {
		listeners.remove(l);
	}

	public abstract Integer getNetworkId();

	/* server */

	public abstract void startListening(InterfaceAddress ni, int port);

	public abstract void stopListening();

	public abstract List<ClientHandler> getClients();

	public abstract void addClient(Socket s);

	public abstract void removeClient(ClientHandler c);

	/* client */

	public abstract boolean connect(String host, int port);

	public abstract void disconnect();
}
