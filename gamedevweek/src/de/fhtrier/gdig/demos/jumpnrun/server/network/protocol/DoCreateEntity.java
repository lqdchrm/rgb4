package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoCreateEntity extends ProtocolCommand {

	private static final long serialVersionUID = -2494668970339654827L;

	private int entityId;
	private int parentId;
	private EntityType type;
	private boolean whileJoining;

	public DoCreateEntity(int entityId, int parentId, EntityType type, boolean whileJoining) {
		super("DoCreateEntity");

		this.entityId = entityId;
		this.type = type;
		this.whileJoining = whileJoining;
	}

	public int getEntityId() {
		return entityId;
	}

	public EntityType getType() {
		return type;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getParentId() {
		return parentId;
	}
	
	public boolean isWhileJoining() {
		return whileJoining;
	}
}
