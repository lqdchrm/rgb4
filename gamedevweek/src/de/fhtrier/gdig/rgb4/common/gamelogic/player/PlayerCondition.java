package de.fhtrier.gdig.rgb4.common.gamelogic.player;

public class PlayerCondition
{
	/**
	 * 
	 */
	public String name;
	
	/**
	 * The Player Health-Level between 0.0f and 1.0f
	 * 1.0f representing 100% health
	 */
	public float health;
	
	/**
	 * Current Ammunition between 0.0f and 1.0f
	 * 1.0f indicating max. Ammunition
	 */
	public float ammo; // between 0.0f and 1.0f
	
	/**
	 * has to be one of Constants.Color
	 */
	public int color;
	
	/**
	 * has to be one of Constants.Color
	 */
	public int weaponColor;
	
	/**
	 * has to be RunLeft or RunRight
	 */
	public int shootDirection;
}
