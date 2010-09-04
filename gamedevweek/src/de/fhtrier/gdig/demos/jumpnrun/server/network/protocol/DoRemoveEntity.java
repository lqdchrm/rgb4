package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoRemoveEntity extends ProtocolCommand {

	private static final long serialVersionUID = -7514424748885675255L;

	private int id;
	
	public DoRemoveEntity(int id) {
		super("DoRemoveEntity");
		this.id = id;
	}
	
	public int getEntityId() {
		return id;
	}

}
