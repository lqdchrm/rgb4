package de.fhtrier.gdig.demos.jumpnrun.common.events;

public class KeineAhnungEvent extends Event {
	public KeineAhnungEvent () {
		
	}
	
	@Override 
	public void update() {
		System.out.println("Keine Ahnung!");
	}
}
