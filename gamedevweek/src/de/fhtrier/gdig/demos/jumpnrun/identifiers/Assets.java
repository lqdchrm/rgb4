package de.fhtrier.gdig.demos.jumpnrun.identifiers;


public class Assets {

	
	// Config
	public static class Config {
		public static final String AssetGuiPath = "content/rgb4/gui";
		public static String AssetManagerPath = "content/rgb4/default/";
		public static final String AssetManagerFallbackPath = "content/rgb4/fallback/";
		public static String GameTitle = "RGB 4 - Der Letzte macht das Licht aus";
	}

	// Level
	public static class Level {
		public static String AssetLevelPath = "content/rgb4";
		public static final int TileMapId = 3;
		public static String TileMapPath = "tiles/blocks.tmx";
		
		public static class Teleporter
		{
			public static final int TeleporterId = 4;
			public static final String TeleporterAnimationPath = "sprites/levelobjects/teleport_96_96_16.png";
		}
		
		public static class DoomsdayDevice
		{
			public static final int DoomsdayDeviceId = 5;
			public static final String DoomsdayDeviceAnimationPath = "sprites/levelobjects/doom_170_170_32.png";
		}

		public static final int TileMapRenderOrder = 10;
	}

	// Player
	public static class Player {
		public static final int aStandingAnimId = 111;
		public static final int bStandingAnimId = 311;
		public static final String aStandingAnimImagePath = "sprites/player/astehen_128_128_15.png";
		public static final String bStandingAnimImagePath = "sprites/player/bstehen_128_128_15.png";

		public static final int aLandAnimId = 112;
		public static final int bLandAnimId = 312;
		public static final String aLandAnimImagePath = "sprites/player/aspringenrunter_128_128_15.png";
		public static final String bLandAnimImagePath = "sprites/player/bspringenrunter_128_128_15.png";

		public static final int aJumpAnimId = 113;
		public static final int bJumpAnimId = 313;
		public static final String aJumpAnimImagePath = "sprites/player/aspringenhoch_128_128_15.png";
		public static final String bJumpAnimImagePath = "sprites/player/bspringenhoch_128_128_15.png";

		public static final int aShootStandingAnimId = 114;
		public static final int bShootStandingAnimId = 314;
		public static final String aShootStandingImagePath = "sprites/player/aschussstehen_128_128_15.png";
		public static final String bShootStandingImagePath = "sprites/player/bschussstehen_128_128_15.png";

		public static final int aShootJumpingAnimId = 115;
		public static final int bShootJumpingAnimId = 315;
		public static final String aShootJumpingImagePath = "sprites/player/aschussspringen_128_128_15.png";
		public static final String bShootJumpingImagePath = "sprites/player/bschussspringen_128_128_15.png";

		public static final int aShootRunningAnimId = 116;
		public static final int bShootRunningAnimId = 316;
		public static final String aShootRunningImagePath = "sprites/player/aschusslaufen_128_128_15.png";
		public static final String bShootRunningImagePath = "sprites/player/bschusslaufen_128_128_15.png";

		public static final int aRunningAnimId = 117;
		public static final int bRunningAnimId = 317;
		public static final String aRunningImagePath = "sprites/player/alaufen_128_128_15.png";
		public static final String bRunningImagePath = "sprites/player/blaufen_128_128_15.png";

		public static final int aFallingAnimId = 118;
		public static final int bFallingAnimId = 318;
		public static final String aFallingImagePath = "sprites/player/aspringenhoch_128_128_15.png";
		public static final String bFallingImagePath = "sprites/player/bspringenhoch_128_128_15.png";

		public static final int aFallShootingAnimId = 119;
		public static final int bFallShootingAnimId = 319;
		public static final String aFallShootingImagePath = "sprites/player/aschussspringen_128_128_15.png";
		public static final String bFallShootingImagePath = "sprites/player/bschussspringen_128_128_15.png";

		public static final int aTodAnimId = 120;
		public static final int bTodAnimId = 320;
		public static final String aTodImagePath = "sprites/player/atod_128_128_15.png";
		public static final String bTodImagePath = "sprites/player/btod_128_128_15.png";
		
		public static final int aSchmerzAnimId = 121;
		public static final int bSchmerzAnimId = 321;
		public static final String aSchmerzImagePath = "sprites/player/aschmerz_128_128_15.png";
		public static final String bSchmerzImagePath = "sprites/player/bschmerz_128_128_15.png";
		
		public static final String VertexShaderPath = "shader/simple.vert";
		public static final String PixelShaderPath = "shader/playercolor.frag";
		public static final String GlowImagePath = "shader/playerglow.png";
	}

