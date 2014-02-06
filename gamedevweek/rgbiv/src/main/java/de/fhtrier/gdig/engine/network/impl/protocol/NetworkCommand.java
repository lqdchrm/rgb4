package de.fhtrier.gdig.engine.network.impl.protocol;

import de.fhtrier.gdig.engine.network.INetworkCommand;

public class NetworkCommand implements INetworkCommand {

	private static final long serialVersionUID = 4800886375129405469L;
	private int sender;
	private boolean handled;

	public NetworkCommand() {
		sender = -1;
		handled = false;
	}

	@Override
	public int getSender() {
		return sender;
	}

	@Override
	public void setSender(int sender) {
		this.sender = sender;
	}

	@Override
	public boolean isHandled() {
		return handled;
	}

	@Override
	public void setHandled(boolean handled) {
		this.handled = handled;
	}
	
	@Override
	public String toString() {
		return "Sender: " + sender + " | " + "Handled: " + handled;
	}
}
