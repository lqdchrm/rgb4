package de.fhtrier.gdig.engine.input.logic.abs;

import de.fhtrier.gdig.engine.input.logic.iface.INode;
import de.fhtrier.gdig.engine.input.logic.iface.IUnaryNode;


public abstract class UnaryNode implements IUnaryNode {

	private INode child;
	
	public UnaryNode() {
		child = null;
	}
	
	public UnaryNode(INode child) {
		this.child = child;
	}

	@Override
	public void update() {
		if (child != null) {
			child.update();
		}
	}
	
	@Override
	public INode getChild() {
		return child;
	}

}
