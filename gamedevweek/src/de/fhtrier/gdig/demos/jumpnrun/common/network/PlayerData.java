package de.fhtrier.gdig.demos.jumpnrun.common.network;

public class PlayerData extends NetworkData {

	public PlayerData(int id) {
		super(id);
	}

	private static final long serialVersionUID = -3776997774121122797L;

	public int state;
	
	public int getState() {
		return state;
	}
	
}
