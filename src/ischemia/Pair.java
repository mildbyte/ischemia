package ischemia;

/**
 * Encapsulates a LISP pair and its evaluation.
 * Also is responsible for evaluating all special forms and procedures.
 */
public class Pair extends SchemeObject {
	private SchemeObject car;
	private SchemeObject cdr;
	
	public Pair(SchemeObject car, SchemeObject cdr) {
		this.car = car;
		this.cdr = cdr;
	}
	
	//Casts the car and cdr of a pair to a pair (more elegant chaining of cars)
	private Pair pcar() {return (Pair)car;}
	private Pair pcdr() {return (Pair)cdr;}
	
	public SchemeObject car() {return car;}
	public SchemeObject cdr() {return cdr;}
	
	public void setCar(SchemeObject car) {this.car = car;}
	public void setCdr(SchemeObject cdr) {this.cdr = cdr;}
	
	/**
	 * Evaluates all objects in a list
	 */
	private static SchemeObject evalAll(Environment env, SchemeObject obj) throws EvalException {
		if (obj instanceof EmptyList) return obj;
		return new Pair(((Pair)obj).car().evaluate(env), evalAll(env, ((Pair)obj).cdr()));
	}
	
	public EvaluationResult eval(Environment env) throws EvalException {
		//Unquote quoted lists when evaluating
		if (car.equals(Symbol.quoteSymbol)) {
			return EvaluationResult.makeFinished(pcdr().car);
		}
		
		//Mutate the value in the current environment if it exists
		if (car.equals(Symbol.setSymbol)) {
			env.setVariableValue(pcdr().car, pcdr().pcdr().car.evaluate(env));
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
		
		//Define a variable or a procedure in the current environment, overwriting it if it exists.
		if (car.equals(Symbol.defineSymbol)) {
			if (pcdr().car instanceof Pair) { //It's a procedure!
				//The first element in the first argument is the name,
				//the rest are the parameters
				//the second argument is the procedure body
				env.defineVariable(pcdr().pcar().car, 
						new CompoundProcedure(pcdr().pcar().cdr, pcdr().cdr));
				return EvaluationResult.makeFinished(Symbol.okSymbol);
						
			}
			env.defineVariable(pcdr().car, pcdr().pcdr().car.evaluate(env));
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
		
		//Evaluate the If symbol
		if (car.equals(Symbol.ifSymbol)) {
			//Anything that's not explicitly false is true
			if (!pcdr().car.evaluate(env).equals(Boolean.FalseValue)) {
				return EvaluationResult.makeUnfinished(pcdr().pcdr().car, env);
			} else {
				//If there is no "false" parameter, return false
				if (pcdr().pcdr().cdr.equals(EmptyList.makeEmptyList())) {
					return EvaluationResult.makeFinished(Boolean.FalseValue);
				}
				//Otherwise, we will evaluate that expression
				return EvaluationResult.makeUnfinished(pcdr().pcdr().pcdr().car, env);
			}
		}
		
		//Evaluate the begin symbol (sequence of events, same as evaluating
		//a compound procedure)
		if (car.equals(Symbol.beginSymbol)) {
			//We are evaluating the first expression
			SchemeObject exp = cdr;
			
			//While there exists a next expression..
			while(!(((Pair)exp).cdr() instanceof EmptyList)) {
				//Evaluate the expression and move on to the next one
				((Pair)exp).car().evaluate(env);
				exp = ((Pair)exp).cdr();
			}
			
			//Tail position, we can optimize it
			return EvaluationResult.makeUnfinished(((Pair)exp).car(), env);
		}
		
		//Evaluate the lambda symbol. Use the begin form defined previously to make sure that
		//the body of the lambda is one expression only and avoid repeating the code.
		if (car.equals(Symbol.lambdaSymbol)) {
			return EvaluationResult.makeFinished(
					new CompoundProcedure(pcdr().car, 
							new Pair(new Pair(Symbol.beginSymbol, pcdr().cdr), EmptyList.makeEmptyList())));
		}
		
		//Evaluate procedure application
		SchemeObject procedure = env.lookupValue(car);
		
		if (!(procedure instanceof Procedure)) {
			throw new EvalException("Unknown procedure!");
		}
		
		return ((Procedure)procedure).evalProcedure(env, evalAll(env, cdr));
	}
	
	public String printPair() {
		//Print the first element.
		String result = car.print();
		
		//If it's a list, don't print dots. If it's a pair, do.
		if (cdr instanceof Pair) {
			return result + " " + pcdr().printPair();
		} else if (cdr instanceof EmptyList) {
			return result;
		} else {
			return result + " . " + cdr.print();
		}		
	}

	public String print() {
		return "(" + printPair() + ")";
	}
}
