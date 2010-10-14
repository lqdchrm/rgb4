package de.fhtrier.gdig.demos.jumpnrun.common.input.logic;

import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;

public class SlickButtonDownNode extends SlickKeyDownNode {

	public SlickButtonDownNode(Integer key) {
		super(key);
	}

	@Override
	public boolean eval() {
		
		GameInputController input = GameInputController.getInstance();

		if (input == null) {
			return false;
		}

		boolean result = false;
		
		for (int cntrl = 0; cntrl < input.getInput().getControllerCount(); cntrl++) {

			switch (getKey()) {
			case 0:
				result |= input.getInput().isControllerLeft(cntrl);
				break;
			case 1:
				result |= input.getInput().isControllerUp(cntrl);
				break;
			case 2:
				result |= input.getInput().isControllerRight(cntrl);
				break;
			case 3:
				result |= input.getInput().isControllerDown(cntrl);
				break;
			default:
				try {
				result |= input.getInput().isButtonPressed(getKey() - 4, cntrl);
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
				break;
			}
		}
		
		return result;
	}	
}
