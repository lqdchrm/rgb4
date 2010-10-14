package de.fhtrier.gdig.engine.input.logic.impl;

import de.fhtrier.gdig.engine.input.logic.iface.INode;

public class TrueNode implements INode {

	@Override
	public boolean eval() {
		return true;
	}

	@Override
	public void update() {
	}
}
