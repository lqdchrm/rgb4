package de.fhtrier.gdig.engine.network.impl;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.engine.network.INetworkCommand;

/**
 * This class is used by the server to listen for commands coming from clients.
 * For every connected client there is a ClientHandler
 */
public class ClientHandler extends Thread {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private NetworkComponentImpl netComp;
	private boolean doClose;

	private int clientNetworkId;

	public ClientHandler(Socket s, NetworkComponentImpl netComp,
			int clientNetworkId) {
		this.s = s;

		try {
			this.s.setTcpNoDelay(true);
		} catch (SocketException e) {
			System.out.println("Could not set TCP_NODELAY Flag");
		}

		this.netComp = netComp;
		this.clientNetworkId = clientNetworkId;
		this.doClose = false;

		try {
			this.out = new ObjectOutputStream(s.getOutputStream());
			this.in = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			Log.error("Getting streams from client failed.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!doClose) {
				INetworkCommand command = (INetworkCommand) this.in
						.readObject();

				command.setSender(clientNetworkId);
				this.netComp.addCommand(command);
			}
			this.in.close();
			this.out.close();
			this.s.close();
		} catch (SocketException e) {
			System.out.println("Client disconnected unexpectedly");
		} catch (EOFException e) {
			// ??WTF??
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.netComp.removeClient(this);
			Log.debug("Client " + this.s.getInetAddress() + " disconnected.");
		}
	}

	public void sendToClient(INetworkCommand command) {
		try {
			if (!doClose) {
				this.out.writeObject(command);
				this.out.flush();
				this.out.reset();
			}
		} catch (IOException e) {
			Log.error("Sending to client failed.");
			e.printStackTrace();
		}
	}

	public void doClose() {
		this.doClose = true;
	}

	public Socket getSocket() {
		return this.s;
	}

	public Integer getNetworkId() {
		return clientNetworkId;
	}
}
