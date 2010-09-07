package de.fhtrier.gdig.tools.spriteviewer;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.WindowConstants;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public class Main {

	
	public static Color backgroundColor = Color.lightGray;
	public static ControlPanel controlPanel;
	public static JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("SpriteViewer");
		JPanel viewPanel = new JPanel(new BorderLayout());
		scrollBar.setEnabled(false);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setJMenuBar(new MenuBar());
		SpriteViewerGame game = new SpriteViewerGame("Sprite Viewer");
		CanvasGameContainer gc = null;
		controlPanel = new ControlPanel();
		mainFrame.add(controlPanel,BorderLayout.WEST);
		viewPanel.add(scrollBar,BorderLayout.SOUTH);
		mainFrame.add(viewPanel);

		try {
			gc = new CanvasGameContainer(game);
			gc.getContainer().setAlwaysRender(true);
			mainFrame.addWindowListener(new WindowExitListener());
			viewPanel.add(gc);
			mainFrame.setSize(800, 600);
			mainFrame.setVisible(true);
			mainFrame.setLocationRelativeTo(null);
			gc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

}
