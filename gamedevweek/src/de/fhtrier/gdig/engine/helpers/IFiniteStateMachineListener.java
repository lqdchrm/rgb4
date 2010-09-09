package de.fhtrier.gdig.engine.helpers;

public interface IFiniteStateMachineListener<STATETYPE> {
	public void leavingState(STATETYPE state);
	public void enteringState(STATETYPE state);
}
