package de.fhtrier.gdig.rgb4.identifiers;

import org.newdawn.slick.Color;

import de.fhtrier.gdig.engine.support.Configuration;

public class Constants {

	public static final float EPSILON = 0.0001f;

	public static class GamePlayConstants extends Configuration {

		public static float gravity = 2000.0f;//981.0f==Erdbeschleunigung
		
		public static float shotSpeed = 1000.0f;
		public static float shotCooldown = 100.0f;
		
		public static float playerWalkSpeed = 4000.0f;
		public static float playerJumpSpeed = 3500.0f;
		public static float playerMaxSpeed = 850.0f;
		
		public static final float playerIdleTriggerSpeed = 10.0f;
		public static final float playerFallTriggerSpeed = 5.0f;
	}

	public static class Debug extends Configuration {
		public static boolean drawBounds = true;
		
		public static Color boundColor = Color.green;
		public static Color overlayColor = Color.white;
		
		public static boolean showCollisions = false;
		
		public static boolean shadersActive = true;
		public static boolean shadersAutoDisable = true;
		
		public static boolean tileMapLogicDebug = false;
		
		public static boolean finiteStateMachineDebug = true;
		
		public static boolean guiDebug = false;
	}
	
	public static class ControlConfig extends Configuration {
		
		public static String REFWALKLEFT = "KEY_LEFT";
		public static String REFWALKRIGHT = "KEY_RIGHT";
		public static String REFJUMP = "KEY_UP";
		public static String REFFIRE = "KEY_SPACE";
		public static String REFCHANGEWEAPON = "KEY_X";
		public static String REFCHANGECOLOR = "KEY_Y";
		public static String REFMENU = "KEY_ESCAPE";
		
	}
	
}
