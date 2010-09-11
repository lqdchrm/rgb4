package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

/**
 * Command send back from the server to the client to acknowledge the queried
 * creation of an entity. Currently used to inform the Client, which playerId is
 * under local control.
 *  
 * @author roessgro
 * @see QueryCreateEntity 
 * @see DoCreateEntity
 */
public class AckCreateEntity extends ProtocolCommand {

	private static final long serialVersionUID = -4983502199487412022L;
	private int entityId;

	public AckCreateEntity(int entityId) {
		super();
		this.entityId = entityId;
	}

	public int getEntityId() {
		return entityId;
	}
}
