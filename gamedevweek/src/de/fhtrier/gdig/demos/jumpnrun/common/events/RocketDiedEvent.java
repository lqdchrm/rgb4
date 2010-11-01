package de.fhtrier.gdig.demos.jumpnrun.common.events;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Rocket;

public class RocketDiedEvent extends Event {

	private Rocket rocket;

	public RocketDiedEvent(Rocket rocket) {
		super();
		this.rocket = rocket;
	}

	@Override
	public void update() {
		rocket.die();
	}
}
