package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.GameFactory;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.DoomsdayDeviceExplosionData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.helpers.Configuration;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class DomsDayDeviceBigExplosion extends Entity {

	private Options options;
	private boolean isDDDActive = false;

	private Level level;

	private float outerRadius = 0;

	private Set<Player> hitPlayer = new HashSet<Player>();

	public int damageColor = StateColor.RED;
	private int timeSinceActivation = 0;

	AssetMgr asset = new AssetMgr();
	private Random random;

	public void setLevel(Level level) {
		this.level = level;

	}

	public DomsDayDeviceBigExplosion(int id, GameFactory factory)
			throws SlickException {
		super(id, EntityType.DOOMSDAYDEVICEEXPLOSION);

		random = new Random();

		asset.storeImage(Assets.DoomsdayBigExplosionImageId,
				Assets.DoomsdayBigExplosionImagePath);

		options = new Options();
		options.showEditor("DOOM");
	}

	public boolean activate() {
		if (isDDDActive) {
			return false;
		}

		isDDDActive = true;

		outerRadius = 0;

		timeSinceActivation = 0;
		hitPlayer.clear();

		int r = random.nextInt(3);

		switch (r) {
		case 0:
			damageColor = StateColor.RED;
			break;
		case 1:
			damageColor = StateColor.BLUE;
			break;
		case 2:
			damageColor = StateColor.GREEN;
			break;

		default:
			break;
		}
		setVisible(true);
		return true;
	}

	@Override
	protected void renderImpl(Graphics graphicContext, Image frameBuffer) {
		super.renderImpl(graphicContext, frameBuffer);

		if (!isDDDActive) {
			return;
		}

		Color constIntoColor = StateColor.constIntoColor(damageColor);
		graphicContext.drawImage(asset
				.getImage(Assets.DoomsdayBigExplosionImageId), -outerRadius,
				-outerRadius, outerRadius, outerRadius, 0, 0,
				asset.getImage(Assets.DoomsdayBigExplosionImageId).getWidth(),
				asset.getImage(Assets.DoomsdayBigExplosionImageId).getHeight(),
				new Color(constIntoColor.r, constIntoColor.g, constIntoColor.b,
						0.5f));
		
		// TODO should not be necessary
		// graphicContext.flush();
	}

	@Override
	public void update(int deltaInMillis) {
		super.update(deltaInMillis);

		float secs = deltaInMillis / 1000.0f;

		if (!isActive() || level == null || !isDDDActive) {
			return;
		}

		timeSinceActivation += deltaInMillis;

		if (options.timeUntilDetonation > timeSinceActivation) {
			return;
		}

		this.outerRadius += options.speed * secs;


		// determine which entities to check
		Set<Player> players = new HashSet<Player>();
		Queue<Entity> queue = new LinkedList<Entity>();

		queue.addAll(level.getChildren());
		while (!queue.isEmpty()) {
			Entity entity = queue.poll();
			queue.addAll(entity.getChildren());
			if (entity instanceof Player)
				players.add((Player) entity);
		}
		
		// check each player for hit and if so, mark it
		for (Player player : players) {
			if (hitPlayer.contains(player)) {
				continue;
			}
			
			float playerPosX = player.getTransformedBounds().getCenterX();
			float playerPosY = player.getTransformedBounds().getCenterY();
			
			float playerdistance = (playerPosX - getData()[X])
							* (playerPosX - getData()[X])
							+ (playerPosY - getData()[Y])
							* (playerPosY - getData()[Y]);

			if (playerdistance >= outerRadius-options.hitSizeSqrd && playerdistance <= outerRadius) {
				if (!player.doDamage(damageColor, options.damage, null))
					hitPlayer.add(player);

			}
		}

		// Calculate when to wear off
		float left = -getData()[X];
		float right = level.getWidth() - getData()[X];
		float top = -getData()[Y];
		float bottom = level.getWidth() - getData()[Y];
		
		float innerRadiusSqrd = (outerRadius-options.size) * (outerRadius-options.size);
		float maxLevelDistX = Math.max(left, right);
		float maxLevelDistY = Math.max(top, bottom);
		float maxLevelDist = maxLevelDistX * maxLevelDistX + maxLevelDistY * maxLevelDistY;
		
		if (innerRadiusSqrd > maxLevelDist) {
			isDDDActive = false;
			setVisible(false);
		}
	}

	@Override
	public NetworkData getNetworkData() {
		DoomsdayDeviceExplosionData result = (DoomsdayDeviceExplosionData) super
				.getNetworkData();
		result.damageColor = damageColor;
		result.isActive = isDDDActive;
		result.outerRadius = outerRadius;
		
		return result;
	}

	@Override
	public void applyNetworkData(NetworkData networkData) {
		super.applyNetworkData(networkData);
		if (networkData instanceof DoomsdayDeviceExplosionData) {
			DoomsdayDeviceExplosionData result = (DoomsdayDeviceExplosionData) networkData;

			damageColor = result.damageColor;
			isDDDActive = result.isActive;
			outerRadius = result.outerRadius;
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
		int timeUntilDetonation = 1230;
		float size = 400f;
		float speed = 400f;
		float hitSizeSqrd = 250f;
		float damage = 0.5f;
	}
}
