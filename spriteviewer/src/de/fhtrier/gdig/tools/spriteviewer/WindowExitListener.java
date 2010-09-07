package de.fhtrier.gdig.tools.spriteviewer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.lwjgl.opengl.Display;

public class WindowExitListener extends WindowAdapter {
	
	@Override
	public void windowClosing(WindowEvent e) {
		Display.destroy();
		super.windowClosing(e);
	}
}
