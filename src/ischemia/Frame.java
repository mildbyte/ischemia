package ischemia;

/**
 * A static class for manipulating frames.
 */
public class Frame {
	private Frame() {}
	
	/**
	 * Makes a frame from two lists of variables and values
	 */
	public static SchemeObject makeFrame(SchemeObject variables, SchemeObject values) {
		return new Pair(variables, values);
	}
	
	/**
	 * Looks up a variable in the frame, returns null if not found.
	 */
	public static SchemeObject lookupValue(SchemeObject frame, SchemeObject variable) {
		SchemeObject variables = ((Pair)frame).car();
		SchemeObject values = ((Pair)frame).cdr();
		
		while (!(variables instanceof EmptyList) && !(values instanceof EmptyList)) {
			if (variable.equals(((Pair)variables).car()))
				return ((Pair)values).car();
			
			variables = ((Pair)variables).cdr();
			values = ((Pair)values).cdr();
		}
		
		return null;
	}
	
	/**
	 * Sets the variable value even if it doesn't exist.
	 */
	public static void defineVariable(SchemeObject frame, SchemeObject variable, SchemeObject value) {
		SchemeObject variables = ((Pair)frame).car();
		SchemeObject values = ((Pair)frame).cdr();
		
		while (!(variables instanceof EmptyList) && !(values instanceof EmptyList)) {
			if (variable.equals(((Pair)variables).car())) {
				((Pair)values).setCar(value);
				return;
			}
			
			variables = ((Pair)variables).cdr();
			values = ((Pair)values).cdr();
		}	
		
		//Add the binding to the frame
		
		//Add the variable first
		((Pair)frame).setCar(new Pair(variable, ((Pair)frame).car()));
		
		//Add the value (goes into the car since we've shifted the frame
		((Pair)frame).setCdr(new Pair(value, ((Pair)frame).cdr()));
	}
	
	
	/**
	 * Sets the value of a variable if it exists, raises an exception otherwise
	 */
	public static void setValue(SchemeObject frame, SchemeObject variable, SchemeObject value) throws EvalException {
		SchemeObject variables = ((Pair)frame).car();
		SchemeObject values = ((Pair)frame).cdr();
		
		while (!(variables instanceof EmptyList) && !(values instanceof EmptyList)) {
			if (variable.equals(((Pair)variables).car())) {
				((Pair)values).setCar(value);
				return;
			}
			
			variables = ((Pair)variables).cdr();
			values = ((Pair)values).cdr();
		}	
		
		throw new EvalException("Error: Unbound variable " + variable.print());
	}
}
