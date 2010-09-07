package de.fhtrier.gdig.demos.jumpnrun.identifiers;

import org.newdawn.slick.Color;

public class StateColor {
	public static final int BLACK = 0;
	public static final int RED = 1;
	public static final int GREEN = RED << 1;
	public static final int YELLOW = RED | GREEN;
	public static final int BLUE = RED << 2;
	public static final int MAGENTA = RED | BLUE;
	public static final int CYAN = GREEN | BLUE;
	public static final int WHITE = RED | GREEN | BLUE;

	// Translates constant of color into Color-class
	public static Color constIntoColor(int colorConst)
			throws IllegalArgumentException {
		switch (colorConst) {
		case BLACK:
			return Color.black;
		case RED:
			return Color.red;
		case GREEN:
			return Color.green;
		case YELLOW:
			return Color.yellow;
		case BLUE:
			return Color.blue;
		case MAGENTA:
			return Color.magenta;
		case CYAN:
			return Color.cyan;
		case WHITE:
			return Color.white;
		default:
			throw new IllegalArgumentException("Undefined parameter");
		}
	}
}