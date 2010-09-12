package de.fhtrier.gdig.demos.jumpnrun.client.input;

import java.lang.reflect.Field;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.Input;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;

public class InputControl {

	// has to be adapted every time a new command is added or removed
	private static final int NUMOFREFKEYS = 12;
		
	// available commands which can be mapped to a key (currently just one)
	public static final int REFWALKLEFT = 0;
	public static final int REFWALKRIGHT = 1;
	public static final int REFJUMP = 2;
	public static final int REFFIRE = 3;
	public static final int REFFIRE2 = 11;
	public static final int REFCHANGEWEAPON= 4;
	public static final int REFCHANGECOLOR= 5;
	public static final int REFMENU= 6;
	public static final int REFPHRASE1 = 7;
	public static final int REFPHRASE2 = 8;
	public static final int REFPHRASE3 = 9;
	public static final int REFPHRASE4 = 10;
	
	// Input Types
	public static final int KEYBOARD = 0;
	public static final int MOUSE = 1;
	public static final int CONTROLLER = 2;
	
	// command states
	private static boolean[] REFKEYDOWN = new boolean[NUMOFREFKEYS];
	private static boolean[] REFKEYPRESSED = new boolean[NUMOFREFKEYS];
	
	// key mapping
	private static int[] REFCONTROLMAPPING2KEY = new int[NUMOFREFKEYS];
	private static int[] REFCONTROLMAPPING2KEYTYPE = new int[NUMOFREFKEYS];
	
	// original Input from slick
	private static Input CURRENTINPUT;
	
	public static Input getOriginalInput() {
		return CURRENTINPUT;
	}
	
	public static void updateInputControl(Input input) {
		CURRENTINPUT = input;
		
		for (int i=0; i < NUMOFREFKEYS; i++) {
			if (REFCONTROLMAPPING2KEYTYPE[i] == KEYBOARD) {
				REFKEYDOWN[i] = input.isKeyDown(REFCONTROLMAPPING2KEY[i]);
				REFKEYPRESSED[i] = input.isKeyPressed(REFCONTROLMAPPING2KEY[i]);
			} else if (REFCONTROLMAPPING2KEYTYPE[i] == MOUSE) {
				REFKEYDOWN[i] = input.isMouseButtonDown(REFCONTROLMAPPING2KEY[i]);
				REFKEYPRESSED[i] = input.isMousePressed(REFCONTROLMAPPING2KEY[i]);
			} else if (REFCONTROLMAPPING2KEYTYPE[i] == CONTROLLER) {
				
				boolean pressed = false;
				boolean down = false;
				for (int j=0; j < Controllers.getControllerCount(); j++) {	
					 try {
						 pressed = pressed || input.isControlPressed(REFCONTROLMAPPING2KEY[i], j);
						 down = down || isControllerDown(REFCONTROLMAPPING2KEY[i], j);
					 } catch (Exception e) {
						 
					 }
				}
				
				REFKEYDOWN[i] = down;
				REFKEYPRESSED[i] = pressed;
			}
			
		}
	}
	
	public static boolean isRefKeyDown(int key) {
		if (key < 0 || key > NUMOFREFKEYS-1) {
			// Throw Exception?
		}
		
		return REFKEYDOWN[key];
	}
	
	public static boolean isRefKeyPressed(int key) {
		if (key < 0 || key > NUMOFREFKEYS-1) {
			// Throw Exception?
		}
		
		return REFKEYPRESSED[key];
	}
	
	public static void setKeyMapping (String refKey, int key, int keyType) {
		try {
			int refKeyIndex = (Integer) InputControl.class.getDeclaredField(refKey).get(Input.class);
			
			REFCONTROLMAPPING2KEY[refKeyIndex] = key;
			REFCONTROLMAPPING2KEYTYPE[refKeyIndex] = keyType;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadKeyMapping ()  {
				
		try {
			setStoredKeyMapping("REFWALKLEFT");
			setStoredKeyMapping("REFWALKRIGHT");
			setStoredKeyMapping("REFJUMP");
			setStoredKeyMapping("REFFIRE");
			setStoredKeyMapping("REFFIRE2");
			setStoredKeyMapping("REFCHANGEWEAPON");
			setStoredKeyMapping("REFCHANGECOLOR");
			setStoredKeyMapping("REFMENU");
			setStoredKeyMapping("REFPHRASE1");
			setStoredKeyMapping("REFPHRASE2");
			setStoredKeyMapping("REFPHRASE3");
			setStoredKeyMapping("REFPHRASE4");
			setStoredKeyMapping("REFFIRE2");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setStoredKeyMapping(String refKey) {
		
		try {
			String keyIndexName = (String) Constants.ControlConfig.class.getDeclaredField(refKey).get(Constants.ControlConfig.class);
			Field declaredField = Input.class.getDeclaredField(keyIndexName);
			declaredField.setAccessible(true);
			int storedKeyIndex = (Integer) declaredField.get(Input.class);
			int refKeyIndex = (Integer) InputControl.class.getDeclaredField(refKey).get(InputControl.class);
			
			REFCONTROLMAPPING2KEY[refKeyIndex] = storedKeyIndex;
			REFCONTROLMAPPING2KEYTYPE[refKeyIndex] = getInputType(keyIndexName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getInputType (String key) {
		if (key.startsWith("KEY_")) {
			return KEYBOARD;
		} else if (key.startsWith("MOUSE_")) {
			return MOUSE;
		} else {
			return CONTROLLER;
		}
	}
	
	private static boolean isControllerDown (int index, int controllerIndex) {
		switch (index) {
		case 0:
			return CURRENTINPUT.isControllerLeft(controllerIndex);
		case 3:
			return CURRENTINPUT.isControllerRight(controllerIndex);
		case 2:
			return CURRENTINPUT.isControllerUp(controllerIndex);
		case 1:
			return CURRENTINPUT.isControllerDown(controllerIndex);
		}
		
		if (index >= 4) {
			return CURRENTINPUT.isButtonPressed((index-4), controllerIndex);
		}
		
		return false;
	}
}

