package de.fhtrier.gdig.engine.network.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class NetworkConnectionListener extends Thread {

	private int port;
	private NetworkComponentImpl networkComponent;
	private ServerSocket ss;

	public NetworkConnectionListener(NetworkComponentImpl networkComponent) {
		this.networkComponent = networkComponent;
	}

	@Override
	public void run() {
		this.ss = initializeSocket(this.port);
		while (true) {
			Socket s = acceptClients(this.ss);
			this.networkComponent.addClient(s);
			System.out.println("Client " + s.getInetAddress() + " connected.");
		}
	}

	private ServerSocket initializeSocket(int port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
			System.exit(1);
		}
		return serverSocket;
	}

	private Socket acceptClients(ServerSocket serverSocket) {
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
		} catch (SocketException e) {
			// exited normally
		} catch (IOException e) {
			System.err.println("Accept failed.");
			e.printStackTrace();
			System.exit(1);
		}
		return clientSocket;
	}

	private void cleanup() {
		try {
			this.ss.close();
		} catch (IOException e) {
			System.err.println("Closing serverSocket failed.");
			e.printStackTrace();
		}
	}

	public void startNetworkConnectionListener(int port) {
		this.port = port;
		start();
	}

	public void stopNetworkConnectionListener() {
		cleanup();
	}
}
