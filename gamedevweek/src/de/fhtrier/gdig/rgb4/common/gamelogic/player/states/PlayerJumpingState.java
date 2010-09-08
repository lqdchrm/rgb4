package de.fhtrier.gdig.rgb4.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.gfx.AssetEntity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.Player;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.rgb4.identifiers.Assets;
import de.fhtrier.gdig.rgb4.identifiers.Constants;
import de.fhtrier.gdig.rgb4.identifiers.EntityOrder;

public class PlayerJumpingState extends PlayerAssetState {

	private Animation anim;
	
	public PlayerJumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.PlayerJumpAnimId,
				Assets.PlayerJumpAnimImagePath, EntityOrder.Player, factory);
	
		AssetEntity e = getGfxEntity();
		
		anim = e.Assets().getAnimation(e.getAssetId());
		anim.setLooping(false);
	}
	
	@Override
	public void enter() {
		getPlayer().setOnGround(false);
		anim.restart();
	}

	@Override
	public void leave() {
	}

	@Override
	public void update() {
		
		// check if landed
		if (getPlayer().isOnGround()) {
			getPlayer().applyAction(PlayerActions.Land);
		}
	}

}
