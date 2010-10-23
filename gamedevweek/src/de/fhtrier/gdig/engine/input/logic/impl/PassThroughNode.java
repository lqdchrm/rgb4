package de.fhtrier.gdig.engine.input.logic.impl;

import de.fhtrier.gdig.engine.input.logic.abs.UnaryNode;
import de.fhtrier.gdig.engine.input.logic.iface.INode;

public class PassThroughNode extends UnaryNode {

	public PassThroughNode(INode child) {
		super(child);
	}
	
	@Override
	public boolean eval() {
		if (getChild() != null) {
			return getChild().eval();
		}
		
		return false;
	}
}
