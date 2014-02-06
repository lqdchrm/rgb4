package de.fhtrier.gdig.engine.graphics.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class ImageEntity extends AssetEntity {

	int srcx;
	int srcy;
	int srcwidth;
	int srcheight;
	
	public ImageEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
		srcx = 0;
		srcy = 0;
		srcwidth = this.getImageAsset().getWidth();
		srcheight = this.getImageAsset().getHeight();
	}
	
	public ImageEntity(int id, int assetId, AssetMgr assets, int x, int y, int width, int height) {
		super(id, assetId, assets);
		srcx = x;
		srcy = y;
		srcwidth = width;
		srcheight = height;
	}

	@Override
	public void renderImpl(Graphics graphicContext, Image frameBuffer) {
		if (isVisible()) {
			graphicContext.drawImage(getImageAsset(), 0, 0, 
					srcx, srcy, srcx+srcwidth, srcy+srcheight, this.getColorTint());
		}
		super.renderImpl(graphicContext, frameBuffer);
	}
	
	public Image getImageAsset() {
		return getAssetMgr().getImage(getAssetId());
	}
}
