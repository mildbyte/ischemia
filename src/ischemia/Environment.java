package ischemia;

public class Environment extends SchemeObject {
	private Frame frame;
	private Environment enclosingEnvironment;
	
	private static Environment globalEnvironment;
	
	public Environment() {
		frame = new Frame();
		enclosingEnvironment = null;
	}
	
	public Environment(Environment enclosingEnvironment, Frame frame) {
		this.frame = frame;
		this.enclosingEnvironment = enclosingEnvironment;
	}
	
	public Environment(Environment enclosingEnvironment) {
		frame = new Frame();
		this.enclosingEnvironment = enclosingEnvironment;
	}
	
	/**
	 * Returns the environment with only primitive procedures defined.
	 */
	public static Environment getInitialEnvironment() {
		Environment env = new Environment();
		PrimitiveProcedures.installProcedures(env);
		
		return env;
	}
	
	/**
	 * A singleton, returns the top-level environment of the REPL.
	 */
	public static Environment getGlobalEnvironment() {
		if (globalEnvironment == null) {
			globalEnvironment = getInitialEnvironment();
		}
		
		return globalEnvironment;
	}
	
	/**
	 * Looks up the value of a variable. If not found, tries the environment
	 * above it. Throws an exception if lookup fails completely.
	 */
	public SchemeObject lookupValue(SchemeObject variable) throws EvalException {
		SchemeObject value = frame.lookupValue(variable);
		if (value == null) {
			if (enclosingEnvironment == null) {
				throw new EvalException("Error: Unbound variable " + variable.print());
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

	protected EvaluationResult eval(Environment environment)
			throws EvalException {
		throw new EvalException("Cannot evaluate an environment!");
	}
	
	/**
	 * Really should print the contents of the current environment and all
	 * enclosing as a LISP list, but it is stored as a HashMap here..
	 */
	public String print() {
		return frame.printAll();
	}
}
