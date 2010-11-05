package de.fhtrier.gdig.demos.jumpnrun.common.input;

import org.newdawn.slick.Input;

import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.RefKeyNode;
import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.SlickButtonDownNode;
import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.SlickButtonPressedNode;
import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.SlickKeyDownNode;
import de.fhtrier.gdig.demos.jumpnrun.common.input.logic.SlickKeyPressedNode;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameInputCommands;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GamePadButtons;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameRefKeys;
import de.fhtrier.gdig.engine.input.InputController;
import de.fhtrier.gdig.engine.input.logic.iface.INode;
import de.fhtrier.gdig.engine.input.logic.impl.AndNode;
import de.fhtrier.gdig.engine.input.logic.impl.OrNode;

public class GameInputController extends InputController<GameInputCommands> {

	private static GameInputController instance = null;

	// Singelton stuff
	public static GameInputController getInstance() {
		if (instance == null) {
			throw new RuntimeException(
					"InputController Singleton not created yet. You might want to call init before.");
		}

		return instance;
	}

	public static void init(Input input) {
		if (instance != null) {
			throw new RuntimeException(
					"InputController Singleton already initialized");
		}

		instance = new GameInputController(input);
	}

	// static helpers
	public static class Helper {
		public static INode createSimpleKeyDownBinding(GameRefKeys refKey,
				Integer slickKey) {
			return new RefKeyNode(refKey, new SlickKeyDownNode(slickKey));
		}

		public static INode createSimpleKeyPressedBinding(GameRefKeys refKey,
				Integer slickKey) {
			return new RefKeyNode(refKey, new SlickKeyPressedNode(slickKey));
		}
		
		public static INode createSimpleButtonDownBinding(GameRefKeys refKey,
				Integer slickButton) {
			return new RefKeyNode(refKey, new SlickButtonDownNode(slickButton));
		}
		
		public static INode createSimpleButtonPressedBinding(GameRefKeys refKey,
				Integer slickButton) {
			return new RefKeyNode(refKey, new SlickButtonPressedNode(slickButton));
		}

		public static INode createOrCombination(INode left, INode right) {
			return new OrNode(left, right);
		}

		public static INode createAndCombination(INode left, INode right) {
			return new AndNode(left, right);
		}
	}

	// class stuff
	public GameInputController(Input input) {
		super(input);
	}

