package de.fhtrier.gdig.engine.input.logic.impl;

import de.fhtrier.gdig.engine.input.logic.abs.BinaryNode;
import de.fhtrier.gdig.engine.input.logic.iface.INode;

public class OrNode extends BinaryNode {
		
	public OrNode(INode left, INode right) {
		super(left, right);
	}

	@Override
	public boolean eval() {
		return (getLeftChild().eval() || getRightChild().eval());
	}
}
