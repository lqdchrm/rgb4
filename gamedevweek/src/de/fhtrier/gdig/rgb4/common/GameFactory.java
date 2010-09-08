package de.fhtrier.gdig.rgb4.common;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.Player;
import de.fhtrier.gdig.rgb4.identifiers.EntityType;

public class GameFactory extends Factory {

	public GameFactory(AssetMgr assets) {
		super(assets);
	}

	public int createEntity(EntityType type) {
		return createEntityById(-1, type);
	}

	public int createEntityById(int id, EntityType type) {
		if (id == -1) {
			id = getNewId();
		} else {
			if (id >= Factory.getLastId()) {
				setLastId(id + 1);
			}
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
			case GEM:
				Gem newGem = new Gem(id, this);
				add(newGem);
				return id;
			case BULLET:
				Bullet newBullet = new Bullet(id, this);
				add(newBullet);
				return id;
			}
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
}
