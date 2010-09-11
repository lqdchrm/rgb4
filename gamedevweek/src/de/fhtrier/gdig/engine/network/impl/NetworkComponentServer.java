package de.fhtrier.gdig.engine.network.impl;

import java.net.InterfaceAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.impl.protocol.ClientQueryConnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ClientQueryDisconnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ServerAckConnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ServerAckDisconnect;

public class NetworkComponentServer extends NetworkComponentImpl {

	protected SortedMap<Integer, ClientHandler> clients;
	private NetworkConnectionListener networkConnectionListener;

	private static int networkIds = 1;

	public NetworkComponentServer() {
		super();

		this.clients = Collections
				.synchronizedSortedMap(new TreeMap<Integer, ClientHandler>());
		this.networkConnectionListener = new NetworkConnectionListener(this);
	}

	@Override
	public void startListening( InterfaceAddress ni, int port ) 
	{
		this.networkConnectionListener.startNetworkConnectionListener( ni, port );

	}
	
	@Override
	public void stopListening() {
		this.networkConnectionListener.stopNetworkConnectionListener();
	}

	@Override
	boolean handleProtocolCommand(INetworkCommand command) {

		// if client asks for networkId, tell him
		if (command instanceof ClientQueryConnect) {
			sendCommand(command.getSender(),
					new ServerAckConnect(command.getSender()));
			return true;
		}

		// if client wants to disconnect, acknowledge and terminate this thread
		if (command instanceof ClientQueryDisconnect) {
			sendCommand(command.getSender(), new ServerAckDisconnect());
			ClientHandler c = this.clients.get(command.getSender());
			c.doClose();
			return true;
		}
		return false;
	}

	@Override
	public void sendCommand(INetworkCommand command) {
		synchronized (this.clients) {
			for (ClientHandler c : this.clients.values()) {
				c.sendToClient(command);
			}
		}
	}

	@Override
	public void sendCommand(int clientNetworkId, INetworkCommand command) {
		synchronized (this.clients) {
			ClientHandler c = this.clients.get(clientNetworkId);
			c.sendToClient(command);
		}
	}

	@Override
	public void addClient(Socket s) {
		ClientHandler ch = new ClientHandler(s, this, networkIds++);
		this.clients.put(ch.getNetworkId(), ch);
		ch.start();
	}

	@Override
	public void removeClient(ClientHandler ch) {
		this.clients.remove(ch.getNetworkId());
	}

	@Override
	public List<ClientHandler> getClients() {
		List<ClientHandler> l = new ArrayList<ClientHandler>();
		synchronized (this.clients) {
			for (ClientHandler ch : this.clients.values()) {
				l.add(ch);
			}
		}
		return l;
	}

	@Override
	public Integer getNetworkId() {
		return 0;
	}

	@Override
	public boolean connect(String host, int port) {
		throw new RuntimeException("connect can't be used on the server");
	}

	@Override
	public void disconnect() {
		throw new RuntimeException("disconnect can't be used on the server");
	}
}
