package de.fhtrier.gdig.tools.spriteviewer;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationDataContainer {
	public String name;
	public Animation animation;
	public SpriteSheet ss;
	public Image image;
	public int duration;
	
	public AnimationDataContainer(File file) {
		try {
			duration = 100;
			image = new Image(file.getCanonicalPath());
			image.setCenterOfRotation(image.getWidth()/2.0f, image.getHeight()/2.0f);
			StringTokenizer tok = new StringTokenizer(file.getName(), "_");
			name = tok.nextToken();
			int tw = Integer.parseInt(tok.nextToken());
			int th = Integer.parseInt(tok.nextToken());
			ss = new SpriteSheet(image, tw, th);
			animation = new Animation(ss, duration);
			animation.setAutoUpdate(false);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
}
