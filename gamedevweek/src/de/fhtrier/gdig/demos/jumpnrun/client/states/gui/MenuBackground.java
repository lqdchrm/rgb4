package de.fhtrier.gdig.demos.jumpnrun.client.states.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;

public class MenuBackground {
	
	private Image backgroundImage;
	
	private static MenuBackground instance=null;

	public static MenuBackground getInstance()
	{
		if (instance==null)
		{
			instance = new MenuBackground();
		}
		return instance;
	}
	
	private MenuBackground()
	{
		try {
			backgroundImage = new Image(Assets.Config.AssetGuiPath+"/menubg_pflanze.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	throws SlickException {
		g.drawImage(backgroundImage,0,0);
	}

}
