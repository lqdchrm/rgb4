package de.fhtrier.gdig.engine.sound;

import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.engine.management.AssetMgr;

public abstract class SoundManager {

	private AssetMgr assets;
	
	protected static SoundManager instance = null;	

	protected SoundManager() throws SlickException {
		this.assets = new AssetMgr();
	}

	protected abstract boolean isMuted();
	protected abstract boolean isMusicMuted();
	protected abstract boolean isSoundMuted();
	
	public static void playSound(int id) {
		
		if (instance == null || instance.isMuted() || instance.isSoundMuted())
			return;
		
		SoundManager.getInstance().assets.getSound(id).play();
	}

	public static void playSound(int id, float pitch, float volume) {
		if (instance == null || instance.isMuted() || instance.isSoundMuted())
			return;
		
		SoundManager.getInstance().assets.getSound(id).play(pitch, volume);
	}

	public static void loopSound(int id) {
		if (instance == null || instance.isMuted() || instance.isSoundMuted())
			return;
		
		SoundManager.getInstance().assets.getSound(id).loop();
	}

	public static void loopSound(int id, float pitch, float volume) {
		if (instance == null || instance.isMuted() || instance.isSoundMuted())
			return;
		
		SoundManager.getInstance().assets.getSound(id).loop(pitch, volume);
	}

	public static boolean soundPlaying(int id) {
		if (instance == null)
			return false;
		
		return SoundManager.getInstance().assets.getSound(id).playing();
	}

	public static void stopSound(int id) {
		if (instance == null)
			return;
		
		SoundManager.getInstance().assets.getSound(id).stop();
	}

	public static void playMusic(int id) {
		if (instance == null || instance.isMuted() || instance.isMusicMuted())
			return;
		
		SoundManager.getInstance().assets.getMusic(id).play();
	}

	public static void playMusic(int id, float pitch, float volume) {
		if (instance == null || instance.isMuted() || instance.isMusicMuted())
			return;
		
		SoundManager.getInstance().assets.getMusic(id).play(pitch, volume);
	}

	public static void loopMusic(int id) {
		if (instance == null || instance.isMuted() || instance.isMusicMuted())
			return;
		
		SoundManager.getInstance().assets.getMusic(id).loop();
	}

	public static void loopMusic(int id, float pitch, float volume) {
		if (instance == null || instance.isMuted() || instance.isMusicMuted())
			return;
		
		SoundManager.getInstance().assets.getMusic(id).loop(pitch, volume);
	}

	public static boolean musicPlaying(int id) {
		if (instance == null)
			return false;
		
		return SoundManager.getInstance().assets.getMusic(id).playing();
	}

	public static void pauseMusic(int id) {
		if (instance == null)
			return;
		
		SoundManager.getInstance().assets.getMusic(id).pause();
	}

	public static void stopMusic(int id) {
		if (instance == null)
			return;
		
		SoundManager.getInstance().assets.getMusic(id).stop();
	}

	public static void resumeMusic(int id) {
		if (instance == null || instance.isMuted() || instance.isMusicMuted())
			return;
		
		SoundManager.getInstance().assets.getMusic(id).resume();
	}

	public static float getMusicVolume(int id) {
		if (instance == null)
			return 0.0f;
		
		return SoundManager.getInstance().assets.getMusic(id).getVolume();
	}

	public static void setMusicVolume(int id, float volume) {
		if (instance == null)
			return;
		
		SoundManager.getInstance().assets.getMusic(id).setVolume(volume);
	}

	public static void fadeMusic(int id, int duration, float endVolume,
			boolean stopAfterFade) {
		if (instance == null)
			return;
	
		SoundManager.getInstance().assets.getMusic(id).fade(duration,
				endVolume, stopAfterFade);
	}

	public static float getMusicPosition(int id) {
		if (instance == null)
			return 0.0f;
		
		return SoundManager.getInstance().assets.getMusic(id).getPosition();
	}

	public static void setMusicPosition(int id, float position) {
		if (instance == null)
			return;
		
		SoundManager.getInstance().assets.getMusic(id).setPosition(position);
	}
	
	public static SoundManager getInstance() {
		if (instance == null) {
			throw new RuntimeException("Soundmanager Singleton not yet created");
		}

		return instance;
	}
	
	public AssetMgr getAssetMgr() {
		return assets;
	}
}
