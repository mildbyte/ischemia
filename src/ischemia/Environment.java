package ischemia;

public class Environment {
	private Frame frame;
	private Environment enclosingEnvironment;
	
	public Environment() {
		frame = new Frame();
		enclosingEnvironment = null;
	}
	
	
	/**
	 * Looks up the value of a variable. If not found, tries the environment
	 * above it. Throws an exception if lookup fails completely.
	 */
	public SchemeObject lookupValue(SchemeObject variable) throws EvalException {
		SchemeObject value = frame.lookupValue(variable);
		if (value == null) {
			if (enclosingEnvironment == null) {
				throw new EvalException("Unbound variable!");
			}
			
			return enclosingEnvironment.lookupValue(variable);
		}
		
		return value;
	}
	
	
	/**
	 * Sets the value of a variable in this environment, tries the enclosing environment
	 * if it fails. Throws an exception if the variable isn't anywhere.
	 */
	public void setVariableValue(SchemeObject variable, SchemeObject value) throws EvalException {
		try {
			frame.setValue(variable, value);
		} catch (EvalException e) {
			if (enclosingEnvironment == null) {
				throw e;
			}
			
			enclosingEnvironment.setVariableValue(variable, value);
		}
	}
	
	
	/**
	 * Defines the variable in this frame no matter whether it exists or not.
	 */
	public void defineVariable(SchemeObject variable, SchemeObject value) {
		frame.defineVariable(variable, value);
	}
}
