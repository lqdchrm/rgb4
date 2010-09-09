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
	
	public static final int PlayerFallingAnimId = 118;
	public static final String PlayerFallingImagePath = "sprites/player/springenhoch_128_128_20.png";
	
	public static final int PlayerFallShootingAnimId = 119;
	public static final String PlayerFallShootingImagePath = "sprites/player/schussspringen_128_128_20.png";
	
	public static final String PlayerVertexShaderPath = "shader/simple.vert";
	public static final String PlayerPixelShaderPath = "shader/playercolor.frag";
	public static final String PlayerGlowImagePath = "shader/playerglow.png";
	
	// Bullet
	public static final int BulletAnimId = 21;
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

	/* Sounds */

	public static final int LevelSoundtrackId = 1;
	public static final String LevelSoundtrackPath = "sounds/background.ogg";

	public static final int PlayerRunSoundId = 20;
	public static final int PlayerJumpSoundId = 21;
	public static final int PlayerLandSoundId = 22;
	public static final int PlayerIdleSoundId = 23;
	public static final int PlayerDyingSoundId = 24;
	public static final int PlayerShootSoundId = 25;
	public static final int PlayerGetItemSoundId = 26;
	public static final int PlayerWoundSoundId = 27;
	public static final int PlayerJoiningSoundID = 28;
	public static final int PlayerChangeColorSoundID = 29;
	
	public static final String PlayerRunSoundPath = "sounds/playerrun.ogg";
	public static final String PlayerJumpSoundPath = "sounds/playerjump.ogg";
	public static final String PlayerLandSoundPath = "sounds/playerland.ogg";
	public static final String PlayerIdleSoundPath = "sounds/playeridle.ogg";
	public static final String PlayerDyingSoundPath = "sounds/playerdie.ogg";
	public static final String PlayerShootSoundPath = "sounds/playershoot.ogg";
	public static final String PlayerGetItemSoundPath = "sounds/playergetitem.ogg";
	public static final String PlayerWoundSoundPath = "sounds/playerwound3.ogg";
	public static final String PlayerJoiningSoundPath = "sounds/playerjoin.ogg";
	public static final String PlayerChangeColorPath = "sounds/playerchangecolor.ogg";

	public static final int BulletSoundId = 30;
	public static final int RocketStartSoundId = 31;
	public static final int RocketExplodeSoundId = 32;	
	public static final int BeamSoundId = 34;
	public static final int DoomsdayDeviceSoundId = 35;
	
	public static final String BulletSoundPath = "sounds/bullet.ogg";
	public static final String RocketStartSoundPath = "sounds/rocketstart.ogg";
	public static final String RocketExplodeSoundPath = "sounds/rocketexplode.ogg";
	public static final String DoomsdayDeviceSoundPath = "sounds/rocketstart.ogg";
	public static final String BeamSoundPath = "sounds/beam.ogg";
	
	public static final int WeaponChangeColorSoundID = 60;
	public static final String WeaponChangeColorSoundPath = "sounds/weaponchangecolor.ogg";
}
