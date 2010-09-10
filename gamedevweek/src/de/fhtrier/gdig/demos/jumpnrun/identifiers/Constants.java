package de.fhtrier.gdig.demos.jumpnrun.identifiers;

import org.newdawn.slick.Color;

import de.fhtrier.gdig.engine.helpers.Configuration;

public class Constants
{

	public static final float EPSILON = 0.0001f;

	public static class GamePlayConstants extends Configuration
	{
		public static float gravity = 2000.0f;

		@ShowAsSlider(maxValue = 50, minValue = 1)
		public static int winningKills = 5;
		
		public static boolean friendyFire = true; // if true you can damage team-mates

		@ShowAsSlider(maxValue = 10000, minValue = 10)
		public static float shotSpeed = 1000.0f;
		public static float shotCooldown = 100.0f;
		
		public static float playerWalkSpeed = 4000.0f;
		public static float playerJumpSpeed = 1000.0f;
		public static float playerMaxSpeed = 850.0f;

		public static final float playerIdleTriggerSpeed = 5.0f;
		public static final float playerFallingTriggerSpeed = 500.0f;
		public static final float playerLandingTriggerSpeed = 10.0f;
		public static final float playerGroundDrag = 0.005f;
		public static final float playerAirDrag = 0.000001f;

		public static final Color DefaultPlayerTextColor = Color.white;	

		public static float colissionPointDistance = 2.0f;

		public static float playerMaxJumpSpeed = 1000.0f;
	}

	public static class Debug extends Configuration
	{
		public static boolean showDebugOverlay = false;
		
		public static boolean drawBounds = true;
		
		public static Color boundColor = Color.green;
		public static Color overlayColor = Color.white;
		
		public static boolean showCollisions = false;
		
		public static boolean shadersActive = true;
		public static boolean shadersAutoDisable = true;
		
		public static boolean tileMapLogicDebug = false;

		public static boolean forceNoFBO = false;

		public static boolean finiteStateMachineDebug = false;
		
		public static boolean guiDebug = false;
		
		public static boolean networkDebug = false;
		
		public static boolean factoryDebug = false;
	}
	
	public static class Level extends Configuration
	{
		public static int collisionLayer = 1;
		public static int logicLayer = 3;
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
