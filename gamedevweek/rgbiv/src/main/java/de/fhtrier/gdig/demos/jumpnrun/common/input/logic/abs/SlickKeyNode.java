package de.fhtrier.gdig.demos.jumpnrun.common.input.logic.abs;

import de.fhtrier.gdig.engine.input.logic.iface.IKeyNode;


public abstract class SlickKeyNode implements IKeyNode<Integer> {

	private Integer key;

	public SlickKeyNode(Integer key) {
		this.key = key;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
	
	@Override
	public void update() {
	}
}
