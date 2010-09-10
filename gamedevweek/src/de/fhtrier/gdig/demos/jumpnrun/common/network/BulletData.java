package de.fhtrier.gdig.demos.jumpnrun.common.network;

public class BulletData extends NetworkData {

	public BulletData(int id) {
		super(id);
	}

	private static final long serialVersionUID = -3776997774121122797L;

	public int bulletColor;

	public int getColor() {
		return bulletColor;
	}

}
