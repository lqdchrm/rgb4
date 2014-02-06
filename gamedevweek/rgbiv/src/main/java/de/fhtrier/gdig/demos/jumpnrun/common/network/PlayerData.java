package de.fhtrier.gdig.demos.jumpnrun.common.network;


public class PlayerData extends NetworkData {


	private static final long serialVersionUID = -3776997774121122797L;

	private int animationEntityId;
	public int color;
	public int weaponColor;

	public PlayerData(int id) {
		super(id);
	}


	public int getAnimationEntityId() {
		return animationEntityId;
	}
	
	public void setAnimationEntityId(int state) {
		this.animationEntityId = state;
	}
	
	public int getColor() {
		return color;
	}

	public int getWeaponColor() {
		return weaponColor;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setWeaponColor(int weaponColor) {
		this.weaponColor = weaponColor;
	}
	
	@Override
	public String toString() {
		return super.toString() + " | Id: " + animationEntityId + " | Color: " + color + " | WeaponColor: " + weaponColor;
	}

}
