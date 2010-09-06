package de.fhtrier.gdig.tools.spriteviewer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5881269168664030654L;

	public MenuBar() {
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem openFile = new JMenuItem(new FileOpenAction());
//		JMenuItem quit = new JMenuItem(new QuitAction());
		fileMenu.add(openFile);
//		fileMenu.add(quit);
		add(fileMenu);
	}
}
