package de.fhtrier.gdig.engine.network.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import de.fhtrier.gdig.engine.network.INetworkCommand;

/**
 * This class is used by clients to listen for commands coming from the server. 
 */
public class ServerHandler extends Thread {

	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private NetworkComponentImpl netComp;
	private boolean doClose;
	
	/** The virtual id by which the client is identified if connected to the server  */
	private int networkId;

	public ServerHandler(Socket s, NetworkComponentImpl netComp) {
		this.s = s;
		this.netComp = netComp;
		this.networkId = -1;
		try {
			this.out = new ObjectOutputStream(s.getOutputStream());
			this.in = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			System.err.println("Erstellen der Streams zum Server fehlgeschlagen.");
			e.printStackTrace();
		}
		this.doClose = false;
	}

	@Override
	public void run() {
		try {
			while (!doClose) {
				INetworkCommand command = (INetworkCommand) this.in.readObject();			
				this.netComp.addCommand(command);
			}
			this.in.close();
			this.out.close();
			this.s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void sendToServer(INetworkCommand command) {
		try {
			this.out.writeObject(command);
			this.out.flush();
			this.out.reset();
		} catch (IOException e) {
			System.err.println("Sending to server failed.");
			e.printStackTrace();
		}
	}

	public void close() {
		this.doClose = true;
	}
	
	public int getNetworkId() {
		return networkId;
	}
}
