package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class DoomsdayDevice extends Entity {

	Random random = new Random();
	int timeSinceLastExplosion = 0;

	int chargeTime;

	AssetMgr assets;
	AssetEntity ddAnimation;

	private GameFactory factory;
	private Level level;

	private DoomsDayDeviceBigExplosion doomesdaydeviceExplosion;

	public DoomsdayDevice(int id, GameFactory factory) throws SlickException {
		super(id, EntityType.DOOMSDAYDEVICE);
		this.factory = factory;
		assets = new AssetMgr();

		// gfx
		assets.storeAnimation(Assets.Level.DoomsdayDevice.DoomsdayDeviceId,
				Assets.Level.DoomsdayDevice.DoomsdayDeviceAnimationPath);
		ddAnimation = factory.createAnimationEntity(EntityOrder.LevelObject,
				Assets.Level.DoomsdayDevice.DoomsdayDeviceId, assets);

		ddAnimation.getData()[X] = -ddAnimation.getAssetMgr()
				.getAnimation(Assets.Level.DoomsdayDevice.DoomsdayDeviceId)
				.getCurrentFrame().getWidth() / 2.0f;

		ddAnimation.getData()[Y] = -ddAnimation.getAssetMgr()
				.getAnimation(Assets.Level.DoomsdayDevice.DoomsdayDeviceId)
				.getCurrentFrame().getHeight();

		ddAnimation.setVisible(true);
		add(ddAnimation);

		// setup
		setVisible(true);

	}

	public void initServer() {
		int doomsDayDeviceID = factory
				.createEntity(EntityType.DOOMSDAYDEVICEEXPLOSION);
		doomesdaydeviceExplosion = (DoomsDayDeviceBigExplosion) factory
				.getEntity(doomsDayDeviceID);
		level.add(doomesdaydeviceExplosion);
		doomesdaydeviceExplosion.getData()[X] = getData(X);
		doomesdaydeviceExplosion.getData()[Y] = getData(Y);

		doomesdaydeviceExplosion.getData()[Entity.Y] -= ddAnimation
				.getAssetMgr()
				.getAnimation(Assets.Level.DoomsdayDevice.DoomsdayDeviceId)
				.getImage(0).getHeight() / 2.0f;

		doomesdaydeviceExplosion.setActive(true);
		doomesdaydeviceExplosion
				.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);

		if (level != null)
			doomesdaydeviceExplosion.setLevel(level);

		resetChargetime();

	}

	public void resetChargetime() {
		timeSinceLastExplosion = 0;
		chargeTime = (random
				.nextInt(Constants.DoomsDayDeviceConfig.maxChargeTime
						- Constants.DoomsDayDeviceConfig.minChargeTime) + Constants.DoomsDayDeviceConfig.minChargeTime) * 1000;
		System.out.println(chargeTime);
	}

	public void setLevel(Level level) {
		this.level = level;
		if (doomesdaydeviceExplosion != null)
			doomesdaydeviceExplosion.setLevel(level);
	}

	@Override
	public void update(int deltaInMillis) {
		super.update(deltaInMillis);
		if (!isActive())
			return;
		timeSinceLastExplosion += deltaInMillis;

		if (timeSinceLastExplosion > chargeTime) {
			SoundManager.playSound(Assets.Sounds.DoomsdayDeviceSoundId, 1f,
					0.5f);
			explode();
		}
	}

	private void explode() {
		doomesdaydeviceExplosion.activate();
		timeSinceLastExplosion = 0;
		resetChargetime();
	}
	
	@Override
	protected void renderImpl(Graphics graphicContext, Image frameBuffer) {
		ddAnimation.getData()[Y] += Constants.DoomsDayDeviceConfig.renderOffset;
		super.renderImpl(graphicContext, frameBuffer);
		ddAnimation.getData()[Y] -= Constants.DoomsDayDeviceConfig.renderOffset;
	}

}
