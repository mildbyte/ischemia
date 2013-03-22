package ischemia;

//A "function pointer" class for a procedure
public abstract class Procedure extends SchemeObject {

	protected EvaluationResult eval(Environment environment)
			throws EvalException {
		throw new EvalException("Cannot evaluate a procedure!");
	}
	
	public abstract EvaluationResult evalProcedure(Environment environment, SchemeObject args) throws EvalException;

	public String print() {
		return ("#<procedure>");
	}

}
