package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Bullet;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.DoomsDayDeviceBigExplosion;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.DoomsdayDevice;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Level;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Rocket;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Teleporter;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.management.Factory;

public class GameFactory extends Factory {

	public int createEntity(EntityType type) {
		return createEntityById(-1, type);
	}

	public int createEntityById(int id, EntityType type) {
		if (id == -1) {
			id = getNewId();
		} else {
//			if (id >= Factory.getLastId()) {
				setLastId(id + 1);
//			}
		}

		try {
			switch (type) {
			case PLAYER:
				Player newPlayer = new Player(id, this);
				add(newPlayer);
				return id;
			case LEVEL:
				Level newLevel = new Level(id, this);
				add(newLevel);
				return id;
			case BULLET:
				Bullet newBullet = new Bullet(id, this);
				add(newBullet);
				return id;
			case DOOMSDAYDEVICEEXPLOSION:
				DoomsDayDeviceBigExplosion newDoomsdaydeviceExplosion = new DoomsDayDeviceBigExplosion(
						id, this);
				add(newDoomsdaydeviceExplosion);
				return id;
			case DOOMSDAYDEVICE:
				DoomsdayDevice newDoomsdaydevice = new DoomsdayDevice(id, this);
				add(newDoomsdaydevice);
				return id;
			case TELEPORTER:
				Teleporter teleporter = new Teleporter(id, this);
				add(teleporter);
				return id;
			case ROCKET:
				Rocket newRocket = new Rocket(id, this);
				add(newRocket);
				return id;
			}
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
}
