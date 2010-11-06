package de.fhtrier.gdig.engine.network.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import de.fhtrier.gdig.engine.network.NetworkServerObject;

public class NetworkBroadcastListener extends Thread {
	private NetworkServerObject serverObject;
	private DatagramSocket socket;
	private boolean halt;

	/**
	 * 
	 * @param serverName
	 *            Der Name des Servers
	 * @param map
	 * @param version
	 *            Die Version des Servers
	 * @param adresse
	 *            Die Adresse des Servers
	 * @param port
	 *            Der Port des Servers
	 */
	public NetworkBroadcastListener(String serverName, String map,
			String version, InetAddress adresse, int port) {
		serverObject = new NetworkServerObject();
		serverObject.setPort(port);
		serverObject.setMap(map);
		serverObject.setName(serverName);
		serverObject.setVersion(version);
		serverObject.setIp(adresse);

		halt = false;

		try {
			socket = new DatagramSocket(50000);
		} catch (SocketException e2) {
			System.out.println(e2.getLocalizedMessage());
		}
	}

	// private boolean isOnSubnet( InterfaceAddress receiver, InetAddress sender
	// )
	// {
	// byte[] receiverBytes = receiver.getAddress().getAddress();
	// byte[] senderBytes = sender.getAddress();
	// byte[] subnetBytes = new byte[4];
	// short length = receiver.getNetworkPrefixLength();
	// int position = 0;
	//
	// if ( length < 8 )
	// {
	// subnetBytes[0] = 0;
	// subnetBytes[1] = 0;
	// subnetBytes[2] = 0;
	// subnetBytes[3] = (byte) (Math.pow( 2, length ) / 2);
	// }
	// else if ( length < 16 )
	// {
	// subnetBytes[0] = 0;
	// subnetBytes[1] = 0;
	// subnetBytes[2] = (byte) (Math.pow( 2, length ) / 2);
	// subnetBytes[3] = 127;
	// }
	// else if ( length < 24 )
	// {
	// subnetBytes[0] = 0;
	// subnetBytes[1] = (byte) (Math.pow( 2, length ) / 2);
	// subnetBytes[2] = 127;
	// subnetBytes[3] = 127;
	// }
	// else
	// {
	// subnetBytes[0] = (byte) (Math.pow( 2, length ) / 2);
	// subnetBytes[1] = 127;
	// subnetBytes[2] = 127;
	// subnetBytes[3] = 127;
	// }
	//
	// for ( int x = senderBytes.length - 1; x >= 0; x-- )
	// {
	// if ( (senderBytes[x] & subnetBytes[x]) != (receiverBytes[x] &
	// subnetBytes[x]) )
	// {
	// return false;
	// }
	//
	// position++;
	// }
	//
	// return true;
	// }

	public void run() {
		while (!halt) {
			try {
				byte[] rcvData = new byte[31];
				DatagramPacket packet = new DatagramPacket(rcvData, 31);
				socket.receive(packet);

				String rcvString = new String(rcvData);

				if (rcvString.startsWith("CQ,CQ,CQ RGB4 ")) {

					// HACK not working
					if (true) {
						// if ( isOnSubnet( this.realServerAddress,
						// packet.getAddress() ) )
						serverObject.setLatency(System.currentTimeMillis());

						Socket socketToClient = new Socket(packet.getAddress(),
								50000);
						ObjectOutputStream streamToClient = new ObjectOutputStream(
								socketToClient.getOutputStream());
						streamToClient.writeObject(serverObject);

						streamToClient.close();
						socketToClient.close();
					}
				}
			} catch (NullPointerException e) {
				// finish();
				System.out.println(e.getLocalizedMessage());
			} catch (IOException e) {
				// finish();
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
