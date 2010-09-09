package de.fhtrier.gdig.demos.jumpnrun.client.network.protocol;

import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;

public class QueryDoomsdayDeviceExplosionRadius extends NetworkData
{

	public float minRadius;
	public float maxRadius;
	public int damageColor;
	public boolean isActive;

	public QueryDoomsdayDeviceExplosionRadius(int id)
	{
		super(id);
	}

}
