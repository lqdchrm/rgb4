package de.fhtrier.gdig.demos.jumpnrun.common.network;

import java.io.Serializable;

public class EntityData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1518408463468363862L;
	
	public int id;
	public int state;
	public float[] data;

	public EntityData() {
		this.id = -1;
		this.state = -1;
		this.data = new float[9];
	}
}
