package de.fhtrier.gdig.engine.network.impl.protocol;

public abstract class ProtocolCommand extends NetworkCommand {

	private static final long serialVersionUID = 4379151712801009842L;
	private String description;

	public ProtocolCommand(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description + " from " + getSender();
	}
}
