package de.fhtrier.gdig.engine.entities.physics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class CollidableEntity extends MoveableEntity {

	private Shape bounds;

	
	public CollidableEntity(int id) {
		super(id);

		this.bounds = null;
	}

	@Override
	public void renderImpl(Graphics graphicContext) {
		super.renderImpl(graphicContext);

		if (this.bounds != null) {
			graphicContext.draw(this.bounds);
		}
	}

	public Shape getBounds() {
		return this.bounds;
	}

	public void setBounds(Shape bounds) {
		this.bounds = bounds;
	}
	
}
