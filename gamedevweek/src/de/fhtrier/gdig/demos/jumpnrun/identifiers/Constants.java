<<<<<<< HEAD:gamedevweek/src/de/fhtrier/gdig/demos/jumpnrun/common/Constants.java
package de.fhtrier.gdig.demos.jumpnrun.common;

import java.net.InetSocketAddress;
import java.net.Proxy.Type;
=======
package de.fhtrier.gdig.demos.jumpnrun.identifiers;
>>>>>>> roessgri/master:gamedevweek/src/de/fhtrier/gdig/demos/jumpnrun/identifiers/Constants.java

import org.newdawn.slick.Color;

import de.fhtrier.gdig.engine.helpers.Configuration;

public class Constants
{

	public static final float EPSILON = 0.0001f;

	public static class GamePlayConstants extends Configuration
	{
		public static float gravity = 2000.0f;

		@ShowAsSlider(maxValue = 10000, minValue = 10)
		public static float shotSpeed = 1000.0f;
		public static float shotCooldown = 100.0f;
		
		public static float playerWalkSpeed = 4000.0f;
		public static float playerJumpSpeed = 1000.0f;
		public static float playerMaxSpeed = 850.0f;

<<<<<<< HEAD:gamedevweek/src/de/fhtrier/gdig/demos/jumpnrun/common/Constants.java
		public static Type enumTest;
=======
		public static final float playerIdleTriggerSpeed = 5.0f;
		public static final float playerGroundDrag = 0.005f;
		public static final float playerAirDrag = 0.000001f;		

		public static float colissionPointDistance = 2.0f;
>>>>>>> roessgri/master:gamedevweek/src/de/fhtrier/gdig/demos/jumpnrun/identifiers/Constants.java
	}

	public static class Debug extends Configuration
	{
		public static boolean drawBounds = true;

		public static Color boundColor = Color.green;
		public static Color overlayColor = Color.white;

		public static boolean showCollisions = false;

		@CommandlineParameter("noShader")
		@DefaultTrue
		public static boolean shadersActive = true;
		public static boolean shadersAutoDisable = true;

		public static boolean tileMapLogicDebug = false;

		@CommandlineParameter("noFBO")
		public static boolean forceNoFBO = false;

		public static boolean finiteStateMachineDebug = false;
		
		public static boolean guiDebug = false;
	}

	public static class ControlConfig extends Configuration
	{

		public static String REFWALKLEFT = "KEY_LEFT";
		public static String REFWALKRIGHT = "KEY_RIGHT";
		public static String REFJUMP = "KEY_UP";
		public static String REFFIRE = "KEY_SPACE";
		public static String REFCHANGEWEAPON = "KEY_X";
		public static String REFCHANGECOLOR = "KEY_Y";
		public static String REFMENU = "KEY_ESCAPE";
<<<<<<< HEAD:gamedevweek/src/de/fhtrier/gdig/demos/jumpnrun/common/Constants.java

	}

	public static class NetworkConfig extends Configuration
	{
		@CommandlineParameter("Server")
		public static boolean isServer;
		@CommandlineParameter("Spectator")
		public static boolean isSpectator;
		@CommandlineParameter("Client")
		public static boolean isClient;
		@CommandlineParameter("Port")
		public static int port;
		@CommandlineParameter("Adress")
		public static InetSocketAddress adress;

	}

=======
	}	
>>>>>>> roessgri/master:gamedevweek/src/de/fhtrier/gdig/demos/jumpnrun/identifiers/Constants.java
}
