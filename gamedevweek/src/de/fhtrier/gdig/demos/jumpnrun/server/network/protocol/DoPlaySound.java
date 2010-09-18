package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class DoPlaySound extends ProtocolCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1913154102805215423L;
	private int soundAssetId;

	public DoPlaySound(int soundAssetId) {
		super("DoPlaySound");

		this.soundAssetId = soundAssetId;
	}

	public int getSoundAssetId() {
		return this.soundAssetId;
	}
}
