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

public class DomsDayDeviceBigExplosion extends Entity
{

	private GameFactory factory;
	private Options options;

	private float minRadius = 0;

	private float maxRadius = 0;

	private Set<Player> hitedPlayer = new HashSet<Player>();

	private int damageColor = StateColor.RED;

	private int timeScincActivation = 0;
	private int timeScinclastChangeColor = 0;

	public DomsDayDeviceBigExplosion(int id, GameFactory factory)
			throws IOException
	{
		super(id, EntityType.DOOMSDAYDEVICEEXPLOSION);
		this.factory = factory;

		try
		{
			level.getAssets().storeImage(Assets.DoomsdayBigExplosionImageId,
					Assets.DoomsdayBigExplosionImagePath);
		} catch (SlickException e)
		{
			throw new IOException("Failed to Load DommsdayDeviceImage ", e);
		}

		options = new Options();
		options.showEditor("DOOM");
	}

	public boolean activate()
	{
		if (options.isActive)
			return false;
		options.isActive = true;
		minRadius = 0;
		maxRadius = 0;
		timeScincActivation = 0;
		return true;
	}

	@Override
	protected void renderImpl(Graphics graphicContext, Image frameBuffer)
	{
		super.renderImpl(graphicContext, frameBuffer);

		// Hack Position of Doomsdaydevice
		graphicContext.setColor(StateColor.constIntoColor(damageColor));
		graphicContext.fillRect(-10, -10, 20, 20);

		if (!options.isActive)
			return;
		Color constIntoColor = StateColor.constIntoColor(damageColor);
		graphicContext.drawImage(
				level.getAssets().getImage(Assets.DoomsdayBigExplosionImageId),
				-maxRadius, -maxRadius, maxRadius, maxRadius, 0, 0, 300, 300,
				new Color(constIntoColor.r, constIntoColor.g, constIntoColor.b,
						0.5f));
		graphicContext.flush();
	}

	@Override
	public void update(int deltaInMillis)
	{
		super.update(deltaInMillis);

		float secs = deltaInMillis / 1000.0f;

		if (!isActive())
			return;
		if (timeScinclastChangeColor < options.timeToChangeColor)
		{
			timeScinclastChangeColor += deltaInMillis;
		} else
		{
			timeScinclastChangeColor = 0;
			damageColor = damageColor << 1;
			if (damageColor > StateColor.BLUE)
				damageColor = StateColor.RED;
		}

		if (level == null)
			return;

		if (!options.isActive)
			return;

		if (timeScincActivation < options.timeToDatonate && false)
		{
			timeScincActivation += deltaInMillis;
			return;
		}

		this.maxRadius += options.speed * secs;
		this.minRadius = Math.max(maxRadius - options.size, 0);

		float maxDistance = maxRadius * maxRadius;
		// achtung der Kollisions bereich ist 10 nicht 100 pixel breit. (10*10)
		float minDistance = maxDistance - 100f;

		Set<Entity> children = new HashSet<Entity>(level.getChildren());

		Set<Player> players = new HashSet<Player>();

		Queue<Entity> queue = new LinkedList<Entity>();

		queue.addAll(children);
		while (!queue.isEmpty())
		{
			Entity entity = queue.poll();
			queue.addAll(entity.getChildren());
			if (entity instanceof Player)
				players.add((Player) entity);
		}

		for (Player player : players)
		{
			if (hitedPlayer.contains(player))
				continue;
			float playerPosX = player.getBounds().getCenterX();
			float playerPosY = player.getBounds().getCenterY();
			float playerdistance = (playerPosX - getData()[X])
					* (playerPosX - getData()[X]) + (playerPosY - getData()[Y])
					* (playerPosY - getData()[Y]);
			if (playerdistance >= minDistance && playerdistance <= maxDistance)
			{
				if (!player.doDamage(damageColor, 0.5f, null))
					hitedPlayer.add(player);
			}
		}

		float max = Math.max(Math.abs(minRadius + getData()[X]),
				Math.abs(minRadius - getData()[X]));
		int minX = (int) (getData()[X] - minRadius);
		int maxX = (int) (getData()[X] + minRadius);
		int minY = (int) (getData()[Y] - minRadius);
		int maxY = (int) (getData()[Y] + minRadius);
		int levelWidth = level.getWidth();
		int levelHeight = level.getHeight();

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
				&& rightTop < distanceToLastVisible)
		{
			options.isActive = false;
		}
	}

	@Override
	public NetworkData getNetworkData()
	{
		DoomsdayDeviceExplosionData result = (DoomsdayDeviceExplosionData) super
				.getNetworkData();
		result.damageColor = damageColor;
		result.data = getData();
		result.isActive = options.isActive;
		result.maxRadius = maxRadius;
		return super.getNetworkData();
	}

	@Override
	public void applyNetworkData(NetworkData networkData)
	{
		super.applyNetworkData(networkData);
		DoomsdayDeviceExplosionData result = (DoomsdayDeviceExplosionData) networkData;
		damageColor = result.damageColor;
		options.isActive = result.isActive;
		maxRadius = result.maxRadius;
	}

	@Override
	protected NetworkData _createNetworkData()
	{
		return new DoomsdayDeviceExplosionData(getId());
	}

	private class Options extends Configuration
	{
		/**
		 * Time in Millseconds till detonation after Activation.
		 */
		int timeToDatonate = 3000;
		int timeToChangeColor = 1000;
		float size = 40f;
		float speed = 20f;
		private boolean isActive = false;

	}
}