	// Bullet
	public static class Bullet {
		public static final int AnimId = 21;
		public static final String AnimPath = "sprites/items/bullet2_64_64_100.png";
		public static final String GlowImagePath = "shader/bulletglow.png";
	}

	public static class Rocket {
		public static final int AnimId = 21;
		public static final String AnimPath = "sprites/items/rotatingglas_64_64_100.png";
		public static final String GlowImagePath = "shader/rotatingglasglow.png";
	}

	
	public static class Weapon {
		public static final int StandingAnimId = 211;
		public static final String StandingAnimImagePath = "sprites/player/weaponstehen_128_128_15.png";

		public static final int LandAnimId = 212;
		public static final String LandAnimImagePath = "sprites/player/weaponspringenrunter_128_128_15.png";

		public static final int JumpAnimId = 213;
		public static final String JumpAnimImagePath = "sprites/player/weaponspringenhoch_128_128_15.png";

		public static final int ShootStandingAnimId = 214;
		public static final String ShootStandingImagePath = "sprites/player/weaponschussstehen_128_128_15.png";

		public static final int ShootJumpingAnimId = 215;
		public static final String ShootJumpingImagePath = "sprites/player/weaponschussspringen_128_128_15.png";

		public static final int ShootRunningAnimId = 216;
		public static final String ShootRunningImagePath = "sprites/player/weaponschusslaufen_128_128_15.png";

		public static final int RunningAnimId = 217;
		public static final String RunningImagePath = "sprites/player/weaponlaufen_128_128_15.png";

		public static final int FallingAnimId = 218;
		public static final String FallingImagePath = "sprites/player/weaponspringenhoch_128_128_15.png";

		public static final int FallShootingAnimId = 219;
		public static final String FallShootingImagePath = "sprites/player/weaponschussspringen_128_128_15.png";
		
		public static final int DyingAnimId = 220;
		public static final String DyingImagePath = "sprites/player/weapontod_128_128_15.png";
		
		// Weapon
		public static final int AnimId = 22;
		public static final int WeaponRenderOrder = 9;
		public static final int ParticleEffect = 200;
		public static final String ParticleEffectImgPath = "sprites/player/weaponparticle6.png";
		public static final String ParticleEffectCfgPath = "sprites/player/weaponparticleemitter7.xml";
		public static final float weaponXOffset = 35;		
		public static final float weaponYOffset = 35;
		public static final String GlowImagePath = "shader/weaponglow.png";
	}

	// Doomsday
	public static final int DoomsdayBigExplosionImageId = 43;
	public static final String DoomsdayBigExplosionImagePath = "sprites/items/doom.png";

	/* Sounds */
	public static class Sounds {
		public static final int LevelSoundtrackId = 1;
		public static final String LevelSoundtrackPath = "sounds/background.ogg";
		
		public static final int MenuSoundtrackId = 5;
		public static final String MenuSoundtrackPath = "sounds/menu.ogg";

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
		public static final int PlayerPhrase1SoundID = 14;
		public static final int PlayerPhrase2SoundID = 15;
		public static final int PlayerPhrase3SoundID = 16;
		public static final int PlayerPhrase4SoundID = 17;
		

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
		public static final String PlayerPhrase1SoundPath = "sounds/phrase1.ogg";
		public static final String PlayerPhrase2SoundPath = "sounds/phrase2.ogg";
		public static final String PlayerPhrase3SoundPath = "sounds/phrase3.ogg";
		public static final String PlayerPhrase4SoundPath = "sounds/phrase4.ogg";

		public static final int BulletSoundId = 30;
		public static final int RocketStartSoundId = 31;
		public static final int RocketExplodeSoundId = 32;
		public static final int BeamSoundId = 34;
		public static final int DoomsdayDeviceSoundId = 35;

		public static final String BulletSoundPath = "sounds/bullet.ogg";
		public static final String RocketStartSoundPath = "sounds/rocketstart.ogg";
		public static final String RocketExplodeSoundPath = "sounds/rocketexplode.ogg";
//		public static final String DoomsdayDeviceSoundPath = "sounds/doomsdaydevice1.ogg";
		public static final String DoomsdayDeviceSoundPath = "sounds/doomsdaydevice2.ogg"; // Alternativer sound
		public static final String BeamSoundPath = "sounds/beam.ogg";

		public static final int WeaponChangeColorSoundID = 60;
		public static final String WeaponChangeColorSoundPath = "sounds/weaponchangecolor.ogg";
	}
}
