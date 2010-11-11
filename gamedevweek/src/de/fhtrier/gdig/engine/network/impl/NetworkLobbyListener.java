package de.fhtrier.gdig.engine.network.impl;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import de.fhtrier.gdig.engine.network.IAddServerListener;
import de.fhtrier.gdig.engine.network.NetworkServerObject;

public class NetworkLobbyListener extends Thread {
	private ServerSocket socket;
	private boolean halt;

	private IAddServerListener parent;

	public NetworkLobbyListener(IAddServerListener myParent) {
		parent = myParent;
		halt = false;
	}

	public void setSocket(ServerSocket rcvSocket) {
		socket = rcvSocket;
	}

	@Override
	public void run() {
		while (!halt) {
			try {
				Socket userSocket = socket.accept();

				ObjectInputStream serverStream = new ObjectInputStream(
						userSocket.getInputStream());
				InetAddress serverAddress = userSocket.getInetAddress();
				NetworkServerObject server = (NetworkServerObject) serverStream
						.readObject();
				// Die Adresse des Servers muss nicht die addresse sein von der
				// von dem das Serverobject kommt, falls der Server mehrere
				// Interfaces hat. Daher muss set IP auf Serverseite gemacht
				// werden.
				// server.setIp(serverAddress);

				long curr = System.currentTimeMillis();

				server.setLatency(curr - server.getLatency());

				if (!halt) {
					parent.addServer(server);
				}

				serverStream.close();
			} catch (EOFException e) {
				// Ignore
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			} catch (ClassNotFoundException e) {
				finish();
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	public void finish() {
		try {
			if (socket != null && socket.isClosed() == false)
				socket.close();

			halt = true;
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
