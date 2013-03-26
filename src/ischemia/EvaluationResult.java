package ischemia;

//Encapsulates the result of evaluating an expression.
//In case the evaluation isn't finished, the result will have to
//be evaluated again (by the calling routine), avoiding blowing up
//the call stack.
public class EvaluationResult {
	private boolean evaluationFinished;
	private SchemeObject result;
	private SchemeObject environment;
	
	//The constructor is made private, the object can only be created by using the
	//static methods below.
	private EvaluationResult(boolean evaluationFinished, SchemeObject result, SchemeObject environment) {
		this.evaluationFinished = evaluationFinished;
		this.result = result;
		this.environment = environment;
	}
	
	public boolean isFinished() {return evaluationFinished;}
	public SchemeObject getResult() {return result;}
	public SchemeObject getEnvironment() {return environment;}
	
	public static EvaluationResult makeFinished(SchemeObject result) {
		return new EvaluationResult(true, result, null);
	}
	
	public static EvaluationResult makeUnfinished(SchemeObject result, SchemeObject environment) {
		return new EvaluationResult(false, result, environment);
	}
}
