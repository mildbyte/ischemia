package ischemia;

public abstract class SchemeObject {
	
	/**
	 * Evaluate the object in the current environment, overridden by children.
	 */
	protected abstract EvaluationResult eval(Environment environment) throws EvalException;
	
	/**
	 * Evaluate the object until it no longer needs to be evaluated
	 * (tail-call optimization)
	 */
	public final SchemeObject evaluate(Environment environment) throws EvalException {
		EvaluationResult evalResult = this.eval(environment);
		while (!evalResult.isFinished()) {
			evalResult = evalResult.getResult().eval(environment);
		}
		
		return evalResult.getResult();
	}
	
	/**
	 * Print the object
	 */
	public abstract String print();
}
