package de.fhtrier.gdig.engine.physics.entities;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;

public class MoveableEntity extends Entity
{

	private float prevPos[];
	private float vel[];
	private float acc[];

	private float drag;

	/**
	 * @param id
	 */
	public MoveableEntity(int id, EntityType type)
	{
		super(id, type);

		this.prevPos = new float[7];
		this.prevPos[SCALE_X] = 1;
		this.prevPos[SCALE_Y] = 1;

		this.vel = new float[7];
		this.acc = new float[7];

		this.drag = 0.0f;
	}

	@Override
	public void update(int deltaInMillis)
	{
		if (isActive())
		{

			float secs = deltaInMillis / 1000.0f;

			for (int i = 0; i < getData().length; i++)
			{
				// simple euler integration
				// reibung folgt hier
				// -1/(x+1)+1 Sollte besser skalieren bei schwankender framrate,
				// und kann nicht durch zu große werte merkwürdige effekte
				// hintersich her ziehen
				// Diese Formel nimmt werte zwichen 0 und 1 an.
				// die implementierte formel nimmt werte zwichen 0 und dem
				// kehrwert von secs an multipliziert mit secs ergibt dies einen
				// wert zwichen 0 und 1.
				if (i == X || i == Y)
				{
					// vel[i] *= (-1.0f / (drag + secs) + (1.0f / secs)) * secs;
					// Dies ist eine alternative formel der oberen davon
					// ausgehend das nur Positive Werte benutzt werden.
					if (drag != 0 && Math.abs(acc[Entity.X]) < Constants.EPSILON)
					{
						vel[i] *= (secs / (drag + secs));
					}
				}
				
				this.vel[i] += this.acc[i] * secs;
				
				this.getData()[i] = this.getData()[i] + this.vel[i] * secs;
			}
		}

		super.update(deltaInMillis);
	}

	public void initData(float[] pos)
	{
		setData(pos);
		for (int i = 0; i < prevPos.length; i++)
		{
			this.prevPos[i] = pos[i];
		}
	}

	public float[] getVel()
	{
		return this.vel;
	}

	public void setVel(float[] vel)
	{
		this.vel = vel;
	}

	public float[] getAcc()
	{
		return this.acc;
	}

	public void setAcc(float[] acc)
	{
		this.acc = acc;
	}

	public float[] getPrevPos()
	{
		return this.prevPos;
	}

	public float getDrag()
	{
		return drag;
	}

	public void setDrag(float drag)
	{
		this.drag = drag;
	}

}
