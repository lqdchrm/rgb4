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
		public static boolean showCollisions = true;
	}
	
	public static class ControlConfig extends Configuration {
		
		public static String REFWALKLEFT = "KEY_LEFT";
		public static String REFWALKRIGHT = "KEY_RIGHT";
		public static String REFJUMP = "KEY_UP";
		public static String REFFIRE = "KEY_ENTER";
		public static String REFCHANGEWEAPON = "KEY_N";
		public static String REFCHANGECOLOR = "KEY_M";
		public static String REFMENU = "KEY_ESCAPE";
		
	}
	
}
