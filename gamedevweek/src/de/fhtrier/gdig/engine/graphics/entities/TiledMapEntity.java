package de.fhtrier.gdig.engine.graphics.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class TiledMapEntity extends AssetEntity {
	public TiledMapEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
	}

	@Override
	public void renderImpl(Graphics graphicContext, Image frameBuffer) {
		if (isVisible()) {
			Assets().getTiledMap(getAssetId()).render(0, 0, 0);
			Assets().getTiledMap(getAssetId()).render(0, 0, 1);
		}
		super.renderImpl(graphicContext, frameBuffer);
	}
}
