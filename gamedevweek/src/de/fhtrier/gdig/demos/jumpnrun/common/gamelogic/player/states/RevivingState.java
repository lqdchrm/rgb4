package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.engine.management.Factory;

public class RevivingState extends DyingState {

	private long startTime;
	
	public RevivingState(int stateId, Player player, Factory factory) throws SlickException {
		super(stateId, player, factory);
	}

	@Override
	protected void initAnim(Animation anim) {
		super.initAnim(anim);	
		anim.setCurrentFrame(anim.getFrameCount()-1);
	}
	
	@Override
	public void enter() {
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public void leave() {
		getPlayer().respawn(); 
	}

	@Override
	public void update() {

		// wait some time
		long delta = System.currentTimeMillis() - startTime;
		
		if (delta > Constants.GamePlayConstants.playerReviveDelayInMillis) {
			getPlayer().applyAction(PlayerActions.DoNothing);
		}
	}
}
