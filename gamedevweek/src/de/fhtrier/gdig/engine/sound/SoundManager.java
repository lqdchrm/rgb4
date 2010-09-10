package de.fhtrier.gdig.engine.sound;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class SoundManager {

	private AssetMgr assets;
	
	private static SoundManager instance = null;
	

	private SoundManager() throws SlickException {
		this.assets = new AssetMgr();
		
		this.assets.storeMusic(Assets.Sounds.LevelSoundtrackId, Assets.Sounds.LevelSoundtrackPath);
		
		this.assets.storeSound(Assets.Sounds.PlayerRunSoundId, Assets.Sounds.PlayerRunSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerJumpSoundId, Assets.Sounds.PlayerJumpSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerLandSoundId, Assets.Sounds.PlayerLandSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerDyingSoundId, Assets.Sounds.PlayerDyingSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerShootSoundId, Assets.Sounds.PlayerShootSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerGetItemSoundId, Assets.Sounds.PlayerGetItemSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerWoundSoundId, Assets.Sounds.PlayerWoundSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerJoiningSoundID, Assets.Sounds.PlayerJoiningSoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerChangeColorSoundID, Assets.Sounds.PlayerChangeColorPath);
		this.assets.storeSound(Assets.Sounds.PlayerPhrase1SoundID, Assets.Sounds.PlayerPhrase1SoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerPhrase2SoundID, Assets.Sounds.PlayerPhrase2SoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerPhrase3SoundID, Assets.Sounds.PlayerPhrase3SoundPath);
		this.assets.storeSound(Assets.Sounds.PlayerPhrase4SoundID, Assets.Sounds.PlayerPhrase4SoundPath);
		
		this.assets.storeSound(Assets.Sounds.BeamSoundId, Assets.Sounds.BeamSoundPath);
		this.assets.storeSound(Assets.Sounds.DoomsdayDeviceSoundId, Assets.Sounds.DoomsdayDeviceSoundPath);
		this.assets.storeSound(Assets.Sounds.RocketStartSoundId, Assets.Sounds.RocketStartSoundPath);
		this.assets.storeSound(Assets.Sounds.RocketExplodeSoundId, Assets.Sounds.RocketExplodeSoundPath);
		this.assets.storeSound(Assets.Sounds.WeaponChangeColorSoundID, Assets.Sounds.WeaponChangeColorSoundPath);
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
	
	public static SoundManager getInstance() {
		if (instance == null) {
			throw new RuntimeException("Soundmanager Singleton not yet created");
		}

		return instance;
	}

	public static void init() throws SlickException {
		if (instance == null) {
			instance = new SoundManager();
		}
	}

}