	public void initKeyboard() {

		INode walkLeft = Helper.createSimpleKeyDownBinding(GameRefKeys.D_LEFT, Input.KEY_LEFT);
		INode walkRight = Helper.createSimpleKeyDownBinding(GameRefKeys.D_RIGHT, Input.KEY_RIGHT);
		
		INode jumpWithUp = Helper.createSimpleKeyPressedBinding(GameRefKeys.D_UP, Input.KEY_UP);
		INode jumpWithA = Helper.createSimpleKeyPressedBinding(GameRefKeys.A, Input.KEY_A);
		INode jump = Helper.createOrCombination(jumpWithUp, jumpWithA);

		INode shoot = Helper.createSimpleKeyPressedBinding(GameRefKeys.B, Input.KEY_S);
		INode rocket = Helper.createSimpleKeyPressedBinding(GameRefKeys.START, Input.KEY_SPACE);
		
		INode ok = Helper.createSimpleKeyPressedBinding(GameRefKeys.X, Input.KEY_ENTER);
		INode back = Helper.createSimpleKeyPressedBinding(GameRefKeys.Y, Input.KEY_ESCAPE);
		
		INode changeColor = Helper.createSimpleKeyPressedBinding(GameRefKeys.R1, Input.KEY_D);
		INode changeWeaponColor = Helper.createSimpleKeyPressedBinding(GameRefKeys.R1, Input.KEY_D);
		
		INode phrase1 = Helper.createAndCombination(
				Helper.createSimpleKeyPressedBinding(GameRefKeys.R2, Input.KEY_1),
				Helper.createSimpleKeyPressedBinding(GameRefKeys.D_LEFT, Input.KEY_1));

		INode phrase2 = Helper.createAndCombination(
				Helper.createSimpleKeyPressedBinding(GameRefKeys.R2, Input.KEY_2),
				Helper.createSimpleKeyPressedBinding(GameRefKeys.D_UP, Input.KEY_2));
		
		INode phrase3 = Helper.createAndCombination(
				Helper.createSimpleKeyPressedBinding(GameRefKeys.R2, Input.KEY_3),
				Helper.createSimpleKeyPressedBinding(GameRefKeys.D_RIGHT, Input.KEY_3));
		
		INode phrase4 = Helper.createAndCombination(
				Helper.createSimpleKeyPressedBinding(GameRefKeys.R2, Input.KEY_4),
				Helper.createSimpleKeyPressedBinding(GameRefKeys.D_DOWN, Input.KEY_4));
		
		INode scrollLeft = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L1, Input.KEY_J),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_LEFT, Input.KEY_J));

		INode scrollUp = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L1, Input.KEY_I),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_UP, Input.KEY_I));
		
		INode scrollRight = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L1, Input.KEY_L),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_RIGHT, Input.KEY_L));
		
		INode scrollDown = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L1, Input.KEY_K),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_DOWN, Input.KEY_K));
		
		INode zoomIn = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L2, Input.KEY_COMMA),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_UP, Input.KEY_COMMA));

		INode zoomOut = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L2, Input.KEY_PERIOD),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_DOWN, Input.KEY_PERIOD));

		INode rotateLeft = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L2, Input.KEY_U),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_LEFT, Input.KEY_U));

		INode rotateRight = Helper.createAndCombination(
				Helper.createSimpleKeyDownBinding(GameRefKeys.L2, Input.KEY_O),
				Helper.createSimpleKeyDownBinding(GameRefKeys.D_RIGHT, Input.KEY_O));
		
		INode fullScreen = Helper.createSimpleKeyPressedBinding(GameRefKeys.SELECT, Input.KEY_F1);

		addConfigNode(GameInputCommands.WALKLEFT, walkLeft, false);
		addConfigNode(GameInputCommands.WALKRIGHT, walkRight, false);
		addConfigNode(GameInputCommands.JUMP, jump, true);
		addConfigNode(GameInputCommands.SHOOT, shoot, true);
		// TODO activate Rocket
		// addConfigNode(GameInputCommands.ROCKET, rocket, true);
		
		addConfigNode(GameInputCommands.OK, ok, true);
		addConfigNode(GameInputCommands.BACK, back, true);
		
		addConfigNode(GameInputCommands.CHANGECOLOR, changeColor, true);
		addConfigNode(GameInputCommands.CHANGEWEAPONCOLOR, changeWeaponColor, true);
		
		addConfigNode(GameInputCommands.PHRASE1, phrase1, true);
		addConfigNode(GameInputCommands.PHRASE2, phrase2, true);
		addConfigNode(GameInputCommands.PHRASE3, phrase3, true);
		addConfigNode(GameInputCommands.PHRASE4, phrase4, true);

		addConfigNode(GameInputCommands.SCROLLLEFT, scrollLeft, false);
		addConfigNode(GameInputCommands.SCROLLUP, scrollUp, false);
		addConfigNode(GameInputCommands.SCROLLRIGHT, scrollRight, false);
		addConfigNode(GameInputCommands.SCROLLDOWN, scrollDown, false);
		
		addConfigNode(GameInputCommands.ZOOMIN, zoomIn, false);
		addConfigNode(GameInputCommands.ZOOMOUT, zoomOut, false);
		
		addConfigNode(GameInputCommands.ROTATELEFT, rotateLeft, false);
		addConfigNode(GameInputCommands.ROTATERIGHT, rotateRight, false);

		addConfigNode(GameInputCommands.FULLSCREEN, fullScreen, true);
	}
	
	private void addConfigNode(GameInputCommands vKey, INode node, boolean pressed) {
		if (pressed) {
			INode old = this.getVKeyPressedBinding(vKey);
			if (old != null) {
				old = Helper.createOrCombination(old, node);
			} else {
				old = node;
			}
			bindVKey2PressedExpr(vKey, old);
		} else {
			INode old = this.getVKeyDownBinding(vKey);
			if (old != null) {
				old = Helper.createOrCombination(old, node);
			} else {
				old = node;
			}
			bindVKey2DownExpr(vKey, old);
		}
	}
	
	public void initGamePad() {

		INode walkLeft = Helper.createSimpleButtonDownBinding(GameRefKeys.D_LEFT, GamePadButtons.D_LEFT);
		INode walkRight = Helper.createSimpleButtonDownBinding(GameRefKeys.D_RIGHT, GamePadButtons.D_RIGHT);

		INode jumpWithUp = Helper.createSimpleButtonPressedBinding(GameRefKeys.D_UP, GamePadButtons.D_UP);
		INode jumpWithA = Helper.createSimpleButtonPressedBinding(GameRefKeys.A, GamePadButtons.BUTTON3);
		INode jump = Helper.createOrCombination(jumpWithUp, jumpWithA);

		INode shoot = Helper.createSimpleButtonPressedBinding(GameRefKeys.B, GamePadButtons.BUTTON4);
		INode rocket = Helper.createSimpleButtonPressedBinding(GameRefKeys.START, GamePadButtons.BUTTON12);
		
		INode ok = Helper.createSimpleButtonPressedBinding(GameRefKeys.X, GamePadButtons.BUTTON1);
		INode back = Helper.createSimpleButtonPressedBinding(GameRefKeys.Y, GamePadButtons.BUTTON2);
		
		INode changeColor = Helper.createSimpleButtonPressedBinding(GameRefKeys.R1, GamePadButtons.BUTTON6);
		INode changeWeaponColor = Helper.createSimpleButtonPressedBinding(GameRefKeys.R1, GamePadButtons.BUTTON6);

		INode phrase1 = Helper.createAndCombination(
				Helper.createSimpleButtonPressedBinding(GameRefKeys.R2, GamePadButtons.BUTTON8),
				Helper.createSimpleButtonPressedBinding(GameRefKeys.D_LEFT, GamePadButtons.D_LEFT));

		INode phrase2 = Helper.createAndCombination(
				Helper.createSimpleButtonPressedBinding(GameRefKeys.R2, GamePadButtons.BUTTON8),
				Helper.createSimpleButtonPressedBinding(GameRefKeys.D_UP, GamePadButtons.D_UP));
		
		INode phrase3 = Helper.createAndCombination(
				Helper.createSimpleButtonPressedBinding(GameRefKeys.R2, GamePadButtons.BUTTON8),
				Helper.createSimpleButtonPressedBinding(GameRefKeys.D_RIGHT, GamePadButtons.D_RIGHT));
		
		INode phrase4 = Helper.createAndCombination(
				Helper.createSimpleButtonPressedBinding(GameRefKeys.R2, GamePadButtons.BUTTON8),
				Helper.createSimpleButtonPressedBinding(GameRefKeys.D_DOWN, GamePadButtons.D_DOWN));
		
		INode scrollLeft = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L1, GamePadButtons.BUTTON5),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_LEFT, GamePadButtons.D_LEFT));

		INode scrollUp = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L1, GamePadButtons.BUTTON5),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_UP, GamePadButtons.D_UP));
		
		INode scrollRight = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L1, GamePadButtons.BUTTON5),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_RIGHT, GamePadButtons.D_RIGHT));
		
		INode scrollDown = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L1, GamePadButtons.BUTTON5),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_DOWN, GamePadButtons.D_DOWN));
		
		INode zoomIn = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L2, GamePadButtons.BUTTON7),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_UP, GamePadButtons.D_UP));

		INode zoomOut = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L2, GamePadButtons.BUTTON7),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_DOWN, GamePadButtons.D_DOWN));

		INode rotateLeft = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L2, GamePadButtons.BUTTON7),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_LEFT, GamePadButtons.D_LEFT));

		INode rotateRight = Helper.createAndCombination(
				Helper.createSimpleButtonDownBinding(GameRefKeys.L2, GamePadButtons.BUTTON7),
				Helper.createSimpleButtonDownBinding(GameRefKeys.D_RIGHT, GamePadButtons.D_RIGHT));

		INode fullScreen = Helper.createSimpleButtonPressedBinding(GameRefKeys.SELECT, GamePadButtons.BUTTON10);

		addConfigNode(GameInputCommands.WALKLEFT, walkLeft, false);
		addConfigNode(GameInputCommands.WALKRIGHT, walkRight, false);
		addConfigNode(GameInputCommands.JUMP, jump, true);
		addConfigNode(GameInputCommands.SHOOT, shoot, true);
		addConfigNode(GameInputCommands.ROCKET, rocket, true);
		addConfigNode(GameInputCommands.OK, ok, true);
		addConfigNode(GameInputCommands.BACK, back, true);
		addConfigNode(GameInputCommands.CHANGECOLOR, changeColor, true);
		addConfigNode(GameInputCommands.CHANGEWEAPONCOLOR, changeWeaponColor, true);
		addConfigNode(GameInputCommands.PHRASE1, phrase1, true);
		addConfigNode(GameInputCommands.PHRASE2, phrase2, true);
		addConfigNode(GameInputCommands.PHRASE3, phrase3, true);
		addConfigNode(GameInputCommands.PHRASE4, phrase4, true);

		addConfigNode(GameInputCommands.SCROLLLEFT, scrollLeft, false);
		addConfigNode(GameInputCommands.SCROLLUP, scrollUp, false);
		addConfigNode(GameInputCommands.SCROLLRIGHT, scrollRight, false);
		addConfigNode(GameInputCommands.SCROLLDOWN, scrollDown, false);
		
		addConfigNode(GameInputCommands.ZOOMIN, zoomIn, false);
		addConfigNode(GameInputCommands.ZOOMOUT, zoomOut, false);
		
		addConfigNode(GameInputCommands.ROTATELEFT, rotateLeft, false);
		addConfigNode(GameInputCommands.ROTATERIGHT, rotateRight, false);

		addConfigNode(GameInputCommands.FULLSCREEN, fullScreen, true);
	}
}
