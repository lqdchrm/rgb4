package de.fhtrier.gdig.demos.jumpnrun.server.network;

import java.io.Serializable;

public class NetworkLevel implements Serializable {

	private static final long serialVersionUID = 3590325800381122894L;

	private int levelID;
	private String assetPath;
	private String levelName;

	public NetworkLevel(int id, String assetPath, String levelName) {
		super();
		this.levelID = id;
		this.assetPath = assetPath;
		this.levelName = levelName;
	}

	public String getAssetPath() {
		return assetPath;
	}

	public void setAssetPath(String assetPath) {
		this.assetPath = assetPath;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public int getLevelID() {
		return levelID;
	}

}
