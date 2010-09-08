package de.fhtrier.gdig.engine.entities.physics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;

public class CollidableEntity extends MoveableEntity
{

	private Shape bounds;

	public CollidableEntity(int id, EntityType type)
	{
		super(id, type);
		this.bounds = null;
	}

	public Shape getBounds()
	{
		return this.bounds;
	}

	@Override
	public void renderImpl(Graphics graphicContext, Image frameBuffer)
	{
		super.renderImpl(graphicContext, frameBuffer);

		if (this.bounds != null && Constants.Debug.drawBounds)
		{
			graphicContext.setColor(Constants.Debug.boundColor);

			graphicContext.draw(this.bounds);
		}
	}

	public void setBounds(Shape bounds)
	{
		this.bounds = bounds;
	}

}
