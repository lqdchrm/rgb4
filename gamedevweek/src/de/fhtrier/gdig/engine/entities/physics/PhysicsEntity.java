package de.fhtrier.gdig.engine.entities.physics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import de.fhtrier.gdig.demos.jumpnrun.common.Constants;
import de.fhtrier.gdig.engine.entities.Entity;

public class PhysicsEntity extends Entity {

	private float prevPos[];
	private float vel[];
	private float acc[];
	private Shape bounds;

	/**
	 * @param id
	 */
	/**
	 * @param id
	 */
	public PhysicsEntity(int id) {
		super(id);

		this.prevPos = new float[] { 0, 0, 0, 0, 1, 1, 0};
		this.vel = new float[] { 0, 0, 0, 0, 0, 0, 0};
		this.acc = new float[] { 0 ,0 ,0 ,0 ,0 ,0 ,0};
		this.bounds = null;
	}

	@Override
	public void update(int deltaInMillis) {
		if (isActive()) {

			float secs = deltaInMillis / 1000.0f;

			for (int i = 0; i < getData().length; i++) {
				// simple euler integration
				this.vel[i] += this.acc[i] * secs;
				this.getData()[i] = this.getData()[i] + this.vel[i] * secs;
			}
		}

		super.update(deltaInMillis);
	}

	@Override
	public void renderImpl(Graphics graphicContext) {
		super.renderImpl(graphicContext);

		if (this.bounds != null && Constants.Debug.drawBounds) {
			graphicContext.setColor(Constants.Debug.boundColor);
			graphicContext.draw(this.bounds);
		}
	}

	public void initData(float[] pos) {
		setData(pos);
		for (int i = 0; i < getData().length; i++) {
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

	public Shape getBounds() {
		return this.bounds;
	}

	public void setBounds(Shape bounds) {
		this.bounds = bounds;
	}
}
