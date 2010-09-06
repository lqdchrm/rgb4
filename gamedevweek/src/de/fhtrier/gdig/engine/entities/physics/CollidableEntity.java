package de.fhtrier.gdig.engine.entities.physics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;

public class CollidableEntity extends MoveableEntity
{

	private Shape bounds;

	public CollidableEntity(final int id)
	{
		super(id);

		CollisionManager.addEntety(this);

		this.bounds = null;
	}

	public Shape getBounds()
	{
		return this.bounds;
	}

	@Override
	public void renderImpl(final Graphics graphicContext)
	{
		super.renderImpl(graphicContext);

		if (this.bounds != null)
		{
			graphicContext.draw(this.bounds);
		}
	}

	public void setBounds(final Shape bounds)
	{
		this.bounds = bounds;
	}

}
