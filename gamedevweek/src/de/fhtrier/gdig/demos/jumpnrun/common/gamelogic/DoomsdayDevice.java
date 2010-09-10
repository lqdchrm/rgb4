package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;

public class DoomsdayDevice extends Entity {

	Random random = new Random();
	int timeSinceLastExplosion = 0;

	int chargeTime;

	AssetEntity asset;

	int minChargeTime = 20;
	int maxChargeTime = 21;
	private GameFactory factory;
	private Level level;

	private DomsDayDeviceBigExplosion doomesdaydeviceExplosion;

	public DoomsdayDevice(int id, GameFactory factory) {
		super(id, EntityType.DOOMSDAYDEVICE);
		this.factory = factory;
		// asset = new AssetEntity(iddd, Assets, assets)

	}

	public void initServer() {
		int domsDayDeviceID = factory
				.createEntity(EntityType.DOOMSDAYDEVICEEXPLOSION);
		doomesdaydeviceExplosion = (DomsDayDeviceBigExplosion) factory
				.getEntity(domsDayDeviceID);
		level.add(doomesdaydeviceExplosion);
		doomesdaydeviceExplosion.getData()[X] = getData(X);
		doomesdaydeviceExplosion.getData()[Y] = getData(Y);
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

	@Override
	protected void renderImpl(Graphics graphicContext, Image frameBuffer) {
		// TODO Auto-generated method stub
		super.renderImpl(graphicContext, frameBuffer);
		graphicContext.setColor(Color.orange);
		graphicContext.fillOval(-10, -10, 10, 10);
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
		doomesdaydeviceExplosion.activate();
		resetChargetime();
	}

}
