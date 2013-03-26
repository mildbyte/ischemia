package ischemia;

//A "function pointer" class for a procedure
public abstract class Procedure extends SchemeObject {

	protected EvaluationResult eval(SchemeObject environment)
			throws EvalException {
		throw new EvalException("Cannot evaluate a procedure!");
	}
	
	public abstract EvaluationResult evalProcedure(SchemeObject environment, SchemeObject args) throws EvalException;

	public String print() {
		return ("#<procedure>");
	}

}
