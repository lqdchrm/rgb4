package de.fhtrier.gdig.engine.input.logic.abs;

import de.fhtrier.gdig.engine.input.logic.iface.IBinaryNode;
import de.fhtrier.gdig.engine.input.logic.iface.INode;


public abstract class BinaryNode implements IBinaryNode {

	private INode left;
	private INode right;
		
	public BinaryNode() {
		left = null;
		right = null;
	}
	
	public BinaryNode(INode left, INode right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public void update() {
		if (left != null) {
			left.update();
		}
		
		if (right!= null) {
			right.update();
		}
	}
	
	@Override
	public INode getLeftChild() {
		return left;
	}

	@Override
	public INode getRightChild() {
		return right;
	}

}
