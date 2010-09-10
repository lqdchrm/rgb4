package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;

public class DoomsdayDeviceExplosionData extends NetworkData
{

	public float maxRadius;
	public int damageColor;
	public boolean isActive;

	public DoomsdayDeviceExplosionData(int id)
	{
		super(id);
	}

}
