package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;

public abstract class RGB4Game extends StateBasedGame implements
		INetworkCommandListener {
	public RGB4Game(String title) {
		super(title);

	}

	// network commands are passed through to the active gamestate
	@Override
	public void notify(final INetworkCommand cmd) {

		if (Constants.Debug.networkDebug) {
			Log.debug("Server-Process:" + cmd);
		}

		final GameState currentState = this.getCurrentState();
		if (currentState != null) {
			if (currentState instanceof INetworkCommandListener)
				((INetworkCommandListener) currentState).notify(cmd);
		}
	}
}
