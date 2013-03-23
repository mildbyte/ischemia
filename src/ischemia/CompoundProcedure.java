package ischemia;

//A user-defined procedure
public class CompoundProcedure extends Procedure {
	private SchemeObject body;
	private SchemeObject unboundArgs;
	 
	public CompoundProcedure(SchemeObject args, SchemeObject body) {
		this.unboundArgs = args;
		this.body = body;
	}
	
	//Evaluates the procedure in the given environment
	public EvaluationResult evalProcedure(Environment environment,
			SchemeObject args) throws EvalException {
		
		//Extend the environment so that the arguments passed to the procedure are
		//visible
		Environment evalEnv = new Environment(environment, new Frame(unboundArgs, args));
		
		//Since we wrapped the body of the procedure in a begin, we know there's only
		//one element in the body.
		return EvaluationResult.makeFinished(((Pair)body).car().evaluate(evalEnv));		
		
/*		//We are evaluating the first expression
		SchemeObject exp = body;
		
		//While there exists a next expression..
		while(!(((Pair)exp).cdr() instanceof EmptyList)) {
			//Evaluate the expression and move on to the next one
			((Pair)exp).car().evaluate(evalEnv);
			exp = ((Pair)exp).cdr();
		}
		
		//Tail position, we can optimize it
		return EvaluationResult.makeUnfinished(((Pair)exp).car(), evalEnv);*/
	}

}
