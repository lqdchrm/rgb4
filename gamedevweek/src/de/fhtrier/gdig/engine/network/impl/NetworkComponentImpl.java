package de.fhtrier.gdig.engine.network.impl;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public abstract class NetworkComponentImpl extends NetworkComponent {

	protected Socket socket;

	public NetworkComponentImpl() {
		this.commands = Collections
				.synchronizedList(new ArrayList<INetworkCommand>());
		this.listeners = new ArrayList<INetworkCommandListener>();
	}

	void addCommand(INetworkCommand c) {
		this.commands.add(c);
	}

	@Override
	public void update() {
		synchronized (this.commands) {
			for (INetworkCommand command : this.commands) {

				if (!command.isHandled()) {
					if (command instanceof ProtocolCommand) {
						if (handleProtocolCommand(command)) {
							command.setHandled(true);
						}
					}
				}

				for (INetworkCommandListener l : this.listeners) {
					l.notify(command);
				}
			}
			this.commands.clear();
		}
	}

	abstract boolean handleProtocolCommand(INetworkCommand command);
}
