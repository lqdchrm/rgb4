package de.fhtrier.gdig.demos.jumpnrun.common.network;

import java.io.Serializable;
import java.util.Arrays;

public class NetworkData implements Serializable {

	private static final long serialVersionUID = -1518408463468363862L;

	public int id;
	public float[] data;

	public NetworkData(int id) {
		this.id = id;
		this.data = new float[7];
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " : " + "ID: " + id + " : " + "Data: " + Arrays.toString(data);
	}
}
