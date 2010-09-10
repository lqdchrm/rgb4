package de.fhtrier.gdig.engine.sound;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class SoundManager {

	private AssetMgr assets;

	private static SoundManager instance = null;

	private SoundManager(AssetMgr assets) throws SlickException {
		this.assets = assets;

		//this.assets.storeSound(Assets.LevelSoundtrackId, Assets.LevelSoundtrackPath);

		this.assets.storeSound(Assets.PlayerRunSoundId, Assets.PlayerRunSoundPath);
		this.assets.storeSound(Assets.PlayerJumpSoundId, Assets.PlayerJumpSoundPath);
		this.assets.storeSound(Assets.PlayerLandSoundId, Assets.PlayerLandSoundPath);
		//this.assets.storeSound(Assets.PlayerIdleSoundId, Assets.PlayerIdleSoundPath);
		this.assets.storeSound(Assets.PlayerDyingSoundId, Assets.PlayerDyingSoundPath);
		this.assets.storeSound(Assets.PlayerShootSoundId, Assets.PlayerShootSoundPath);
		this.assets.storeSound(Assets.PlayerGetItemSoundId, Assets.PlayerGetItemSoundPath);
		this.assets.storeSound(Assets.PlayerWoundSoundId, Assets.PlayerWoundSoundPath);
		this.assets.storeSound(Assets.PlayerJoiningSoundID, Assets.PlayerJoiningSoundPath);
		this.assets.storeSound(Assets.PlayerChangeColorSoundID, Assets.PlayerChangeColorPath);
		
		this.assets.storeSound(Assets.BeamSoundId, Assets.BeamSoundPath);
		this.assets.storeSound(Assets.BulletSoundId, Assets.PlayerShootSoundPath);
		this.assets.storeSound(Assets.DoomsdayDeviceSoundId, Assets.DoomsdayDeviceSoundPath);
		this.assets.storeSound(Assets.RocketStartSoundId, Assets.RocketStartSoundPath);
		this.assets.storeSound(Assets.RocketExplodeSoundId, Assets.RocketExplodeSoundPath);
		this.assets.storeSound(Assets.WeaponChangeColorSoundID, Assets.WeaponChangeColorSoundPath);
	}

	public static void playSound(int id) {
		SoundManager.getInstance().assets.getSound(id).play();
	}

	public static void playSound(int id, float pitch, float volume) {
		SoundManager.getInstance().assets.getSound(id).play(pitch, volume);
	}

	public static void loopSound(int id) {
		SoundManager.getInstance().assets.getSound(id).loop();
	}

	public static void loopSound(int id, float pitch, float volume) {
		SoundManager.getInstance().assets.getSound(id).loop(pitch, volume);
	}

	public static boolean soundPlaying(int id) {
		return SoundManager.getInstance().assets.getSound(id).playing();
	}

	public static void stopSound(int id) {
		SoundManager.getInstance().assets.getSound(id).stop();
	}

	public static void playMusic(int id) {
		SoundManager.getInstance().assets.getMusic(id).play();
	}

	public static void playMusic(int id, float pitch, float volume) {
		SoundManager.getInstance().assets.getMusic(id).play(pitch, volume);
	}

	public static void loopMusic(int id) {
		SoundManager.getInstance().assets.getMusic(id).loop();
	}

	public static void loopMusic(int id, float pitch, float volume) {
		SoundManager.getInstance().assets.getMusic(id).loop(pitch, volume);
	}

	public static boolean musicPlaying(int id) {
		return SoundManager.getInstance().assets.getMusic(id).playing();
	}

	public static void pauseMusic(int id) {
		SoundManager.getInstance().assets.getMusic(id).pause();
	}

	public static void stopMusic(int id) {
		SoundManager.getInstance().assets.getMusic(id).stop();
	}

	public static void resumeMusic(int id) {
		SoundManager.getInstance().assets.getMusic(id).resume();
	}

	public static float getMusicVolume(int id) {
		return SoundManager.getInstance().assets.getMusic(id).getVolume();
	}

	public static void setMusicVolume(int id, float volume) {
		SoundManager.getInstance().assets.getMusic(id).setVolume(volume);
	}

	public static void fadeMusic(int id, int duration, float endVolume,
			boolean stopAfterFade) {
		SoundManager.getInstance().assets.getMusic(id).fade(duration,
				endVolume, stopAfterFade);
	}

	public static float getMusicPosition(int id) {
		return SoundManager.getInstance().assets.getMusic(id).getPosition();
	}

	public static void setMusicPosition(int id, float position) {
		SoundManager.getInstance().assets.getMusic(id).setPosition(position);
	}

	public static void setAssetMgr(AssetMgr assets) throws SlickException {
		if (instance == null) {
			instance = new SoundManager(assets);
		} else {
			instance.assets = assets;
		}
	}

	public static SoundManager getInstance() {
		if (instance == null) {
			throw new RuntimeException("Soundmanager Singleton not yet created");
		}

		return instance;
	}

}
