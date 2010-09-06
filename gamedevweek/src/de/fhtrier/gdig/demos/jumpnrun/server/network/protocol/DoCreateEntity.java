package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

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
