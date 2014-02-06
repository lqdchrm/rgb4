package de.fhtrier.gdig.engine.input.logic.impl;

import de.fhtrier.gdig.engine.input.logic.abs.BinaryNode;
import de.fhtrier.gdig.engine.input.logic.iface.INode;

public class AndNode extends BinaryNode {

	public AndNode(INode left, INode right) {
		super(left, right);
	}
	
	@Override
	public boolean eval() {
		return (this.getLeftChild().eval() && this.getRightChild().eval());
	}
}
