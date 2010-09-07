package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Color;

import de.fhtrier.gdig.engine.support.Configuration;

public class Constants {

	public static final float EPSILON = 0.0001f;

	public static class GamePlayConstants extends Configuration {

		public static float gravity = 981.0f;
		
		public static float shotSpeed = 1000.0f;
		public static float shotCooldown = 100.0f;
		
		public static float playerWalkVel = 800.0f;
		public static float playerJumpVel = 800.0f;
		public static float playerMaxSpeed = 1000.0f;
	}

	public static class Debug extends Configuration {
		public static boolean drawBounds = true;
		public static Color boundColor = Color.green;
		public static Color overlayColor = Color.white;
		public static boolean showCollisions = true;
	}
}
