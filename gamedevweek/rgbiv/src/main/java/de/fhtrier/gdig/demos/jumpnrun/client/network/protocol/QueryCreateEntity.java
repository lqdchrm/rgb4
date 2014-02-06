package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckCreateEntity;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

/**
 * Network command (currently) used by the client to query the server to create an object of a specific type.
 * It's usually acknowledged by the server, and whenever the server creates an entity an analogous
 * creation is triggered on the client by <code>DoCreateEntity</code> send back from the server.
 * <p>
 * So the process of creating entities in the game from the client side involves the following steps:
 * <ul>
 * 	<li>sending an <code>QueryCreateEntity</code> to the server
 * 	<li>creation of the entity on the server
 * 	<li>sending an <code>DoCreateEntity</code> from the server to the client
 * 	<li>handling the <code>DoCreateEntity</code> on the client by creating the entity locally
 * 	<li>sending an <code>AckCreateEntity</code> from the server back to the client
 * 	<li>handling the <code>AckCreateEntity</code> adequately on the client
 * </ul>
 * 
 * @author roessgro
 * @see DoCreateEntity
 * @see AckCreateEntity
 */
public class QueryCreateEntity extends ProtocolCommand {

	private static final long serialVersionUID = 4687095068613647172L;

	private EntityType type;

	public QueryCreateEntity(EntityType type) {
		super();

		this.type = type;
	}

	public EntityType getType() {
		return type;
	}
}
