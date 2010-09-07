package de.fhtrier.gdig.engine.entities.gfx;

import org.newdawn.slick.Graphics;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class TiledMapEntity extends AssetEntity {
	public TiledMapEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
	}

	@Override
	public void renderImpl(Graphics graphicContext) {
		if (isVisible()) {
			Assets().getTiledMap(getAssetId()).render(0, 0, 0);
		}
		super.renderImpl(graphicContext);
	}
}
