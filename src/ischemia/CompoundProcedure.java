package ischemia;

//A user-defined procedure
public class CompoundProcedure extends Procedure {
	private SchemeObject body;
	private SchemeObject unboundArgs;
	private Environment environment;
	 
	public CompoundProcedure(SchemeObject args, SchemeObject body, Environment environment) {
		//Use the begin form defined previously to make sure that
		//the body of the lambda is one expression only and avoid repeating the code.		
		this.unboundArgs = args;
		this.environment = environment;
		this.body = new Pair(new Pair(Symbol.beginSymbol, body), EmptyList.makeEmptyList());
	}
	
	//Evaluates the procedure in the procedure's definition environment
	public EvaluationResult evalProcedure(Environment currEnv, SchemeObject args) throws EvalException {
		
		//Extend the environment so that the arguments passed to the procedure are
		//visible
		Environment evalEnv = new Environment(environment, new Frame(unboundArgs, args));
		
		//Since we wrapped the body of the procedure in a begin, we know there's only
		//one element in the body.
		return EvaluationResult.makeUnfinished(((Pair)body).car(), evalEnv);		
	}

}
