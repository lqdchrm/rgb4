package de.fhtrier.gdig.engine.graphics.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class TiledMapEntity extends AssetEntity {
	public TiledMapEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
	}

	@Override
	public void renderImpl(Graphics graphicContext, Image frameBuffer) {
		if (isVisible()) {
			
			TiledMap tm = getTiledMapAsset();
			
			// render layer 0 and 1
			tm.render(0, 0, 0);
			tm.render(0, 0, 1);
		}
		super.renderImpl(graphicContext, frameBuffer);
	}
	
	public TiledMap getTiledMapAsset() {
		return getAssetMgr().getTiledMap(getAssetId());
	}
}
