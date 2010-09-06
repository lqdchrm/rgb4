package de.fhtrier.gdig.engine.network.impl;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.impl.protocol.ClientQueryConnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ClientQueryDisconnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ServerAckConnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ServerAckDisconnect;

enum LocalState {
	DISCONNECTED, WAITINGFORNETWORKID, READYTOSEND, WAITINGFORDISCONNECT
}

public class NetworkComponentClient extends NetworkComponentImpl {

	private ServerHandler serverHandler;
	private LocalState localState;
	private int networkId;
	private Queue<INetworkCommand> queue;

	public NetworkComponentClient() {
		super();
		this.networkId = -1;
		setState(LocalState.DISCONNECTED);
		this.queue = new LinkedList<INetworkCommand>();
	}

	@Override
	public boolean connect(String host, int port) {
		if (localState == LocalState.DISCONNECTED) {
			try {
				this.socket = new Socket(host, port);
				this.serverHandler = new ServerHandler(this.socket, this);
				this.serverHandler.start();
				askForNetworkId();
			} catch (UnknownHostException e) {
				System.err.println("Unknown host");
				// e.printStackTrace();
				return false;
			} catch (IOException e) {
				// System.err.println("Fail connecting");
				// e.printStackTrace();
				return false;
			}
		} else {
			throw new RuntimeException("already connected");
		}
		return true;
	}

	@Override
	boolean handleProtocolCommand(INetworkCommand command) {

		if (localState == LocalState.WAITINGFORNETWORKID) {
			if (command instanceof ServerAckConnect) {
				this.networkId = ((ServerAckConnect) command).getNetworkId();
				setState(LocalState.READYTOSEND);

				// if commands have queued up, send them
				for (INetworkCommand cmd : queue) {
					sendCommand(cmd);
				}
				queue.clear();
				return true;
			}
		}

		if (localState == LocalState.WAITINGFORDISCONNECT) {
			// if server tells us to disconnect, do it
			if (command instanceof ServerAckDisconnect) {
				this.serverHandler.close();
				setState(LocalState.DISCONNECTED);
				return true;
			}
		}
		return false;
	}

	void setState(LocalState state) {
		if (state == null) {
			throw new IllegalArgumentException("new state must not be null");
		}

		System.out.println("NetworkComponent: Changed state from "
				+ ((localState == null) ? "null" : localState.name()) + " to "
				+ state.name());
		localState = state;
	}

	@Override
	public void disconnect() {
		if (localState == LocalState.READYTOSEND) {
			sendCommand(new ClientQueryDisconnect());
			setState(LocalState.WAITINGFORDISCONNECT);
		}
	}

	void askForNetworkId() {
		this.serverHandler.sendToServer(new ClientQueryConnect());
		setState(LocalState.WAITINGFORNETWORKID);
	}

	@Override
	public void sendCommand(INetworkCommand command) {
		if (localState == LocalState.READYTOSEND) {
			this.serverHandler.sendToServer(command);
		} else {
			queue.add(command);
		}
	}

	@Override
	public Integer getNetworkId() {
		return this.networkId;
	}

	@Override
	public void sendCommand(int clientNetworkId, INetworkCommand command) {
		throw new RuntimeException(
				"send command to specific client is not allowed for clients");
	}

	@Override
	public void startListening(int port) {
		throw new RuntimeException("startListening is not possible on a client");
	}

	@Override
	public void stopListening() {
		throw new RuntimeException("stopListening is not possible on a client");
	}

	@Override
	public List<Socket> getClients() {
		throw new RuntimeException("getClients is not possible on a client");
	}

	@Override
	public void addClient(Socket s) {
		throw new RuntimeException("addClient is not possible on a client");
	}

	@Override
	public void removeClient(ClientHandler c) {
		throw new RuntimeException("removeClient is not possible on a client");
	}
}