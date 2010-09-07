package de.fhtrier.gdig.engine.entities.physics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import de.fhtrier.gdig.demos.jumpnrun.common.Constants;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;

public class CollidableEntity extends MoveableEntity
{

	private Shape bounds;

	public CollidableEntity(int id, EntityType type)
	{
		super(id, type);
		CollisionManager.addEntity(this);
		this.bounds = null;
	}

	public Shape getBounds()
	{
		return this.bounds;
	}

	@Override
	public void renderImpl(Graphics graphicContext)
	{
		super.renderImpl(graphicContext);

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
