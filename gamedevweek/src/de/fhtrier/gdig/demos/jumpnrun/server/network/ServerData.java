package de.fhtrier.gdig.demos.jumpnrun.server.network;

import java.util.TreeMap;

import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class ServerData extends TreeMap<Integer, NetworkData> implements
		INetworkCommand {

	private static final long serialVersionUID = 5621266482979265456L;
	private int sender;
	private boolean handled;

	public ServerData() {
		this.sender = NetworkComponent.getInstance().getNetworkId();
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

}
