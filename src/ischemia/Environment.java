package ischemia;

/**
 * A static class to manipulate environments represented as nested lists.
 */
public class Environment {
	private static SchemeObject globalEnvironment;
	
	private Environment() {}
	
	/**
	 * Makes a new environment enclosing the old one and with added bindings.
	 */
	public static SchemeObject extendEnvironment(SchemeObject variables, SchemeObject values, SchemeObject baseEnv) {
		return new Pair(Frame.makeFrame(variables, values), baseEnv);
	}
	
	/**
	 * Returns the environment with only primitive procedures defined.
	 */
	public static SchemeObject getInitialEnvironment() {
		SchemeObject env = EmptyList.makeEmptyList();
		PrimitiveProcedures.installProcedures(env);
		
		return env;
	}
	
	/**
	 * Returns the top-level environment of the REPL.
	 */
	public static SchemeObject getGlobalEnvironment() {
		if (globalEnvironment == null) {
			globalEnvironment = getInitialEnvironment();
		}
		
		return globalEnvironment;
	}
	
	/**
	 * Looks up the value of a variable. If not found, tries the environment
	 * above it. Throws an exception if lookup fails completely.
	 */
	public static SchemeObject lookupValue(SchemeObject environment, SchemeObject variable) throws EvalException {
		SchemeObject value = Frame.lookupValue(((Pair)environment).car(), variable);
		
		SchemeObject enclosingEnvironment = ((Pair)environment).cdr();
		
		if (value == null) {
			if (enclosingEnvironment instanceof EmptyList) {
				throw new EvalException("Error: Unbound variable " + variable.print());
			}
			
			return lookupValue(enclosingEnvironment, variable);
		}
		
		return value;
	}
	
	/**
	 * Sets the value of a variable in this environment, tries the enclosing environment
	 * if it fails. Throws an exception if the variable isn't anywhere.
	 */
	public static void setVariableValue(SchemeObject environment, 
			SchemeObject variable, SchemeObject value) throws EvalException {
		if (environment instanceof EmptyList)
			throw new EvalException("Error: unbound variable " + variable.print());
		
		try {
			Frame.setValue(((Pair)environment).car(), variable, value);
		} catch (EvalException e) {
			setVariableValue(((Pair)environment).cdr(), variable, value);
		}
	}
	
	/**
	 * Defines the variable in this frame no matter whether it exists or not.
	 */
	public static void defineVariable(SchemeObject environment, SchemeObject variable, SchemeObject value) {
		Frame.defineVariable(((Pair)environment).car(), variable, value);
	}
}
