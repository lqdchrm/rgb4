package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

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
	 * Damage of player dealt to other players 0.0f and 1.0f
	 */
	public float damage; // between 0.0f and 1.0f
	
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
