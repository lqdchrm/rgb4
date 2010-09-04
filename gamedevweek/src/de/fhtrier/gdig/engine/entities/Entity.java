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

public class Entity implements Identifiable {

	public static final int X = 0; // pos
	public static final int Y = 1;
	public static final int CENTER_X = 2; // center of scaling and rotating
	public static final int CENTER_Y = 3;
	public static final int SCALE_X = 4; // scale
	public static final int SCALE_Y = 5;
	public static final int ROTATION = 6; // rotation

	private int id;

	private TreeMap<Integer, Entity> children;
	private TreeSet<Entity> childrenInOrder;

	private boolean active;
	private boolean visible;
	private boolean transmitting;
	private boolean recursing;

	private Integer order;

	// encoding of position, scale and rotation:
	// having all in a single float array allows
	// for incremental updates
	// posX, posY, originX, originY, focusX, focusY, scaleX, scaleY, rotation
	private float[] data;
	
	// holds a copy of internal data for network transfer
	private NetworkData networkData;

	public Entity(int id) {

		this.id = id;

		this.children = new TreeMap<Integer, Entity>();
		this.childrenInOrder = new TreeSet<Entity>(new Comparator<Entity>() {

			@Override
			public int compare(Entity o1, Entity o2) {
				int result = o1.getOrder().compareTo(o2.getOrder());
				
				if (result != 0) {
					return result;
				}
				
				return o1.getId().compareTo(o2.getId());
			}
		});

		// physics
		this.data = new float[7];
		this.data[SCALE_X] = 1;
		this.data[SCALE_Y] = 1;
		
		// network
		this.networkData = _createNetworkData();
		
		// config
		this.active = true;
		this.visible = true;
		this.transmitting = false;
		this.recursing = true;

		this.setOrder(id);
		
	}

	public Entity add(Entity e) {
		if (!this.children.containsKey(e.getId())) {
			this.children.put(e.getId(), e);
			this.childrenInOrder.add(e);
		} else {
			throw new IllegalArgumentException("entity with this id was already added");
		}
		return e;
	}

	public Entity replace(Entity e) {
		this.childrenInOrder.remove(this.children.get(e.get(id)));
		this.children.put(e.getId(), e);
		this.childrenInOrder.add(e);
		return e;
	}

	public Entity get(int id) {
		return this.children.get(id);
	}

	public void remove(int id) {
		remove(get(id));
	}
	
	public void remove(Entity e) {
		if (this.children.containsKey(e.getId())) {
			this.childrenInOrder.remove(e);
			this.children.remove(e.getId());
		} else {
			throw new IllegalArgumentException(
					"entity with this id doesn't exist");
		}
	}

	protected void preRender(Graphics graphicContext) {
		graphicContext.pushTransform();
		
		
		graphicContext.translate(getData()[X], getData()[Y]); // move to pos																// position

		graphicContext.translate(getData()[CENTER_X], getData()[CENTER_Y]); // translate back 
		
		graphicContext.rotate(0, 0, getData()[ROTATION]); // rotate
		
		graphicContext.scale(getData()[SCALE_X], getData()[SCALE_Y]); // zoom
																	// from
																	// point

		graphicContext.translate(-getData()[CENTER_X], -getData()[CENTER_Y]); // set center of rotation
	}

	protected void renderImpl(Graphics graphicContext) {
		if (this.recursing) {
			for (Entity child : this.childrenInOrder) {
				child.render(graphicContext);
			}
		}
	}

	public final void render(Graphics graphicContext) {
		preRender(graphicContext);
		renderImpl(graphicContext);
		postRender(graphicContext);
	}

	protected void postRender(Graphics graphicContext) {
		graphicContext.popTransform();
	}
	
	public void update(int deltaInMillis) {
		if (this.recursing) {
			for (Entity child : this.childrenInOrder) {
				child.update(deltaInMillis);
			}
		}
	}
	
	public void handleInput(Input input) {
		if (this.recursing) {
			for (Entity child : this.childrenInOrder) {
				child.handleInput(input);
			}
		}
	}

	/**
	 * internal factory method to create network data object
	 * must be overloaded for inherited classes !!
	 * @return
	 */
	protected NetworkData _createNetworkData() {
		return new NetworkData(this.getId());
	}
	
	/**
	 * updates the network data member and returns it
	 * @return
	 */
	public NetworkData getNetworkData() {

		this.networkData.data = getData();		
		return this.networkData;
	}
	
	/**
	 * Used to set data values coming from the network into entity
	 * @param networkData
	 */
	public void applyNetworkData(NetworkData networkData) {
		this.setData(networkData.data);
	}
	
	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isTransmitting() {
		return this.transmitting;
	}
	
	public void setTransmitting(boolean transmitting) {
		this.transmitting = transmitting;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}

	public void setRecursing(boolean recursing) {
		this.recursing = recursing;
	}

	public boolean isRecursing() {
		return this.recursing;
	}

	public float[] getData() {
		return this.data;
	}
	
	public float getData(int position)
	{
		return this.data[position];
	}

	public void setData(float[] data) {
		this.data = data;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}
	
	public Set<Entity> getChildren() {
		return Collections.unmodifiableSet(childrenInOrder);
	}
}
