package de.fhtrier.gdig.demos.jumpnrun.identifiers;


public class PlayerState {
	public static final int Idle = 0;
	public static final int RunRight = 1;
	public static final int RunLeft = 2;
	public static final int Jump = 3;

	public String name;
	public float health; // between 0.0f and 1.0f
	public float ammo; // between 0.0f and 1.0f
	public int color; // has to be one of Constants.Color
	public int weaponColor; // has to be one of Constants.Color

}
