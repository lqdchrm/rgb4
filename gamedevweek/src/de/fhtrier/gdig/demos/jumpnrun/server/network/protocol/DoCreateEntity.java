package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoCreateEntity extends ProtocolCommand {

	private static final long serialVersionUID = -2494668970339654827L;

	private int playerId;
	private EntityType type;
	
	public DoCreateEntity(int playerId, EntityType type) {
		super("DoCreateEntity");
		
		this.playerId = playerId;
		this.type = type;
	}

	public int getPlayerId() {
		return playerId;
	}
	
	public EntityType getType() {
		return type;
	}
}
