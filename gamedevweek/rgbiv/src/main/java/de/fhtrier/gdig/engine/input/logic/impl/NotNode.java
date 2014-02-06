package de.fhtrier.gdig.engine.input.logic.impl;

import de.fhtrier.gdig.engine.input.logic.abs.UnaryNode;
import de.fhtrier.gdig.engine.input.logic.iface.INode;

public class NotNode extends UnaryNode {

	public NotNode(INode child) {
		super(child);
	}
	
	@Override
	public boolean eval() {
		return !getChild().eval();
	}

}
