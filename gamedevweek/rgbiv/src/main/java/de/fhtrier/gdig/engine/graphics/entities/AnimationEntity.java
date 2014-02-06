package de.fhtrier.gdig.engine.graphics.entities;

import org.newdawn.slick.Animation;
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
			graphicContext.drawAnimation(getAnimationAsset(),
					0, 0, this.getColorTint());
		}
		super.renderImpl(graphicContext, frameBuffer);
	}
	
	public Animation getAnimationAsset() {
		return getAssetMgr().getAnimation(getAssetId());
	}
}
