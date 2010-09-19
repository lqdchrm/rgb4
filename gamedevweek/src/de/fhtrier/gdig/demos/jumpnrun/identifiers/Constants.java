package de.fhtrier.gdig.demos.jumpnrun.identifiers;

import java.net.InetSocketAddress;

import org.newdawn.slick.Color;

import de.fhtrier.gdig.engine.helpers.Configuration;
public class Constants {

	public static final float EPSILON = 0.0001f;

	public static class GamePlayConstants extends Configuration {
		public static float gravity = 2000.0f;

		@ShowAsSlider(maxValue = 50, minValue = 1)
		public static int winningKills_Deathmatch = 5;
		public static int winningKills_TeamDeathmatch = 25;
		
		public static boolean friendyFire = true; // if true you can damage team-mates

		public static float shotSpeed = 400.0f;
		public static float shotCooldown = 1000.0f;
		public static float colorChangeCooldownWeapon = 1000.0f;
		public static float colorChangeCooldownPlayer = 1000.0f;
		
		public static float playerWalkSpeed = 1000.0f;
		public static float playerJumpSpeed = 1000.0f;
		public static float playerMaxSpeed = 300.0f;

		public static float playerIdleTriggerSpeed = 5.0f;
		public static float playerFallingTriggerSpeed = 500.0f;
		public static float playerLandingTriggerSpeed = 10.0f;
		public static float playerGroundDrag = 0.005f;
		public static float playerAirDrag = 0.000001f;

		public static Color defaultPlayerTextColor = Color.white;

		public static float weaponGlowFalloff = 1.5f;

		public static float playerGlowFalloff = 1.5f;
		public static float playerBrightness = 1.0f;	

		public static float colissionPointDistance = 2.0f;

		public static float playerMaxJumpSpeed = 1000.0f;
		
		public static long playerReviveDelayInMillis = 3000;

		public static float initialPlayerHealth = 1.0f;
		
		public static float maxPlayerHealth = 2.0f;
	}


	public static class SoundConfig extends Configuration {
		public static boolean musicEnabled = true;
		public static boolean isMuted = false;
		public static boolean soundEnabled = true;
	}
	
	public static class Debug extends Configuration {
		public static boolean showDialogs = false;
		
		@CommandlineParameter("noRender")
		public static boolean doNotRender = false;

		public static boolean showDebugOverlay = false;
		
		public static boolean drawBounds = false;
		
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
		
		// network
		public static boolean networkDebug = false;
		public static boolean showProtocolCommandsOnly = false;
		
		public static boolean factoryDebug = false;

		public static boolean debugGameLogic = false;
	}
	
	public static class Level extends Configuration {
		public static int collisionLayer = 1;
		public static int logicLayer = 3;
	}
	
	public static class GameTypes extends Configuration {
		public static int deathMatch = 0;
		public static int teamDeathMatch = 1;
	}

	public static class NetworkConfig extends Configuration {
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
	
	public static class GuiConfig extends Configuration
	{
		public static final String WAITING_FOR_MASTER_TEXT = "Waiting for Master to Start!";
		public static de.lessvoid.nifty.tools.Color btnSelectedColor = new de.lessvoid.nifty.tools.Color(1,1,1,1);
		public static de.lessvoid.nifty.tools.Color btnNotSelectedColor = new de.lessvoid.nifty.tools.Color(0.8f,0.8f,0.8f,1);
	}
}
