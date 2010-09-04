package de.fhtrier.gdig.demos.jumpnrun.common.network;

import java.io.Serializable;

public class NetworkData implements Serializable {

	private static final long serialVersionUID = -1518408463468363862L;
	
	public int id;
	public float[] data;

//	public NetworkData() {
//		this.id = -1;
//		this.data = new float[7];
//	}
	
	public NetworkData(int id) {
		this.id = id;
		this.data = new float[7];
	}
}
