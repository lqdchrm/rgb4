package de.fhtrier.gdig.engine.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.entities.ImageEntity;
import de.fhtrier.gdig.engine.graphics.entities.ParticleEntity;
import de.fhtrier.gdig.engine.graphics.entities.TiledMapEntity;

public class Factory {

	// private AssetMgr assets;
	private TreeMap<Integer, Entity> entities;
	private static int lastId = 0;

	// public Factory(AssetMgr assets) {
	public Factory() {
		// this.assets = assets;
		this.entities = new TreeMap<Integer, Entity>();
	}

	protected int getNewId() {
		int newId = lastId++;

		if (entities.containsKey(newId)) {
			throw new RuntimeException(
					"Fatal Error occurred during id creation: id generated was already used");
		}
		return newId;
	}

	public int createEntity(int order, EntityType type) {
		Entity result = new Entity(getNewId(), type);
		result.setOrder(order);
		add(result);
		return result.getId();
	}

	public ImageEntity createImageEntity(int order, int assetId, AssetMgr assets) {
		ImageEntity result = new ImageEntity(getNewId(), assetId, assets);
		result.setOrder(order);
		add(result);
		return result;
	}

	public ParticleEntity createParticleEntity(int order, int assetId,
			AssetMgr assets) {
		ParticleEntity result = new ParticleEntity(getNewId(), assetId, assets);
		result.setOrder(order);
		add(result);
		return result;
	}

	public AnimationEntity createAnimationEntity(int order, int assetId,
			AssetMgr assets) {
		AnimationEntity result = new AnimationEntity(getNewId(), assetId,
				assets);
		result.setOrder(order);
		add(result);
		return result;
	}

	public TiledMapEntity createTiledMapEntity(int order, int assetId,
			AssetMgr assets) {
		TiledMapEntity result = new TiledMapEntity(getNewId(), assetId, assets);
		result.setOrder(order);
		add(result);
		return result;
	}

	public void removeEntity(int id, boolean recursive) {
		if (recursive) {
			Entity e = getEntity(id);
			if (e != null) {

				List<Integer> childIds = new ArrayList<Integer>();
				for (Entity child : e.getChildren()) {
					childIds.add(child.getId());
				}

				for (Integer childId : childIds) {
					e.remove(childId);
					removeEntity(childId, true);
				}
			}
		}

		entities.remove(id);
	}

	public Entity getEntity(int id) {
		return entities.get(id);
	}

	// public AssetMgr getAssetMgr() {
	// return assets;
	// }

	public int size() {
		return entities.size();
	}

	public Collection<Entity> getEntities() {
		return Collections.unmodifiableCollection(entities.values());
	}

	protected Entity add(Entity e) {
		if (entities.containsKey(e.getId())) {
			throw new IllegalArgumentException(
					"Factory already contains an entity with this id");
		}
		entities.put(e.getId(), e);

		if (Constants.Debug.factoryDebug) {
			Log.debug("Factory: Entity " + e.getId() + " added - Type: "
					+ e.getType().toString());
		}

		return e;
	}

	public static int getLastId() {
		return lastId;
	}

	public static void setLastId(int id) {
		lastId = id;
	}
}