package de.fhtrier.gdig.demos.jumpnrun.common.events;

public abstract class Event {
	private long id;

	public Event() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	/**
	 * is executed in EventManager of each event
	 */
	public void update() {
	}
}
