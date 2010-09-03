package de.fhtrier.gdig.engine.entities.gfx;

import org.newdawn.slick.Graphics;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class ImageEntity extends AssetEntity {

	public ImageEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
	}

	@Override
	public void renderImpl(Graphics graphicContext) {
		if (isVisible()) {
			graphicContext.drawImage(Assets().getImage(getAssetId()), 0, 0);
		}
		super.renderImpl(graphicContext);
	}
}
