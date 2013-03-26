package ischemia;

import java.util.HashMap;

//Maps variables to their values
public class Frame {
	private HashMap<SchemeObject, SchemeObject> mappings;
	public Frame() {
		mappings = new HashMap<>();
	}
	
	
	/**
	 * Makes a frame from two lists of variables and values
	 */
	public Frame(SchemeObject variables, SchemeObject values) {
		mappings = new HashMap<>();
		while (!(variables instanceof EmptyList) && !(values instanceof EmptyList)) {
			mappings.put(((Pair)variables).car(), ((Pair)values).car());
			
			variables = ((Pair)variables).cdr();
			values = ((Pair)values).cdr();
		}
	}
	
	/**
	 * Looks up the variable in the table, returns null if not found.
	 */
	public SchemeObject lookupValue(SchemeObject variable) {
		return mappings.get(variable);
	}
	
	/**
	 * Sets the variable value even if it doesn't exist.
	 */
	public void defineVariable(SchemeObject variable, SchemeObject value) {
		mappings.put(variable, value);
	}
	
	
	/**
	 * Sets the value of a variable if it exists, raises an exception otherwise
	 */
	public void setValue(SchemeObject variable, SchemeObject value) throws EvalException {
		if (!mappings.containsKey(variable)) throw new EvalException("Error: Unbound variable " + variable.print());
		
		defineVariable(variable, value);
	}
	
	/**
	 * Prints all variables bound in the frame
	 * @return
	 */
	public String printAll() {
		String result = "";
		for (SchemeObject variable : mappings.keySet()) {
			result += variable.print() + " ";
		}
		
		return result;
	}
}
