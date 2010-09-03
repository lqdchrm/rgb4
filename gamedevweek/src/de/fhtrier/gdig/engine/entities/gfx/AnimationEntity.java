package de.fhtrier.gdig.engine.entities.gfx;

import org.newdawn.slick.Graphics;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class AnimationEntity extends AssetEntity {

	public AnimationEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
		setActive(true);
	}

	@Override
	public void renderImpl(Graphics graphicContext) {
		if (isVisible()) {
			graphicContext.drawAnimation(Assets().getAnimation(getAssetId()), 0, 0);
		}
		super.renderImpl(graphicContext);
	}
}
