package de.fhtrier.gdig.tools.spriteviewer;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

public class ControlPanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2627114143053649540L;
	public JComboBox animationChooser;
	public JToggleButton flip;
//	public JToggleButton 
	public JSpinner animationSpeed;
	public JSpinner movementSpeed;
	public JToggleButton movementPlayPause;
	public JToggleButton playPause;

	public ControlPanel() {
		flip = new JToggleButton("Horizontal spiegeln");
		animationChooser = new JComboBox();
		playPause = new JToggleButton(new ImageIcon(ClassLoader.getSystemResource("de/fhtrier/gdig/tools/spriteviewer/icons/pause.png")));
		movementPlayPause = new JToggleButton(new ImageIcon(ClassLoader.getSystemResource("de/fhtrier/gdig/tools/spriteviewer/icons/play.png")));
		animationSpeed = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 1));
		movementSpeed = new JSpinner(new SpinnerNumberModel(100, -10000, 10000, 1));

		JLabel animationLabel = new JLabel("Animation:");
		JLabel animationSpeedLabel = new JLabel("Animationsdelay:");
		JLabel movementSpeedLabel = new JLabel("Bewegungsgeschwindigkeit:");
		
		GroupLayout gl = new GroupLayout(this);
		gl.setHorizontalGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false)
				.addGroup(gl.createSequentialGroup().addComponent(flip))
				.addGroup(gl.createSequentialGroup().addComponent(animationLabel))				
				.addGroup(gl.createSequentialGroup().addComponent(animationChooser))
				.addGroup(gl.createSequentialGroup().addComponent(animationSpeedLabel))				
				.addGroup(gl.createSequentialGroup().addComponent(animationSpeed).addComponent(playPause))
				.addGroup(gl.createSequentialGroup().addComponent(movementSpeedLabel))				
				.addGroup(gl.createSequentialGroup().addComponent(movementSpeed).addComponent(movementPlayPause)));
		
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false).addComponent(flip))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(animationLabel))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false).addComponent(animationChooser))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(animationSpeedLabel))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false).addComponent(animationSpeed).addComponent(playPause))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(movementSpeedLabel))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE, false).addComponent(movementSpeed).addComponent(movementPlayPause)));
		
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
				setLayout(gl);
				add(flip);
				add(animationChooser);
				add(playPause);
				add(animationSpeed);
	}
}
