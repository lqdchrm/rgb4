package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.DoomsdayDeviceExplosionData;
import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.helpers.Configuration;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class DomsDayDeviceBigExplosion extends Entity {

	private Options options;
	private boolean isActive = false;

	private Level level;

	private float minRadius = 0;

	private float maxRadius = 0;

	private Set<Player> hitedPlayer = new HashSet<Player>();

	public int damageColor = StateColor.RED;
	private int timeScincActivation = 0;

	AssetMgr asset = new AssetMgr();

	public void setLevel(Level level) {
		this.level = level;

	}

	public DomsDayDeviceBigExplosion(int id, GameFactory factory)
			throws IOException {
		super(id, EntityType.DOOMSDAYDEVICEEXPLOSION);

		try {
			asset.storeImage(Assets.DoomsdayBigExplosionImageId,
					Assets.DoomsdayBigExplosionImagePath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		options = new Options();
		options.showEditor("DOOM");
	}

	public boolean activate() {
		if (isActive)
			return false;
		isActive = true;
		minRadius = 0;
		maxRadius = 0;
		timeScincActivation = 0;
		hitedPlayer.clear();
		setVisible(true);
		return true;
	}

	@Override
	protected void renderImpl(Graphics graphicContext, Image frameBuffer) {
		super.renderImpl(graphicContext, frameBuffer);

		// Hack Position of Doomsdaydevice

		if (!isActive)
			return;
		Color constIntoColor = StateColor.constIntoColor(damageColor);
		graphicContext.drawImage(asset
				.getImage(Assets.DoomsdayBigExplosionImageId), -maxRadius,
				-maxRadius, maxRadius, maxRadius, 0, 0,
				asset.getImage(Assets.DoomsdayBigExplosionImageId).getWidth(),
				asset.getImage(Assets.DoomsdayBigExplosionImageId).getHeight(),
				new Color(constIntoColor.r, constIntoColor.g, constIntoColor.b,
						0.5f));
		graphicContext.flush();
	}

	@Override
	public void update(int deltaInMillis) {
		super.update(deltaInMillis);

		float secs = deltaInMillis / 1000.0f;

		if (!isActive())
			return;

		if (level == null)
			return;

		if (!isActive)
			return;

		timeScincActivation += deltaInMillis;
		if (options.timeToDatonate > timeScincActivation)
			return;
		this.maxRadius += options.speed * secs;
		this.minRadius = Math.max(maxRadius - options.size, 0);

		float maxDistance = maxRadius;
		// achtung der Kollisions bereich ist 10 nicht 100 pixel breit. (10*10)
		float minDistance = maxDistance - 100f;

		Set<Entity> children = new HashSet<Entity>(level.getChildren());

		Set<Player> players = new HashSet<Player>();

		Queue<Entity> queue = new LinkedList<Entity>();

		queue.addAll(children);
		while (!queue.isEmpty()) {
			Entity entity = queue.poll();
			queue.addAll(entity.getChildren());
			if (entity instanceof Player)
				players.add((Player) entity);
		}
		for (Player player : players) {
			if (hitedPlayer.contains(player))
				continue;
			float playerPosX = player.getTransformedBounds().getCenterX();
			float playerPosY = player.getTransformedBounds().getCenterY();
			float playerdistance = (float) Math
					.sqrt((playerPosX - getData()[X])
							* (playerPosX - getData()[X])
							+ (playerPosY - getData()[Y])
							* (playerPosY - getData()[Y]));
			// System.out.println(minDistance + " " + maxDistance);
			if (playerdistance >= minDistance && playerdistance <= maxDistance) {
				if (!player.doDamage(damageColor, 0.5f, null))
					hitedPlayer.add(player);

			}
		}

		float left = -getData()[X];
		float right = level.getWidth() - getData()[X];
		float top = -getData()[Y];
		float bottom = level.getWidth() - getData()[Y];

		float leftTop = left * left + top * top;
		float leftBottom = left * left + bottom * bottom;
		float rightTop = right * right + top * top;
		float rightBottom = right * right + bottom * bottom;
		float distanceToLastVisible = minRadius * minRadius;

		if (leftTop < distanceToLastVisible
				&& leftBottom < distanceToLastVisible
				&& rightBottom < distanceToLastVisible
				&& rightTop < distanceToLastVisible) {
			isActive = false;
			setVisible(false);
		}
	}

	@Override
	public NetworkData getNetworkData() {
		DoomsdayDeviceExplosionData result = (DoomsdayDeviceExplosionData) super
				.getNetworkData();
		result.damageColor = damageColor;
		result.data = getData();
		result.isActive = isActive;
		result.maxRadius = maxRadius;
		return super.getNetworkData();
	}

	@Override
	public void applyNetworkData(NetworkData networkData) {
		super.applyNetworkData(networkData);
		if (networkData instanceof DoomsdayDeviceExplosionData) {
			DoomsdayDeviceExplosionData result = (DoomsdayDeviceExplosionData) networkData;

			damageColor = result.damageColor;
			isActive = result.isActive;
			maxRadius = result.maxRadius;
		} else {
			throw new IllegalArgumentException("Wrong package type received");
		}
	}

	@Override
	protected NetworkData _createNetworkData() {
		return new DoomsdayDeviceExplosionData(getId());
	}

	private static class Options extends Configuration {
		/**
		 * Time in Millseconds till detonation after Activation.
		 */
		int timeToDatonate = 1230;
		float size = 400f;
		float speed = 400f;

	}
}
