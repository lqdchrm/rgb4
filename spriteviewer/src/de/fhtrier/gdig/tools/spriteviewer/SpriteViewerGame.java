package de.fhtrier.gdig.tools.spriteviewer;

import javax.swing.BoundedRangeModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpriteViewerGame extends BasicGame {

	private float offset = 0.0f;

	public SpriteViewerGame(String title) {
		super(title);
	}
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setBackground(Main.backgroundColor);
		g.clear();
		AnimationDataContainer adc = (AnimationDataContainer)Main.controlPanel.animationChooser.getSelectedItem();
		Image image = null;
		if (adc != null) {
			image = adc.image;
		}
		Color c = new Color(Main.backgroundColor);
		c.r = 1.0f - c.r;
		c.g = 1.0f - c.g;
		c.b = 1.0f - c.b;
		g.setColor(c);
		// drawing Imagestrip
		if(image != null) {
			int maxScroll = image.getWidth() - gc.getWidth();
			Main.scrollBar.setEnabled(maxScroll > 0);
			BoundedRangeModel model = Main.scrollBar.getModel();
			int value = model.getValue();
			int maxValue = model.getMaximum();
			float scale = ((float)value)/((float)maxValue-10);
//			System.out.println(value);
//			System.out.println(maxValue);
//			System.out.println(scale);
			g.drawImage(image, -(maxScroll * scale), gc.getHeight() - image.getHeight());
			g.drawLine(0,gc.getHeight() - image.getHeight() - 1, gc.getWidth(), gc.getHeight() - image.getHeight() - 1);
			g.drawLine(0,0, 0,gc.getHeight());
			g.drawLine(gc.getWidth()-1,0, gc.getWidth()-1,gc.getHeight());
			
		} else {
			Main.scrollBar.setEnabled(false);
			g.drawLine(0, 0, gc.getWidth(), gc.getHeight());
		}
		
		// drawing Animation
		Animation a = null;
		if(adc != null) {
			a = ((AnimationDataContainer)Main.controlPanel.animationChooser.getSelectedItem()).animation;
		}
		if(a != null) {
			// drawing floor
			Color d = new Color(Main.backgroundColor);
			d.r = 1.0f - d.r/1.3f;
			d.g = 1.0f - d.g/1.4f;
			d.b = 1.0f - d.b/1.5f;
			g.setColor(d);
			g.fillRect(40.0f, gc.getHeight()/2.0f, gc.getWidth() - 80.0f, 10);
			
			// Animation zeichnen
			g.translate(gc.getWidth()/2.0f - a.getWidth()/2 + offset, gc.getHeight()/2.0f - a.getHeight());
			if(Main.controlPanel.flip.getModel().isSelected()) {
				g.scale(-1.0f, 1.0f);
				g.translate(-a.getWidth(), 0.0f);
			}
			a.draw(0, 0);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		AnimationDataContainer adc = (AnimationDataContainer)Main.controlPanel.animationChooser.getSelectedItem();
		Animation a = null;
		if(adc != null) {
			a = adc.animation;
		}
		if(a != null) {
			if(!Main.controlPanel.playPause.getModel().isSelected()) {
				int newDuration = ((Integer)Main.controlPanel.animationSpeed.getValue()).intValue();
				if(a.getDuration(0) != newDuration) {
					adc.animation = new Animation(adc.ss, newDuration);
					adc.animation.setAutoUpdate(false);
					a = adc.animation;
				}
				a.update(delta);
			}
		}
		
		// offset berechnen
		if(Main.controlPanel.movementPlayPause.isSelected()) {
			offset += ((Integer)Main.controlPanel.movementSpeed.getValue()) * delta/1000.0f;
			if (offset > gc.getWidth()/2.0f-40.0f
				|| offset < -gc.getWidth()/2.0f+40.0f) {
				offset *= -1.0f;
			}
		}
	}

}
