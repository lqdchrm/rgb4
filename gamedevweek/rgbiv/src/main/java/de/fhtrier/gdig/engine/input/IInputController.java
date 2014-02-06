package de.fhtrier.gdig.engine.input;

import org.newdawn.slick.Input;

import de.fhtrier.gdig.engine.input.logic.iface.INode;

/**
 * This class serves as abstraction input layer.<p>
 * 
 * You can register virtual keys aka game commands and attach
 * an expression-tree which evaluates to a boolean value.
 * 
 * @author roessgro
 *
 */
public interface IInputController<VKEYTYPE> {
	
	public boolean isKeyPressed(VKEYTYPE vKey);
	public boolean isKeyDown(VKEYTYPE vKey);
	
	public void bindVKey2PressedExpr(VKEYTYPE vKey, INode expr);
	public void bindVKey2DownExpr(VKEYTYPE vKey, INode expr);
	
	public INode getVKeyPressedBinding(VKEYTYPE vKey);
	public INode getVKeyDownBinding(VKEYTYPE vKey);
	
	public Input getInput();
	
	public void update();
}
