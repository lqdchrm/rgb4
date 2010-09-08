package de.fhtrier.gdig.engine.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.rgb4.identifiers.Constants;

public class FiniteStateMachine<STATETYPE, INPUTTYPE> {

	private STATETYPE initialState;
	private STATETYPE currentState;
	
	private HashMap<STATETYPE, HashMap<INPUTTYPE, STATETYPE>> transitions;
	
	private List<IFiniteStateMachineListener<STATETYPE>> listeners;
	
	public FiniteStateMachine(STATETYPE initialState) {
		this.currentState = this.initialState = initialState;
		transitions = new HashMap<STATETYPE, HashMap<INPUTTYPE,STATETYPE>>();
		listeners = new ArrayList<IFiniteStateMachineListener<STATETYPE>>();
	}
	
	public void add(STATETYPE oldState, INPUTTYPE input, STATETYPE newState) {
		if (!transitions.containsKey(oldState)) {
			transitions.put(oldState, new HashMap<INPUTTYPE, STATETYPE>());
		}
		
		HashMap<INPUTTYPE, STATETYPE> lookup = transitions.get(oldState);
		lookup.put(input, newState);
	}
	
	public void apply(INPUTTYPE input) {
		if (transitions.containsKey(currentState)) {
			HashMap<INPUTTYPE, STATETYPE> lookup = transitions.get(currentState);
			if (lookup.containsKey(input)) {
				
				// leaving
				if (Constants.Debug.finiteStateMachineDebug) {
					Log.debug("leaving state: " + currentState.toString());
				}

				for (IFiniteStateMachineListener<STATETYPE> l : listeners) {
					l.leavingState(currentState);
				}
				
				// switch
				currentState = lookup.get(input);
				
				// entering
				if (Constants.Debug.finiteStateMachineDebug) {
					Log.debug("entering state: " + currentState.toString());
				}

				for (IFiniteStateMachineListener<STATETYPE> l : listeners) {
					l.enteringState(currentState);
				}
			}
		}
	}
	
	public void reset() {
		currentState = initialState;
	}
	
	public STATETYPE getCurrentState() {
		return currentState;
	}
	
	// Listeners
	public void add(IFiniteStateMachineListener<STATETYPE> listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void remove(IFiniteStateMachineListener<STATETYPE> listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
}
