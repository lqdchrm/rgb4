package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

import java.io.Serializable;

import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendPlayerCondition;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class PlayerCondition implements Serializable {

	private static final long serialVersionUID = 7844617905759951119L;

	private int playerId;
	private String name;
	private int teamId;
	private float health;

	/**
	 * Current Ammunition between 0.0f and 1.0f 1.0f indicating max. Ammunition
	 */
	private float ammo; // between 0.0f and 1.0f
	
	/**
	 * Damage of player dealt to other players 0.0f and 1.0f
	 */
	private float damage; // between 0.0f and 1.0f
	private int kills;
	private int deaths;

	
	public PlayerCondition(int playerId, String name, int teamId, float health, float ammo, float damage) {
		this.playerId = playerId;
		this.name = name;
		this.teamId = teamId;
		this.health = health;
		this.ammo = ammo;
		this.damage = damage;
	}

	public void setName(String name) {
		this.name = name;
		broadCast();
	}


	public String getName() {
		return name;
	}


	public void setTeamId(int teamId) {
		this.teamId = teamId;
		broadCast();
	}


	public int getTeamId() {
		return teamId;
	}


	public void setHealth(float health) {
		this.health = health;
		broadCast();
	}


	public float getHealth() {
		return health;
	}


	public void setAmmo(float ammo) {
		this.ammo = ammo;
		broadCast();
	}


	public float getAmmo() {
		return ammo;
	}


	public void setDamage(float damage) {
		this.damage = damage;
		broadCast();
	}

	/** damage induced by bullets this player fires */
	public float getDamage() {
		return damage;
	}


	public void setKills(int kills) {
		this.kills = kills;
		broadCast();
	}


	public int getKills() {
		return kills;
	}
	
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
		broadCast();
	}
	
	
	public int getDeaths() {
		return deaths;
	}
	
	private void broadCast() {
		NetworkComponent.getInstance().sendCommand(new SendPlayerCondition(playerId, this));
	}
}
