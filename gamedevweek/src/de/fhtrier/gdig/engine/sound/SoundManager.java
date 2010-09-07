package de.fhtrier.gdig.engine.sound;

import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public class SoundManager {
	
	AssetMgr assets;
	
	public SoundManager(AssetMgr assets) {
		this.assets = assets;
		
		/* TODO: Load Soundfiles here! */
		
	}
	
	public void playSound (int id)
	{
		this.assets.getSound(id).play();
	}
	
	public void playSound (int id, float pitch, float volume)
	{
		this.assets.getSound(id).play(pitch, volume);
	}
	
	public void loopSound (int id)
	{
		this.assets.getSound(id).loop();
	}
	
	public void loopSound (int id, float pitch, float volume)
	{
		this.assets.getSound(id).loop(pitch, volume);
	}

	public boolean soundPlaying (int id)
	{
		return this.assets.getSound(id).playing();
	}
	
	public void stopSound (int id)
	{
		this.assets.getSound(id).stop();
	}
}
