package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.engine.graphics.entities.AssetEntity;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class DyingState extends AbstractAssetState {

	private Animation anim;
	private Animation weaponAnim;
	
	public DyingState(Player player, Factory factory)
			throws SlickException {
		super(player,
				Assets.Player.aTodAnimId, Assets.Player.bTodAnimId,
				Assets.Player.aTodImagePath, Assets.Player.bTodImagePath,
				Assets.Weapon.DyingAnimId, Assets.Weapon.DyingImagePath,
				EntityOrder.Player, factory);
	
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

		SoundManager.playSound(Assets.Sounds.PlayerDyingSoundId, 1.0f, 0.3f);
	}

	@Override
	public void leave() {
		getPlayer().respawn(); // FIXME: Do we want to respawn immediately?
		getPlayer().getPlayerStats().increaseDeaths();
		Team.getTeamById(getPlayer().getPlayerCondition().teamId).increaseDeaths();
		
		getPlayer().applyAction(PlayerActions.Revive);
	}

	@Override
	public void update() {
		if (anim.isStopped()) {
			getPlayer().applyAction(PlayerActions.Revive);
		}

	}

}
