package de.fhtrier.gdig.demos.jumpnrun.client.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.engine.graphics.shader.Shader;
import de.fhtrier.gdig.engine.management.AssetMgr;

public class MenuBackgroundRenderer {
	
	private Image backgroundImage;
	private Shader menuShader;
	private Color col;
	private float colr = 0;
	private float colg = 0;
	private float colb = 0;
	private Image bgglowImage;
	private int counter = 0;
	
	private static MenuBackgroundRenderer instance=null;

	public static MenuBackgroundRenderer getInstance()
	{
		if (instance==null)
		{
			instance = new MenuBackgroundRenderer();
		}
		return instance;
	}
	
	private MenuBackgroundRenderer()
	{
		try {
			AssetMgr amgr = new AssetMgr();
			backgroundImage = new Image(Assets.Config.AssetGuiPath+"/images/menubg_pflanze.png");
			bgglowImage = new Image(Assets.Config.AssetGuiPath+"/images/menubg_pflanze_glow.png");
			menuShader = new Shader(amgr.getPathRelativeToAssetPath("shader/simple.vert"),
					amgr.getPathRelativeToAssetPath("shader/menucolor.frag"));
			col = new Color(2, 0, 0, 0.5f);
		} catch (SlickException e) {
			Log.error(e);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	throws SlickException {
		
		if (++counter > 120)
		{
			counter = 0;
			colr = (float)Math.random()/10-0.05f;
			colg = (float)Math.random()/10-0.05f;
			colb = (float)Math.random()/10-0.05f;
		}
		
		col.r = clamp(col.r+colr, 0, 2);
		col.g = clamp(col.g+colg, 0, 2);
		col.b = clamp(col.b+colb, 0, 2);
		
		g.drawImage(backgroundImage,0,0);
		
		Shader.pushShader(menuShader);
		menuShader.setValue("menucolor", col);
		g.drawImage(bgglowImage,0,0);
		Shader.popShader();
	}

	private static float clamp(float f, float i, float j) {
		if (f < i) return i;
		if (f > j) return j;
		
		return f;
	}

	
	public void renderMouseParticle()
	{
		
	}
}
