package de.fhtrier.gdig.rgb4.identifiers;

public class Assets {
	
	// Config
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

	public static final int PlayerJumpDownAnimId = 112;
	public static final String PlayerJumpDownAnimImagePath = "sprites/player/springenrunter_128_128_20.png";
	
	public static final int PlayerJumpUpAnimId = 113;
	public static final String PlayerJumpUpAnimImagePath = "sprites/player/springenhoch_128_128_20.png";
	
	public static final int PlayerShootStandingAnimId = 114;
	public static final String PlayerShootStandingImagePath = "sprites/player/schussstehen_128_128_20.png";
	
	public static final int PlayerShootJumpingAnimId = 115;
	public static final String PlayerShootJumpingImagePath =  "sprites/player/schussspringen_128_128_20.png";
	
	public static final int PlayerShootRunningAnimId = 116;
	public static final String PlayerShootRunningImagePath =  "sprites/player/schusslaufen_128_128_20.png";

	public static final int PlayerRunningAnimId = 117;
	public static final String PlayerRunningImagePath = "sprites/player/laufen_128_128_20.png";
	
	public static final String PlayerVertexShaderPath = "content/jumpnrun/shader/simple.vert";
	public static final String PlayerPixelShaderPath = "content/jumpnrun/shader/playercolor.frag";
	
	// Bullet
	public static final int BulletAnimId = 21;
	public static final String BulletAnimImagePath = "sprites/player/projektil_100_35_10.png";
	public static final String BulletAnimPath = "sprites/items/Bullet2_64_64_100.png";

	// Gem 
	public static final int GemImageId = 20;
	
	// Weapon
	public static final int WeaponImageId = 22;
}
