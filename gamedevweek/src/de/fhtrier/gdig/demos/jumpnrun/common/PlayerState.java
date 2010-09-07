package de.fhtrier.gdig.demos.jumpnrun.common;

public class PlayerState {
	public String name;
	public float health; // between 0.0f and 1.0f
	public float ammo; // between 0.0f and 1.0f
	public int color; // has to be one of Constants.Color
	public int weaponColor; // has to be one of Constants.Color
	public int shootDirection; // has to be RunLeft or RunRight
}
