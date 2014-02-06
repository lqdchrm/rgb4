package de.fhtrier.gdig.engine.graphics.entities;

import org.newdawn.slick.Color;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class AssetEntity extends Entity {

	private int assetId;
	private AssetMgr assets;
	private Color colorTint = Color.white;

	public AssetEntity(int id, int assetId, AssetMgr assets) {
		super(id, EntityType.ASSET);
		this.assetId = assetId;
		this.assets = assets;
		this.setVisible(false);
	}

	public AssetMgr getAssetMgr() {
		return this.assets;
	}

	public int getAssetId() {
		return assetId;
	}

	public Color getColorTint() {
		return colorTint;
	}

	public void setColorTint(Color colorTint) {
		this.colorTint = colorTint;
	}
}
