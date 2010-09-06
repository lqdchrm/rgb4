package de.fhtrier.gdig.demos.jumpnrun.common.entities.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.physics.CollidableEntity;

public class CollisionManager
{

	/**
	 * Speichert alle {@link CollidableEntity}'s zwichen denen die Kollision
	 * geprüft werden soll.
	 */
	private static List<CollidableEntity> entetys;

	/**
	 * speichert in einer Map die daten der untereinander collidierenden
	 * {@link CollidableEntity}'s.
	 */
	private static Map<CollidableEntity, List<CollidableEntity>> collisionmap;

	static
	{
		CollisionManager.entetys = new ArrayList<CollidableEntity>();
		CollisionManager.collisionmap = new HashMap<CollidableEntity, List<CollidableEntity>>();
	}

	/**
	 * fügt eine Entety der Überwachung hinzu.
	 * 
	 * @param entety
	 *            Die Entety.
	 */
	public static void addEntety(final CollidableEntity entety)
	{
		CollisionManager.entetys.add(entety);
	}

	/**
	 * Überprüft ob zwei Entetys Collidieren.
	 * 
	 * @param e1
	 *            Entety 1
	 * @param e2
	 *            Entety 2
	 * @return Ob bide sich überschneiden.
	 */
	private static boolean collide(final CollidableEntity e1,
			final CollidableEntity e2)
	{
		if (e1.getBounds() == null || e2.getBounds() == null)
		{
			return false;
		}

		return CollisionManager.getTransformedBounds(e1).intersects(
				CollisionManager.getTransformedBounds(e2));
	}

	private static Shape generateMovmentshape(final Shape r1, final Shape r2)
	{

		final float maxLeft = Math.min(r1.getMinX(), r2.getMinX());
		final float maxRight = Math.max(r1.getMaxX(), r2.getMaxX());
		final float maxTop = Math.min(r1.getMinY(), r2.getMinY());
		final float maxBottom = Math.max(r1.getMaxY(), r2.getMaxY());

		final float[] points = new float[16];

		final float[] r1Points = r1.getPoints();
		final float[] r2Points = r2.getPoints();
		int pointIndex = 0;

		for (int i = 0; i < r1Points.length; i += 2)
		{
			if (r1Points[i] == maxLeft || r1Points[i] == maxRight
					|| r1Points[i + 1] == maxBottom
					|| r1Points[i + 1] == maxTop)
			{
				points[pointIndex] = r1Points[i];
				points[pointIndex + 1] = r1Points[i + 1];
				pointIndex += 2;
			}
		}
		for (int i = 0; i < r2Points.length; i += 2)
		{
			if (r2Points[i] == maxLeft || r2Points[i] == maxRight
					|| r2Points[i + 1] == maxBottom
					|| r2Points[i + 1] == maxTop)
			{
				points[pointIndex] = r2Points[i];
				points[pointIndex + 1] = r2Points[i + 1];
				pointIndex += 2;
			}
		}

		final float[] erg = Arrays.copyOf(points, pointIndex);

		final Polygon p = new Polygon(erg);

		return p;

	}

	/**
	 * Entity BoundingBox is relative to center, i.e. we need to transform it to
	 * a common basis. Assuming the entity belongs to the same parent as the
	 * tilemap we use center offset and position
	 * 
	 * @return transformed Shape
	 */
	private static Shape getTransformedBounds(final CollidableEntity entety)
	{
		Shape rec1 = entety.getBounds().transform(
				Transform.createTranslateTransform(entety.getData()[Entity.X],
						entety.getData()[Entity.Y]));
		Shape rec2 = entety.getBounds().transform(
				Transform.createTranslateTransform(
						entety.getPrevPos()[Entity.X],
						entety.getPrevPos()[Entity.Y]));

		Shape erg = generateMovmentshape(rec1, rec2);
		// gebe nicht erg zurüc da die erwieterte Kollisionbehanlung bei
		// bewegung noch nicht funktioniert.
		return rec1;
	}

	/**
	 * Liefert zurück mit welchen entetys das Übergebene Entety collidiert.
	 * 
	 * @param entety
	 *            Die entety welcer die Kollisionsobjekte übergeben werden
	 *            sollen.
	 * @return Die lisete der Kollisionsobjekte.
	 */
	public static List<CollidableEntity> iColideWith(
			final CollidableEntity entety)
	{
		final List<CollidableEntity> list = CollisionManager.collisionmap
				.get(entety);
		return list == null ? new ArrayList<CollidableEntity>() : list;
	}

	/**
	 * Entfernt ein {@link CollidableEntity} aus der Überwachung.
	 * 
	 * @param entety
	 *            Das Entety welches aus der Überwachung entfernt werden soll.
	 */
	public static void removeEntety(final CollidableEntity entety)
	{
		CollisionManager.entetys.remove(entety);
	}

	/**
	 * Updated die Colisionen. Muss jedes Frame aufgerufen werden, sonst gibt
	 * die Kollisionserkennung falsche werte zurück.
	 */
	public static void update()
	{
		CollisionManager.collisionmap.clear();
		for (int x = 0; x < CollisionManager.entetys.size(); ++x)
		{
			for (int y = 0; y < x; ++y)
			{
				final CollidableEntity e1 = CollisionManager.entetys.get(x);
				final CollidableEntity e2 = CollisionManager.entetys.get(y);
				if (CollisionManager.collide(e1, e2))
				{
					if (!CollisionManager.collisionmap.containsKey(e1))
					{
						CollisionManager.collisionmap.put(e1,
								new ArrayList<CollidableEntity>());
					}
					if (!CollisionManager.collisionmap.containsKey(e2))
					{
						CollisionManager.collisionmap.put(e2,
								new ArrayList<CollidableEntity>());
					}
					CollisionManager.collisionmap.get(e1).add(e2);
					CollisionManager.collisionmap.get(e2).add(e1);
				}
			}
		}

	}

}
