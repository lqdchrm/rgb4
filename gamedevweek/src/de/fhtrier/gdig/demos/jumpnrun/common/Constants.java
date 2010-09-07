package de.fhtrier.gdig.demos.jumpnrun.common;

import java.net.Proxy.Type;

import org.newdawn.slick.Color;

import de.fhtrier.gdig.engine.support.Configuration;

public class Constants
{

	public static final float EPSILON = 0.0001f;

	public static class GamePlayConstants extends Configuration
	{

		public static float gravity = 2000.0f;// 981.0f==Erdbeschleunigung

		@ShowAsSlider(maxValue = 10000, minValue = 10)
		public static float shotSpeed = 1000.0f;
		public static float shotCooldown = 100.0f;

		public static float playerWalkVel = 4000.0f;
		public static float playerJumpVel = 3500.0f;
		public static float playerMaxSpeed = 850.0f;

		public static Type en;
	}

	public static class Debug extends Configuration
	{
		public static boolean drawBounds = true;
		public static Color boundColor = Color.green;
		public static boolean showCollisions = false;
	}
}
