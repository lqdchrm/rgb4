package de.fhtrier.gdig.engine.physics;

import org.newdawn.slick.geom.Shape;

public class Collisions {
	private static float[] result = new float[2];

	public static float[] getIntersectionDepth(Shape rectA, Shape rectB) {

		// Calculate half sizes.
		float halfWidthA = rectA.getWidth() / 2.0f;
		float halfHeightA = rectA.getHeight() / 2.0f;
		float halfWidthB = rectB.getWidth() / 2.0f;
		float halfHeightB = rectB.getHeight() / 2.0f;

		// Calculate current and minimum-non-intersecting distances between
		// centers.
		float distanceX = rectA.getCenterX() - rectB.getCenterX();
		float distanceY = rectA.getCenterY() - rectB.getCenterY();
		float minDistanceX = halfWidthA + halfWidthB;
		float minDistanceY = halfHeightA + halfHeightB;

		// If we are not intersecting at all, return (0, 0).
		if ((Math.abs(distanceX) >= minDistanceX)
				|| (Math.abs(distanceY) >= minDistanceY)) {
			result[0] = result[1] = 0;
			return result;
		}

		// Calculate and return intersection depths.
		result[0] = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX
				- distanceX;
		result[1] = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY
				- distanceY;
		return result;
	}
}
