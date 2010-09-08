package de.fhtrier.gdig.engine.entities.gfx;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.fhtrier.gdig.engine.management.AssetMgr;

public class AnimationEntity extends AssetEntity {

	public AnimationEntity(int id, int assetId, AssetMgr assets) {
		super(id, assetId, assets);
	}

	@Override
	public void renderImpl(Graphics graphicContext, Image frameBuffer) {
		if (isVisible()) {
			graphicContext.drawAnimation(Assets().getAnimation(getAssetId()),
					0, 0);
		}
		super.renderImpl(graphicContext, frameBuffer);
	}
}
