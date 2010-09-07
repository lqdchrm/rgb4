package de.fhtrier.gdig.tools.spriteviewer;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;


public class FileOpenAction extends AbstractAction {

	public FileOpenAction() {
		super("Öffnen");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6217161278761491572L;

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		int result = fc.showOpenDialog(null);
		
		if (result != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		
		File[] files = fc.getSelectedFiles();
		
		for (File file : files) {
			if (!file.isDirectory())
			{
				
				AnimationDataContainer adc = new AnimationDataContainer(file);
				Main.controlPanel.animationChooser.addItem(adc);
				
			}
		}
	}
}
