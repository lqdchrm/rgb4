package de.fhtrier.gdig.engine.network.impl;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.newdawn.slick.util.Log;

public class NetworkConnectionListener extends Thread {

	private int port;
	private InterfaceAddress networkInterface;
	private NetworkComponentImpl networkComponent;
	private ServerSocket ss;

	public NetworkConnectionListener(NetworkComponentImpl networkComponent) {
		this.networkComponent = networkComponent;
	}

	@Override
	public void run() {
		this.ss = initializeSocket(this.networkInterface, this.port);
		while (true) {
			Socket s = acceptClients(this.ss);
			this.networkComponent.addClient(s);
			Log.info("Client " + s.getInetAddress() + " connected.");
		}
	}

	private ServerSocket initializeSocket(InterfaceAddress ni, int port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port, 0, ni.getAddress());
		} catch (IOException e) {
			Log.error("Could not listen on port: " + port + ".");
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
			Log.error("Accept failed.");
			e.printStackTrace();
			System.exit(1);
		}
		return clientSocket;
	}

	private void cleanup() {
		try {
			this.ss.close();
		} catch (IOException e) {
			Log.error("Closing serverSocket failed.");
			e.printStackTrace();
		}
	}

	public void startNetworkConnectionListener(InterfaceAddress ni, int port) {
		this.port = port;
		this.networkInterface = ni;
		start();
	}

	public void stopNetworkConnectionListener() {
		cleanup();
	}
}
