package de.fhtrier.gdig.engine.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Input;

import de.fhtrier.gdig.engine.input.logic.iface.INode;

public abstract class InputController<VKEYTYPE> implements
		IInputController<VKEYTYPE> {

	private HashMap<VKEYTYPE, INode> vKeysPressed2Expr;
	private HashMap<VKEYTYPE, INode> vKeysDown2Expr;
	
	private List<INode> updateNodes;
	
	private Input input;
	
	public InputController(Input input) {
		vKeysPressed2Expr = new HashMap<VKEYTYPE, INode>(); 
		vKeysDown2Expr = new HashMap<VKEYTYPE, INode>();
		
		updateNodes = new ArrayList<INode>();
		
		this.input = input;
	}
	
	@Override
	public boolean isKeyPressed(VKEYTYPE vKey) {
		
		if (vKeysPressed2Expr.containsKey(vKey)) {

			INode expr = vKeysPressed2Expr.get(vKey);
			
			if (expr != null) {
				return expr.eval();
			}
		}
		
		return false;
	}

	@Override
	public boolean isKeyDown(VKEYTYPE vKey) {
		if (vKeysDown2Expr.containsKey(vKey)) {

			INode expr = vKeysDown2Expr.get(vKey);
			
			if (expr != null) {
				return expr.eval();
			}
		}
		
		return false;
	}
	
	@Override
	public void update() {
		for (INode node : updateNodes) {
			node.update();
		}
	}

	private void addUpdateNode(INode expr) {
		if (!updateNodes.contains(expr)) {
			updateNodes.add(expr);
		}
	}
	
	private void removeUpdateNode(INode expr) {
		if (updateNodes.contains(expr)) {
			updateNodes.remove(expr);
		}
	}
	
	@Override
	public void bindVKey2PressedExpr(VKEYTYPE vKey, INode expr) {
		
		// check if already value in map
		if (vKeysPressed2Expr.containsKey(vKey)) {
			INode oldExpr = vKeysPressed2Expr.get(vKey);
			removeUpdateNode(oldExpr);
		}
		
		addUpdateNode(expr);
		vKeysPressed2Expr.put(vKey, expr);
	}

	@Override
	public void bindVKey2DownExpr(VKEYTYPE vKey, INode expr) {
		vKeysDown2Expr.put(vKey, expr);
	}

	@Override
	public INode getVKeyDownBinding(VKEYTYPE vKey) {
		if (vKeysDown2Expr.containsKey(vKey)) {
			return vKeysDown2Expr.get(vKey);
		}
		
		return null;
	}

	@Override
	public INode getVKeyPressedBinding(VKEYTYPE vKey) {
		if (vKeysPressed2Expr.containsKey(vKey)) {
			return vKeysPressed2Expr.get(vKey);
		}
		
		return null;
	}
	
	@Override
	public Input getInput() {
		return input;
	}
}
