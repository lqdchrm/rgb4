package de.fhtrier.gdig.rgb4.client.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.rgb4.identifiers.EntityType;

public class QueryCreateEntity extends ProtocolCommand {

	private static final long serialVersionUID = 4687095068613647172L;

	private EntityType type;

	public QueryCreateEntity(EntityType type) {
		super("QueryCreatePlayer");

		this.type = type;
	}

	public EntityType getType() {
		return type;
	}
}
