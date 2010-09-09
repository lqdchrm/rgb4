package de.fhtrier.gdig.engine.network.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import de.fhtrier.gdig.engine.network.NetworkServerObject;

public class NetworkBroadcastListener extends Thread {
	private NetworkServerObject serverObject;
	private DatagramSocket socket;
	private boolean halt;

	public NetworkBroadcastListener(String serverName, String map,
			String version, int port) {
		serverObject = new NetworkServerObject();
		serverObject.setPort(port);
		serverObject.setMap(map);
		serverObject.setName(serverName);
		serverObject.setVersion(version);

		halt = false;

		try {
			socket = new DatagramSocket(50000);
		} catch (SocketException e2) {
			System.out.println(e2.getLocalizedMessage());
		}
	}

	public void run() {
		while (!halt) {
			try {
				byte[] rcvData = new byte[31];
				DatagramPacket packet = new DatagramPacket(rcvData, 31);
				socket.receive(packet);

				String rcvString = new String(rcvData);

				if (rcvString.startsWith("CQ,CQ,CQ RGB4 ")) {
					serverObject.setLatency(System.currentTimeMillis());

					Socket socketToClient = new Socket(packet.getAddress(),
							50000);
					ObjectOutputStream streamToClient = new ObjectOutputStream(
							socketToClient.getOutputStream());
					streamToClient.writeObject(serverObject);

					if (serverObject.getIp() == null) {
						sleep(10); // Wait for the Client to get our IP if we
									// didn't send it in the package
					}

					streamToClient.close();
					socketToClient.close();
				}
			} catch (InterruptedException e) {
				finish();
				System.out.println(e.getLocalizedMessage());
			} catch (NullPointerException e) {
				finish();
				System.out.println(e.getLocalizedMessage());
			} catch (IOException e) {
				finish();
				System.out.println(e.getLocalizedMessage());
			}
		}

		finish();
	}

	public void finish() {
		halt = true;
		socket.close();
	}
}
