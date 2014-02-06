package de.fhtrier.gdig.demos.jumpnrun.common.input.logic;

import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;
import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.abs.SlickKeyNode;

public class SlickKeyDownNode extends SlickKeyNode {

	public SlickKeyDownNode(Integer key) {
		super(key);
	}

	@Override
	public boolean eval() {
		
		GameInputController input = GameInputController.getInstance();  
		
		if (input == null) {
			return false;
		}

		return input.getInput().isKeyDown(getKey());
	}
}
