package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.Factory;

public class PlayerLandingState extends PlayerAssetState {
	
	private Animation anim;
	
	public PlayerLandingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerLandAnimId, Assets.PlayerLandAnimImagePath, EntityOrder.Player, factory);
	
		AssetEntity e = getGfxEntity();
		
		anim = e.Assets().getAnimation(e.getAssetId());
		anim.setLooping(false);
	}

	@Override
	public void enter() {
		anim.restart();
	}

	@Override
	public void leave() {
	}

	@Override
	public void update() {	

		// check for anim end
		if (anim.isStopped()) {
			getPlayer().applyAction(PlayerActions.DoNothing);
		}
	}
}
