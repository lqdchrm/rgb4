package de.fhtrier.gdig.engine.network;

import java.io.Serializable;

public interface INetworkCommand extends Serializable {

	int getSender();

	void setSender(int sender);

	boolean isHandled();

	void setHandled(boolean handled);
}
