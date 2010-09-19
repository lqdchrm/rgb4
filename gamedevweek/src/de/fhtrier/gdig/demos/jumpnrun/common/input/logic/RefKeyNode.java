package de.fhtrier.gdig.demos.jumpnrun.common.input.logic;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameRefKeys;
import de.fhtrier.gdig.engine.input.logic.iface.IKeyNode;
import de.fhtrier.gdig.engine.input.logic.iface.INode;
import de.fhtrier.gdig.engine.input.logic.impl.PassThroughNode;

public class RefKeyNode extends PassThroughNode implements IKeyNode<GameRefKeys> {

	private GameRefKeys key;
	
	public RefKeyNode(GameRefKeys key, INode child) {
		super(child);
		this.key = key;
	}
	
	@Override
	public GameRefKeys getKey() {
		return key;
	}
}
