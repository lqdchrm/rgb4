package de.fhtrier.gdig.engine.sound;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class SoundManager {

	AssetMgr assets;

	public SoundManager(AssetMgr assets) throws SlickException {
		this.assets = assets;
		
//		this.assets.storeMusic(Assets.LevelSoundtrackId, Assets.LevelSoundtrackPath);
		
//		this.assets.storeSound(Assets.PlayerRunSoundId, Assets.PlayerRunSoundPath);
		this.assets.storeSound(Assets.PlayerJumpSoundId, Assets.PlayerJumpSoundPath);
//		this.assets.storeSound(Assets.PlayerLandSoundId, Assets.PlayerLandSoundPath);
//		this.assets.storeSound(Assets.PlayerIdleSoundId, Assets.PlayerIdleSoundPath);
		this.assets.storeSound(Assets.PlayerDyingSoundId, Assets.PlayerDyingSoundPath);
//		this.assets.storeSound(Assets.PlayerShootSoundId, Assets.PlayerShootSoundPath);
		
//		this.assets.storeSound(Assets.BulletSoundId, Assets.BulletSoundPath);
//		this.assets.storeMusic(Assets.DoomsdayDeviceSoundId, Assets.DoomsdayDeviceSoundPath);
//		this.assets.storeSound(Assets.RocketStartSoundId, Assets.RocketStartSoundPath);
//		this.assets.storeSound(Assets.RocketExplodeSoundId, Assets.RocketExplodeSoundPath);

	}

	public void playSound(int id) {
		this.assets.getSound(id).play();
	}

	public void playSound(int id, float pitch, float volume) {
		this.assets.getSound(id).play(pitch, volume);
	}

	public void loopSound(int id) {
		this.assets.getSound(id).loop();
	}

	public void loopSound(int id, float pitch, float volume) {
		this.assets.getSound(id).loop(pitch, volume);
	}

	public boolean soundPlaying(int id) {
		return this.assets.getSound(id).playing();
	}

	public void stopSound(int id) {
		this.assets.getSound(id).stop();
	}

	public void playMusic(int id) {
		this.assets.getMusic(id).play();
	}

	public void playMusic(int id, float pitch, float volume) {
		this.assets.getMusic(id).play(pitch, volume);
	}

	public void loopMusic(int id) {
		this.assets.getMusic(id).loop();
	}

	public void loopMusic(int id, float pitch, float volume) {
		this.assets.getMusic(id).loop(pitch, volume);
	}

	public boolean musicPlaying(int id) {
		return this.assets.getMusic(id).playing();
	}

	public void pauseMusic(int id) {
		this.assets.getMusic(id).pause();
	}

	public void stopMusic(int id) {
		this.assets.getMusic(id).stop();
	}

	public void resumeMusic(int id) {
		this.assets.getMusic(id).resume();
	}

	public float getMusicVolume(int id) {
		return this.assets.getMusic(id).getVolume();
	}

	public void setMusicVolume(int id, float volume) {
		this.assets.getMusic(id).setVolume(volume);
	}

	public void fadeMusic(int id, int duration, float endVolume,
			boolean stopAfterFade) {
		this.assets.getMusic(id).fade(duration, endVolume, stopAfterFade);
	}

	public float getMusicPosition(int id) {
		return this.assets.getMusic(id).getPosition();
	}

	public void setMusicPosition(int id, float position) {
		this.assets.getMusic(id).setPosition(position);
	}

}
