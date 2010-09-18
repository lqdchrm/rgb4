package de.fhtrier.gdig.demos.jumpnrun.common.network;


public class DoomsdayDeviceExplosionData extends NetworkData {

	private static final long serialVersionUID = -2106837252361280741L;
	public float outerRadius;
	public int damageColor;
	public boolean isActive;

	public DoomsdayDeviceExplosionData(int id) {
		super(id);
	}

}
