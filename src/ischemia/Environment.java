package ischemia;

public class Environment {
	private Frame frame;
	private Environment enclosingEnvironment;
	
	public Environment() {
		frame = new Frame();
		enclosingEnvironment = null;
	}
	
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
	
	public void defineVariable(SchemeObject variable, SchemeObject value) {
		frame.defineVariable(variable, value);
	}
}
