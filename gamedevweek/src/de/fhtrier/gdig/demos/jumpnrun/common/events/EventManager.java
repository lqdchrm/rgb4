package de.fhtrier.gdig.demos.jumpnrun.common.events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
	
	private static List <Event> events;
	
	static {
		EventManager.events = new ArrayList <Event> ();
	}
	
	public static void addEvent (Event event) {
		events.add(event);
	}
	
	public static void update () {
		for ( int x = 0; x < events.size(); x++ )
		{	
		   events.get( x ).update();
		}
		
		events.clear();
	}
}
