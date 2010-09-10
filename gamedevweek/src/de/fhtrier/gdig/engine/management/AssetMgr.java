package de.fhtrier.gdig.engine.management;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.tiled.TiledMap;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;

public class AssetMgr {

	private String assetPathPrefix;
	private String assetFallbackPathPrefix;
	private HashMap<Integer, Image> images;
	private HashMap<Integer, Sound> sounds;
	private HashMap<Integer, Music> musics;
	private HashMap<Integer, TiledMap> tiledMaps;
	private HashMap<Integer, Animation> animations;
	private HashMap<Integer, ParticleSystem> particleSystems;

	public AssetMgr() {

		this.images = new HashMap<Integer, Image>();
		this.sounds = new HashMap<Integer, Sound>();
		this.tiledMaps = new HashMap<Integer, TiledMap>();
		this.animations = new HashMap<Integer, Animation>();
		this.particleSystems = new HashMap<Integer, ParticleSystem>();
		this.assetPathPrefix = Assets.AssetManagerPath;
		this.assetFallbackPathPrefix = Assets.AssetManagerFallbackPath;
	}

	public void setAssetFallbackPathPrefix(String path) {
		this.assetFallbackPathPrefix = path;
	}

	public void setAssetPathPrefix(String path) {
		this.assetPathPrefix = path;

		if (this.assetFallbackPathPrefix.equals("")) {
			setAssetFallbackPathPrefix(path);
		}
	}

	static String combinePathStrings(String prefix, String suffix) {

		if (prefix.endsWith("\\") || prefix.endsWith("/")) {
			prefix = prefix.substring(0, prefix.length() - 1);
		}

		if (suffix.startsWith("\\") || suffix.startsWith("/")) {
			suffix = suffix.replaceFirst("[\\\\|/]", "");
		}

		return prefix + File.separator + suffix;
	}

	public void storeImage(int id, Image img) {
		this.images.put(id, img);
	}

	public Image storeImage(int id, String src) throws SlickException {
		Image img = null;
		try {
			img = new Image(combinePathStrings(this.assetPathPrefix, src));
		} catch (Exception e) {
			img = new Image(combinePathStrings(this.assetFallbackPathPrefix,
					src));
		}
		storeImage(id, img);
		return img;
	}

	public Image getImage(int id) {
		return this.images.get(id);
	}

	public void storeSound(int id, Sound snd) {
		this.sounds.put(id, snd);
	}

	public Sound storeSound(int id, String src) throws SlickException {
		Sound snd = null;
		try {
			snd = new Sound(combinePathStrings(this.assetPathPrefix, src));
		} catch (Exception e) {
			snd = new Sound(combinePathStrings(this.assetFallbackPathPrefix,
					src));
		}
		storeSound(id, snd);
		return snd;
	}

	public Sound getSound(int id) {
		return this.sounds.get(id);
	}
	
	public void storeMusic(int id, Music song)
	{
		this.musics.put(id, song);
	}
	
	public Music storeMusic(int id, String src) throws SlickException {
		Music song = null;
		try {
			song = new Music(combinePathStrings(this.assetPathPrefix, src));
		} catch (Exception e) {
			song = new Music(combinePathStrings(this.assetFallbackPathPrefix, src));
		}
		storeMusic(id, song);
		return song;
	}
	
	public Music getMusic(int id) {
		return this.musics.get(id);
	}

	public void storeTiledMap(int id, TiledMap map) {
		this.tiledMaps.put(id, map);
	}

	public TiledMap storeTiledMap(int id, String src) throws SlickException {
		TiledMap map = null;
		try {
			map = new TiledMap(combinePathStrings(this.assetPathPrefix, src));
		} catch (Exception e) {
			map = new TiledMap(combinePathStrings(this.assetFallbackPathPrefix,
					src));
		}
		storeTiledMap(id, map);
		return map;
	}

	public TiledMap getTiledMap(int id) {
		return this.tiledMaps.get(id);
	}

	public Animation storeAnimation(int id, String src, int cellWidth,
			int cellHeight, int duration) throws SlickException {
		SpriteSheet sheet = null;
		try {
			sheet = new SpriteSheet(combinePathStrings(this.assetPathPrefix,
					src), cellWidth, cellHeight);
		} catch (Exception e) {
			sheet = new SpriteSheet(combinePathStrings(
					this.assetFallbackPathPrefix, src), cellWidth, cellHeight);
		}

		Animation anim = new Animation(sheet, duration);
		storeAnimation(id, anim);
		return anim;
	}
	
	public Animation storeAnimation(int id, String src) throws SlickException {
		StringTokenizer tok = new StringTokenizer(src, "_");
		int cellWidth = 0;
		int cellHeight = 0;
		int duration = 0;
		try {
		tok.nextToken();
			cellWidth = Integer.parseInt(tok.nextToken());
			cellHeight = Integer.parseInt(tok.nextToken());
			StringTokenizer tok2 = new StringTokenizer(tok.nextToken(), ".");
			duration = Integer.parseInt(tok2.nextToken());
		} catch (Exception e) {
			throw new IllegalArgumentException("Trying to load file in incorrect format? filename_96_96_75.png");
		}
		return storeAnimation(id, src, cellWidth, cellHeight, duration);
	}

	public void storeAnimation(int id, Animation anim) {
		this.animations.put(id, anim);
	}

	public Animation getAnimation(int id) {
		return this.animations.get(id);
	}
	
	public ParticleSystem storeParticleSystem (int id, String imgPath, String emitterCfgPath) throws SlickException {
		
		Image image = null;
		try {
			image = new Image(combinePathStrings(this.assetPathPrefix, imgPath), false);
		} catch (Exception e1) {
			image = new Image(combinePathStrings(this.assetFallbackPathPrefix, imgPath), false);
		}  
		
        ParticleSystem system = new ParticleSystem(image);  
          
        ConfigurableEmitter emitter1 = null;
        try {
			File xmlFile = new File(combinePathStrings(this.assetPathPrefix, emitterCfgPath));
			emitter1 = ParticleIO.loadEmitter(xmlFile);			
		} catch (Exception e) {
			File xmlFile = new File(combinePathStrings(this.assetFallbackPathPrefix, emitterCfgPath));
			try {
				emitter1 = ParticleIO.loadEmitter(xmlFile);
			} catch (IOException e1) {
				throw new IllegalArgumentException("Wrong cfg file or incorrect path?");
			}
		}
 
		system.addEmitter(emitter1);
		
		storeParticleSystem(id, system);
		
		return system;
	}
	
	public void storeParticleSystem (int id, ParticleSystem system) {
		this.particleSystems.put(id, system);
	}
	
	public ParticleSystem getParticleSystem (int id) {
		return this.particleSystems.get(id);
	}
	
	public String makePathRelativeToAssetPath(String path) {
		String result = AssetMgr.combinePathStrings(assetPathPrefix, path);
		File file = new File(result);
		
		if (!file.exists()) {
			result = AssetMgr.combinePathStrings(assetFallbackPathPrefix, path);
			file = new File(result);
			
			if (!file.exists()) {
				throw new RuntimeException("File " + path + " neither found in " + assetPathPrefix + " nor in " + assetFallbackPathPrefix);
			}
		}
		return result;
	}
}
