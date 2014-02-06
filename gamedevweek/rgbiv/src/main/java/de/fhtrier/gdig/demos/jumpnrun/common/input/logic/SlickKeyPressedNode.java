package de.fhtrier.gdig.demos.jumpnrun.common.input.logic;

import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;
import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.abs.SlickKeyNode;

public class SlickKeyPressedNode extends SlickKeyNode {

	protected boolean previous;
	protected boolean current;
	
	public SlickKeyPressedNode(Integer key) {
		super(key);
	}

	@Override
	public boolean eval() {
		return (!previous && current);
	}
	
	@Override
	public void update() {
		GameInputController input = GameInputController.getInstance();  
		
		if (input == null) {
			current = false;
		}

		previous = current;
		current = input.getInput().isKeyDown(getKey());
	}
}
