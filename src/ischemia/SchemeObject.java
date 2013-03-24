package ischemia;

public abstract class SchemeObject {
	
	/**
	 * Evaluate the object in the current environment, overridden by children.
	 */
	protected abstract EvaluationResult eval(Environment environment) throws EvalException;
	
	/**
	 * Evaluate the object until it no longer needs to be evaluated
	 * (tail-call optimization)
	 * (oh crap, I've just invented a trampoline)
	 */
	public final SchemeObject evaluate(Environment environment) throws EvalException {
		EvaluationResult evalResult = this.eval(environment);
		while (!evalResult.isFinished()) {
			evalResult = evalResult.getResult().eval(evalResult.getEnvironment());
		}
		
		return evalResult.getResult();
	}
	
	/**
	 * Print the object
	 */
	public abstract String print();

	public boolean equals(SchemeObject s) {return (s == this);}
}
