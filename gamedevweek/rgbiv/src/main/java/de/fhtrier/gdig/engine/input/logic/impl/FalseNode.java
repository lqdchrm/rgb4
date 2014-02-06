package de.fhtrier.gdig.engine.input.logic.impl;

import de.fhtrier.gdig.engine.input.logic.iface.INode;

public class FalseNode implements INode {

	@Override
	public boolean eval() {
		return false;
	}
	
	@Override
	public void update() {
	}
}
