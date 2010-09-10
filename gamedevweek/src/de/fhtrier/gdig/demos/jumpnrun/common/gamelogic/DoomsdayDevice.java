package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.Random;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoCreateEntity;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.gamelogic.EntityUpdateStrategy;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class DoomsdayDevice extends Entity
{

	Random random = new Random();
	int timeSinceLastExplosion = 0;

	int chargeTime;

	AssetEntity asset;

	int minChargeTime;
	int maxChargeTime;
	private GameFactory factory;

	public DoomsdayDevice(int id, GameFactory factory)
	{
		super(id, EntityType.DOOMSDAYDEVICE);
		this.factory = factory;
		// asset = new AssetEntity(iddd, Assets, assets)

		resetChargetime();
	}

	public void resetChargetime()
	{
		timeSinceLastExplosion = 0;
		chargeTime = (random.nextInt(maxChargeTime - minChargeTime) + minChargeTime) * 1000;
	}

	@Override
	public void update(int deltaInMillis)
	{
		super.update(deltaInMillis);
		if (!isActive())
			return;
		timeSinceLastExplosion += deltaInMillis;
		if (timeSinceLastExplosion > chargeTime)
			explode();
	}

	private void explode()
	{
		int domsDayDeviceID = factory
				.createEntity(EntityType.DOOMSDAYDEVICEEXPLOSION);
		Entity doomesdaydevice = factory.getEntity(domsDayDeviceID);
		add(doomesdaydevice);
		doomesdaydevice.setActive(true);
		doomesdaydevice.setUpdateStrategy(EntityUpdateStrategy.ServerToClient);
		doomesdaydevice.getData()[X] = this.getData()[X];
		doomesdaydevice.getData()[Y] = this.getData()[Y];

		DoCreateEntity command = new DoCreateEntity(domsDayDeviceID,
				EntityType.DOOMSDAYDEVICEEXPLOSION);
		NetworkComponent.getInstance().sendCommand(command);
		resetChargetime();
	}

}
