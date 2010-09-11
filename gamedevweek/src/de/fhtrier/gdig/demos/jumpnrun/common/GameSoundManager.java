package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class GameSoundManager extends SoundManager {

	protected GameSoundManager() throws SlickException {
		super();

		AssetMgr assets = getAssetMgr();
		
		assets.storeMusic(Assets.Sounds.LevelSoundtrackId, Assets.Sounds.LevelSoundtrackPath);
		assets.storeMusic(Assets.Sounds.MenuSoundtrackId, Assets.Sounds.MenuSoundtrackPath);
		
		assets.storeSound(Assets.Sounds.PlayerRunSoundId, Assets.Sounds.PlayerRunSoundPath);
		assets.storeSound(Assets.Sounds.PlayerJumpSoundId, Assets.Sounds.PlayerJumpSoundPath);
		assets.storeSound(Assets.Sounds.PlayerLandSoundId, Assets.Sounds.PlayerLandSoundPath);
		assets.storeSound(Assets.Sounds.PlayerDyingSoundId, Assets.Sounds.PlayerDyingSoundPath);
		assets.storeSound(Assets.Sounds.PlayerShootSoundId, Assets.Sounds.PlayerShootSoundPath);
		assets.storeSound(Assets.Sounds.PlayerGetItemSoundId, Assets.Sounds.PlayerGetItemSoundPath);
		assets.storeSound(Assets.Sounds.PlayerWoundSoundId, Assets.Sounds.PlayerWoundSoundPath);
		assets.storeSound(Assets.Sounds.PlayerJoiningSoundID, Assets.Sounds.PlayerJoiningSoundPath);
		assets.storeSound(Assets.Sounds.PlayerChangeColorSoundID, Assets.Sounds.PlayerChangeColorPath);
		assets.storeSound(Assets.Sounds.PlayerPhrase1SoundID, Assets.Sounds.PlayerPhrase1SoundPath);
		assets.storeSound(Assets.Sounds.PlayerPhrase2SoundID, Assets.Sounds.PlayerPhrase2SoundPath);
		assets.storeSound(Assets.Sounds.PlayerPhrase3SoundID, Assets.Sounds.PlayerPhrase3SoundPath);
		assets.storeSound(Assets.Sounds.PlayerPhrase4SoundID, Assets.Sounds.PlayerPhrase4SoundPath);
		
		assets.storeSound(Assets.Sounds.BeamSoundId, Assets.Sounds.BeamSoundPath);
		assets.storeSound(Assets.Sounds.DoomsdayDeviceSoundId, Assets.Sounds.DoomsdayDeviceSoundPath);
		assets.storeSound(Assets.Sounds.RocketStartSoundId, Assets.Sounds.RocketStartSoundPath);
		assets.storeSound(Assets.Sounds.RocketExplodeSoundId, Assets.Sounds.RocketExplodeSoundPath);
		assets.storeSound(Assets.Sounds.WeaponChangeColorSoundID, Assets.Sounds.WeaponChangeColorSoundPath);
	}
	
	public static void init(boolean isClient) throws SlickException {
		
		// HACK
		if (isClient) {
			Constants.SoundConfig.soundEnabled = true;
			Constants.SoundConfig.musicEnabled = false;
		} else {
			Constants.SoundConfig.soundEnabled = false;
			Constants.SoundConfig.musicEnabled = true;
		}
		
		GameSoundManager.init();
	}
	
	protected static void init() throws SlickException {
		if (instance == null) {
			instance = new GameSoundManager();
		} else {
			throw new RuntimeException("SoundManager already initialized");
		}
	}

	@Override
	protected boolean isMuted() {
		return Constants.SoundConfig.isMuted;
	}
	
	@Override
	protected boolean isMusicMuted() {
		return !Constants.SoundConfig.musicEnabled;
	}
	
	@Override
	protected boolean isSoundMuted() {
		return !Constants.SoundConfig.soundEnabled;
	}
}
