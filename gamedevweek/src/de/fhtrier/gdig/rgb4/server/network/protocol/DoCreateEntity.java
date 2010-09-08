package de.fhtrier.gdig.rgb4.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.rgb4.identifiers.EntityType;

public class DoCreateEntity extends ProtocolCommand {

	private static final long serialVersionUID = -2494668970339654827L;

	private int entityId;
	private EntityType type;

	public DoCreateEntity(int entityId, EntityType type) {
		super("DoCreateEntity");

		this.entityId = entityId;
		this.type = type;
	}

	public int getEntityId() {
		return entityId;
	}

	public EntityType getType() {
		return type;
	}
}
