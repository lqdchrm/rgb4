package de.fhtrier.gdig.engine.physics.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.graphics.shader.Shader;

public class CollidableEntity extends MoveableEntity {

	private Shape bounds;

	public CollidableEntity(int id, EntityType type) {
		super(id, type);
		this.bounds = null;
	}

	public Shape getBounds() {
		return this.bounds;
	}

	@Override
	public void renderImpl(Graphics graphicContext, Image frameBuffer) {
		super.renderImpl(graphicContext, frameBuffer);

		if (this.bounds != null && Constants.Debug.drawBounds) {
			if (Constants.Debug.shadersActive) {
				Shader.pushShader(null);
			}

			graphicContext.setColor(Constants.Debug.boundColor);

			graphicContext.draw(this.bounds);

			if (Constants.Debug.shadersActive) {
				Shader.popShader();
			}
		}
	}

	public void setBounds(Shape bounds) {
		this.bounds = bounds;
	}

}
