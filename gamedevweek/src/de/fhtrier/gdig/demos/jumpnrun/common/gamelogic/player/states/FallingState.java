package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.Factory;

public class FallingState extends AbstractAssetState {
	
	private Animation anim;
	private Animation weaponAnim;
	
	public FallingState(int stateId, Player player, Factory factory)
			throws SlickException {
		super(stateId, player, Assets.Player.aFallingAnimId, Assets.Player.bFallingAnimId, Assets.Player.aFallingImagePath, Assets.Player.bFallingImagePath, Assets.Weapon.FallingAnimId, Assets.Weapon.FallingImagePath, EntityOrder.Player, factory);
	
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
		anim.restart();
		weaponAnim.restart();
	}

	@Override
	public void leave() {
	}

	@Override
	public void update() {	
		
		if (getPlayer().isOnGround()) {
			getPlayer().applyAction(PlayerActions.Land);
		}
	}
}

