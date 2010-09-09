package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryDoomsdayDeviceExplosionRadius;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.StateColor;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.helpers.Configuration;
import de.fhtrier.gdig.engine.management.Factory;

public class DomsDayDeviceBigExplosion extends Entity
{

	private Factory factory;
	private Options options;

	private float minRadius = 0;
	private float maxRadius = 0;

	private Set<Player> hitedPlayer = new HashSet<Player>();

	private Level level;
	private int damageColor = StateColor.RED;

	private int timeScincActivation = 0;
	private int timeScinclastChangeColor = 0;

	public DomsDayDeviceBigExplosion(int id, Factory factory)
	{
		super(id, EntityType.DOMSDAYDEVICE);
		this.factory = factory;

		try
		{
			factory.getAssetMgr()
					.storeImage(29729387, "sprites/items/doom.png");
			// .storeImage(29729387, "sprites/items/doom.png");
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		options = new Options();
		options.showEditor("DOOM");
	}

	public void setLevel(Level level)
	{
		this.level = level;
		getData()[X] = level.getWidth() >> 1;
		getData()[Y] = level.getHeight() >> 1;
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

		graphicContext.setColor(StateColor.constIntoColor(damageColor));
		graphicContext.fillRect(0, 0, 20, 20);

		if (!options.isActive)
			return;
		Color constIntoColor = StateColor.constIntoColor(damageColor);
		graphicContext.drawImage(factory.getAssetMgr().getImage(29729387),
				-maxRadius, -maxRadius, maxRadius, maxRadius, 0, 0, 300, 300,
				new Color(constIntoColor.r, constIntoColor.g, constIntoColor.b,
						0.5f));
		// float lineWidth = graphicContext.getLineWidth();
		// graphicContext.setLineWidth(1000);
		// graphicContext.drawOval(-maxRadius, -maxRadius, maxRadius +
		// maxRadius,
		// maxRadius + maxRadius);
		graphicContext.flush();
		// graphicContext.setLineWidth(lineWidth);

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
		float minDistance = minRadius * minRadius;

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
			float[] playerPos = player.getData();
			float playerdistance = (playerPos[X] - getData()[X])
					* (playerPos[X] - getData()[X])
					+ (playerPos[Y] - getData()[Y])
					* (playerPos[Y] - getData()[Y]);
			if (playerdistance > minDistance && playerdistance < maxDistance)
			{
				System.out.println("DomsDayDeviceBigExplosion.update()");
				if (!player.doDamage(damageColor, 2.5f))
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
		if (minX < 0 && maxX > levelWidth && minY < 0 && maxY > levelHeight)
		{
			options.isActive = false;
		}
	}

	@Override
	public NetworkData getNetworkData()
	{
		QueryDoomsdayDeviceExplosionRadius result = (QueryDoomsdayDeviceExplosionRadius) super
				.getNetworkData();
		result.damageColor = damageColor;
		result.data = getData();
		result.isActive = options.isActive;
		result.maxRadius = maxRadius;
		result.minRadius = minRadius;
		return super.getNetworkData();
	}

	@Override
	public void applyNetworkData(NetworkData networkData)
	{
		super.applyNetworkData(networkData);
		QueryDoomsdayDeviceExplosionRadius result = (QueryDoomsdayDeviceExplosionRadius) networkData;
		damageColor = result.damageColor;
		options.isActive = result.isActive;
		maxRadius = result.maxRadius;
		minRadius = result.minRadius;
	}

	@Override
	protected NetworkData _createNetworkData()
	{
		return new QueryDoomsdayDeviceExplosionRadius(getId());
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
