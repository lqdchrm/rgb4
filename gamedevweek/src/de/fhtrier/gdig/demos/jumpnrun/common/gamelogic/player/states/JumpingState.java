package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.Factory;

public class JumpingState extends AbstractAssetState {

	private Animation anim;
	private Animation weaponAnim;
	
	public JumpingState(Player player, Factory factory)
			throws SlickException {
		super(player, Assets.Player.aJumpAnimId, Assets.Player.bJumpAnimId, Assets.Player.aJumpAnimImagePath, Assets.Player.bJumpAnimImagePath, Assets.Weapon.JumpAnimId, Assets.Weapon.JumpAnimImagePath, EntityOrder.Player, factory);
	
		AssetEntity e = getGfxEntity();
		
		anim = e.getAssetMgr().getAnimation(e.getAssetId());
		anim.setLooping(false);
		
		e = getWeaponGfxEntity();
		
		weaponAnim = e.getAssetMgr().getAnimation(e.getAssetId());
		weaponAnim.setLooping(false);
	}
	
	public void getAnim() {
		AssetEntity e = getGfxEntity();
		
		anim = e.getAssetMgr().getAnimation(e.getAssetId());
		anim.setLooping(false);
	}
	
	@Override
	public void enter() {
		getAnim();
		getPlayer().setOnGround(false);
		anim.restart();
		weaponAnim.restart();
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
