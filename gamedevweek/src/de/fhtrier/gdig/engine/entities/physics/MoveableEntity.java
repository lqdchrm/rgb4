package de.fhtrier.gdig.engine.entities.physics;

import de.fhtrier.gdig.engine.entities.Entity;

public class MoveableEntity extends Entity {

	private float prevPos[];
	private float vel[];
	private float acc[];

	/**
	 * @param id
	 */
	/**
	 * @param id
	 */
	public MoveableEntity(int id) {
		super(id);

		this.prevPos = new float[] { 0, 0, 0, 0, 0, 0, 1, 1, 0};
		this.vel = new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0};
		this.acc = new float[] { 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0};
	}

	@Override
	public void update(int deltaInMillis) {
		if (isActive()) {

			float secs = deltaInMillis / 1000.0f;

			for (int i = 0; i < 9; i++) {
				// simple euler integration
				this.vel[i] += this.acc[i] * secs;
				this.getData()[i] = this.getData()[i] + this.vel[i] * secs;
			}
		}

		super.update(deltaInMillis);
	}

	public void initData(float[] pos) {
		setData(pos);
		for (int i = 0; i < 9; i++) {
			this.prevPos[i] = pos[i];
		}
	}

	public float[] getVel() {
		return this.vel;
	}

	public void setVel(float[] vel) {
		this.vel = vel;
	}

	public float[] getAcc() {
		return this.acc;
	}

	public void setAcc(float[] acc) {
		this.acc = acc;
	}

	public float[] getPrevPos() {
		return this.prevPos;
	}

}
