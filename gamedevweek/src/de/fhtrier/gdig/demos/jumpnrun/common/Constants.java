package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Color;

public class Constants {

	public static float EPSILON = 0.0001f;
	
	public static class GamePlayConstants {
		public static float gravity = 981.0f;
		public static float shotSpeed = 1000.0f;
		public static float shotCooldown = 100.0f;
		public static float walkVelo = 800.f;
		public static float JumpVelo = 800.f;
	}
	
	public static class Debug{
		
		public static final int NONE = 0;
		public static final int LOW = 1;
		public static final int MEDIUM = 2;
		public static final int HIGH = 3;
		
		public static boolean drawBounds = true;
		public static Color boundColor = Color.green;
		public static int debugLevel = 0;
	}
}
