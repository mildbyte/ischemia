package ischemia;

public abstract class SchemeObject {
	
	/**
	 * 
	 * Evaluate the object in the current environment
	 * 
	 */
	public abstract SchemeObject eval(Environment environment) throws EvalException;
	
	/**
	 * Print the object
	 */
	public abstract String print();
}
