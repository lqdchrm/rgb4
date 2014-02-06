package de.fhtrier.gdig.demos.jumpnrun.common.events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

	private static List<Event> events;
	private static long currentId;

	static {
		EventManager.events = new ArrayList<Event>();
		currentId = 0;
	}

	/**
	 * adds an event to EventManager
	 */
	public static void addEvent(Event event) {
		event.setId(currentId);
		events.add(event);

		currentId++;
	}

	/**
	 * executes every event.update()
	 */
	public static void update() {
		for (int x = 0; x < events.size(); x++) {
			events.get(x).update();
		}

		events.clear();
	}
}
