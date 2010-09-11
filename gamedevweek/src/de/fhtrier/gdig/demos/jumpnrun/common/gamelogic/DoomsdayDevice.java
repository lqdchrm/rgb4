package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.Random;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class DoomsdayDevice extends Entity {

	Random random = new Random();
	int timeSinceLastExplosion = 0;

	int chargeTime;

	AssetMgr assets;
	AssetEntity ddAnimation;

	int minChargeTime = 20;
	int maxChargeTime = 21;
	private GameFactory factory;
	private Level level;

	private DomsDayDeviceBigExplosion doomesdaydeviceExplosion;

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
				.getCurrentFrame().getWidth() >> 1;
		ddAnimation.getData()[Y] = -ddAnimation.getAssetMgr()
				.getAnimation(Assets.Level.DoomsdayDevice.DoomsdayDeviceId)
				.getCurrentFrame().getHeight();
		ddAnimation.setVisible(true);
		add(ddAnimation);

		// setup
		setVisible(true);

	}

	public void initServer() {
		int domsDayDeviceID = factory
				.createEntity(EntityType.DOOMSDAYDEVICEEXPLOSION);
		doomesdaydeviceExplosion = (DomsDayDeviceBigExplosion) factory
				.getEntity(domsDayDeviceID);
		level.add(doomesdaydeviceExplosion);
		doomesdaydeviceExplosion.getData()[X] = getData(X);
		doomesdaydeviceExplosion.getData()[Y] = getData(Y);

		doomesdaydeviceExplosion.getData()[Entity.X] += ddAnimation
				.getAssetMgr()
				.getAnimation(Assets.Level.DoomsdayDevice.DoomsdayDeviceId)
				.getImage(0).getWidth() / 2.0f;
		doomesdaydeviceExplosion.getData()[Entity.Y] += ddAnimation
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
		chargeTime = (random.nextInt(maxChargeTime - minChargeTime) + minChargeTime) * 1000;
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
		if (timeSinceLastExplosion > chargeTime)
			explode();
	}

	private void explode() {
		System.out.println("EXPLOAD");
		int r = random.nextInt(3);
		switch (r) {
		case 0:
			doomesdaydeviceExplosion.damageColor = StateColor.RED;
			break;
		case 1:
			doomesdaydeviceExplosion.damageColor = StateColor.BLUE;
			break;
		case 2:
			doomesdaydeviceExplosion.damageColor = StateColor.GREEN;
			break;

		default:
			break;
		}
		doomesdaydeviceExplosion.activate();
		resetChargetime();
	}

}
