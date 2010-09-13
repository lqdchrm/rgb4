package de.fhtrier.gdig.demos.jumpnrun.server.network.protocol;

import java.util.HashMap;

import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkPlayer;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;

public class AckNewPlayerList extends ProtocolCommand {

	private HashMap<Integer, NetworkPlayer> playerList;

	public HashMap<Integer, NetworkPlayer> getPlayerList() {
		return playerList;
	}

	public AckNewPlayerList(HashMap<Integer, NetworkPlayer> playerList) {
		super("AckNewPlayerList");
		this.playerList = playerList;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4891374201810902491L;

}
