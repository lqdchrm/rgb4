package de.fhtrier.gdig.demos.jumpnrun.identifiers;

public class Assets {
	
	// Config
	public static final String AssetGuiPath = "content/rgb4/gui";
	public static final String AssetManagerPath = "content/rgb4/publish/";
	public static final String AssetManagerFallbackPath = "content/rgb4/fallback/";
	public static final String GameTitle = "RGB 4 - Der Letzte macht das Licht aus";
	
	// Level
	public static final int LevelBackgroundImageId = 1;
	public static final int LevelMiddlegroundImageId = 2;
	public static final int LevelTileMapId = 3;
	public static final int LevelSoundtrackId = 4;

	// Player
	public static final int PlayerStandingAnimId = 111;
	public static final String PlayerStandingAnimImagePath = "sprites/player/stehen_128_128_20.png";
	

	public static final int PlayerLandAnimId = 112;
	public static final String PlayerLandAnimImagePath = "sprites/player/springenrunter_128_128_20.png";
	
	public static final int PlayerJumpAnimId = 113;
	public static final String PlayerJumpAnimImagePath = "sprites/player/springenhoch_128_128_20.png";
	
	public static final int PlayerShootStandingAnimId = 114;
	public static final String PlayerShootStandingImagePath = "sprites/player/schussstehen_128_128_20.png";
	
	public static final int PlayerShootJumpingAnimId = 115;
	public static final String PlayerShootJumpingImagePath =  "sprites/player/schussspringen_128_128_20.png";
	
	public static final int PlayerShootRunningAnimId = 116;
	public static final String PlayerShootRunningImagePath =  "sprites/player/schusslaufen_128_128_20.png";

	public static final int PlayerRunningAnimId = 117;
	public static final String PlayerRunningImagePath = "sprites/player/laufen_128_128_20.png";
	
	public static final String PlayerVertexShaderPath = "shader/simple.vert";
	public static final String PlayerPixelShaderPath = "shader/playercolor.frag";
	public static final String PlayerGlowImagePath = "shader/playerglow.png";
	
	// Bullet
	public static final int BulletAnimId = 21;
	public static final String BulletAnimImagePath = "sprites/player/projektil_100_35_10.png";
	public static final String BulletAnimPath = "sprites/items/bullet2_64_64_100.png";


	// Gem 
	public static final int GemImageId = 20;
	
	// Weapon
	public static final int WeaponImageId = 22;
	public static final String WeaponAnimImagePath = "sprites/items/waffestehen_128_128_20.png";
	
	// Particle Effects
	public static final int WeaponParticleEffect = 200;
	public static final String WeaponParticleEffectImgPath = "sprites/player/weaponparticle.png";
	public static final String WeaponParticleEffectCfgPath = "sprites/player/weaponparticleemitter3.xml";
	public static final String WeaponGlowImagePath = "shader/weaponglow.png";
}
