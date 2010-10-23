package de.fhtrier.gdig.demos.jumpnrun.common.input.logic;

import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;

public class SlickButtonPressedNode extends SlickKeyPressedNode {

	public SlickButtonPressedNode(Integer key) {
		super(key);
	}

	@Override
	public void update() {
		GameInputController input = GameInputController.getInstance();

		if (input == null) {
			current = false;
		}

		previous = current;

		for (int cntrl = 0; cntrl < input.getInput().getControllerCount(); cntrl++) {

			switch (getKey()) {
			case 0:
				current = input.getInput().isControllerLeft(cntrl);
				break;
			case 1:
				current = input.getInput().isControllerUp(cntrl);
				break;
			case 2:
				current = input.getInput().isControllerRight(cntrl);
				break;
			case 3:
				current = input.getInput().isControllerDown(cntrl);
				break;
			default:
				try {
				current = input.getInput().isButtonPressed(getKey() - 4, cntrl);
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}
	}
}
