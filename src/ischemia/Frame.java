package ischemia;

import java.util.HashMap;

public class Frame {
	private HashMap<SchemeObject, SchemeObject> mappings;
	public Frame() {
		mappings = new HashMap<>();
	}
	
	public SchemeObject lookupValue(SchemeObject variable) {
		return mappings.get(variable);
	}
	
	public void defineVariable(SchemeObject variable, SchemeObject value) {
		mappings.put(variable, value);
	}
	
	public void setValue(SchemeObject variable, SchemeObject value) throws EvalException {
		if (!mappings.containsKey(variable)) throw new EvalException("Unbound variable!");
		
		defineVariable(variable, value);
	}
}
