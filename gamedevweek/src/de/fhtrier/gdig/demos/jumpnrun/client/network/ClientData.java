package de.fhtrier.gdig.demos.jumpnrun.client.network;

import java.io.Serializable;

import de.fhtrier.gdig.demos.jumpnrun.common.network.EntityData;
import de.fhtrier.gdig.engine.network.impl.protocol.NetworkCommand;

public class ClientData extends NetworkCommand implements Serializable {

	private static final long serialVersionUID = 6507211207331141555L;

	private EntityData playerData;
	
	public void setPlayerData(EntityData data) {
		this.playerData = data;
	}

	public EntityData getPlayerData() {
		return this.playerData;
	}
}
