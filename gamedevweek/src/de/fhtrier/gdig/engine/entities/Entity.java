package de.fhtrier.gdig.engine.entities;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.engine.helpers.Identifiable;

public class Entity implements Identifiable
{
	/**
	 * final static index for x component in entity data 
	 */
	public static final int X = 0; // pos
	/**
	 * final static index for y component in entity data
	 */
	public static final int Y = 1;
	/**
	 * center of scaling and rotating
	 */
	public static final int CENTER_X = 2; // center of scaling and rotating
	/**
	 * center of scaling and rotating
	 */
	public static final int CENTER_Y = 3;
	public static final int SCALE_X = 4; // scale
	public static final int SCALE_Y = 5;
	public static final int ROTATION = 6; // rotation

	private int id;

	private TreeMap<Integer, Entity> children;
	private TreeSet<Entity> childrenInOrder;

	private boolean active;
	private boolean visible;
	private boolean recursing;

	private EntityUpdateStrategy updateStrategy;
	
	private Integer order;

	/**
	 * encoding of position, scale and rotation: having all in a single float
	 * array allows for incremental updates<br/>
	 * posX, posY, originX, originY, focusX, focusY, scaleX, scaleY, rotation
	 */
	private float[] data;

	// holds a copy of internal data for network transfer
	private NetworkData networkData;

	public Entity(final int id)
	{

		this.id = id;

		this.children = new TreeMap<Integer, Entity>();
		this.childrenInOrder = new TreeSet<Entity>(new Comparator<Entity>()
		{

			@Override
			public int compare(final Entity o1, final Entity o2)
			{
				final int result = o1.getOrder().compareTo(o2.getOrder());

				if (result != 0)
				{
					return result;
				}

				return o1.getId().compareTo(o2.getId());
			}
		});

		// physics
		this.data = new float[7];
		this.data[Entity.SCALE_X] = 1;
		this.data[Entity.SCALE_Y] = 1;

		// network
		this.networkData = _createNetworkData();
		this.updateStrategy = EntityUpdateStrategy.Local;

		// config
		this.active = false;
		this.visible = false;
		this.recursing = true;

		this.setOrder(id);

	}

	/**
	 * internal factory method to create network data object must be overloaded
	 * for inherited classes !!
	 * 
	 * @return
	 */
	protected NetworkData _createNetworkData()
	{
		return new NetworkData(this.getId());
	}

	public Entity add(final Entity e)
	{
		if (!this.children.containsKey(e.getId()))
		{
			this.children.put(e.getId(), e);
			this.childrenInOrder.add(e);
		} else
		{
			throw new IllegalArgumentException(
					"entity with this id was already added");
		}
		return e;
	}

	/**
	 * Used to set data values coming from the network into entity
	 * 
	 * @param networkData
	 */
	public void applyNetworkData(final NetworkData networkData)
	{
		this.setData(networkData.data);
	}

	public Entity get(final int id)
	{
		return this.children.get(id);
	}

	public Set<Entity> getChildren()
	{
		return Collections.unmodifiableSet(this.childrenInOrder);
	}

	/**
	 * encoding of position, scale and rotation: having all in a single float
	 * array allows for incremental updates<br/>
	 * posX, posY, originX, originY, focusX, focusY, scaleX, scaleY, rotation
	 */
	public float[] getData()
	{
		return this.data;
	}

	public float getData(final int position)
	{
		return this.data[position];
	}

	@Override
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * updates the network data member and returns it
	 * 
	 * @return
	 */
	public NetworkData getNetworkData()
	{

		this.networkData.data = this.getData();
		return this.networkData;
	}

	public Integer getOrder()
	{
		return this.order;
	}


	public void handleInput(final Input input)
	{
		if (this.recursing)
		{
			for (final Entity child : this.childrenInOrder)
			{
				child.handleInput(input);
			}
		}
	}
	
	public boolean handleCollisions() {
		boolean result = false;
		if (this.recursing) {
			for (Entity child : this.childrenInOrder) {
				result |= child.handleCollisions();
			}
		}
		return result;
	}

	public boolean isActive()
	{
		return this.active;
	}

	public boolean isRecursing()
	{
		return this.recursing;
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	protected void postRender(final Graphics graphicContext)
	{
		graphicContext.popTransform();
	}

	protected void preRender(final Graphics graphicContext)
	{
		graphicContext.pushTransform();

		graphicContext.translate(this.getData()[Entity.X],
				this.getData()[Entity.Y]); // move to pos // position

		graphicContext.translate(this.getData()[Entity.CENTER_X],
				this.getData()[Entity.CENTER_Y]); // translate back

		graphicContext.rotate(0, 0, this.getData()[Entity.ROTATION]); // rotate

		graphicContext.scale(this.getData()[Entity.SCALE_X],
				this.getData()[Entity.SCALE_Y]); // zoom
		// from
		// point

		graphicContext.translate(-this.getData()[Entity.CENTER_X],
				-this.getData()[Entity.CENTER_Y]); // set center of rotation
	}

	public void remove(final Entity e)
	{
		if (this.children.containsKey(e.getId()))
		{
			this.childrenInOrder.remove(e);
			this.children.remove(e.getId());
		} else
		{
			throw new IllegalArgumentException(
					"entity with this id doesn't exist");
		}
	}

	public void remove(final int id) {
		this.remove(this.get(id));
	}

	public final void render(final Graphics graphicContext)
	{
		this.preRender(graphicContext);
		this.renderImpl(graphicContext);
		this.postRender(graphicContext);
	}

	protected void renderImpl(final Graphics graphicContext)
	{
		if (this.recursing)
		{
			for (final Entity child : this.childrenInOrder)
			{
				child.render(graphicContext);
			}
		}
	}

	public EntityUpdateStrategy getUpdateStrategy() {
		return updateStrategy;
	}
	
	public void setUpdateStrategy(EntityUpdateStrategy updateStrategy) {
		this.updateStrategy = updateStrategy;
	}
	
	public Entity replace(final Entity e)
	{
		this.childrenInOrder.remove(this.children.get(e.get(this.id)));
		this.children.put(e.getId(), e);
		this.childrenInOrder.add(e);
		return e;
	}

	public void setActive(final boolean active)
	{
		this.active = active;
	}

	public void setData(final float[] data)
	{
		this.data = data;
	}

	public void setOrder(final int order)
	{
		this.order = order;
	}

	public void setRecursing(final boolean recursing)
	{
		this.recursing = recursing;
	}

	public void setVisible(final boolean visible)
	{
		this.visible = visible;
	}

	public void update(final int deltaInMillis)
	{
		if (this.recursing)
		{
			for (final Entity child : this.childrenInOrder)
			{
				child.update(deltaInMillis);
			}
		}
	}
}
