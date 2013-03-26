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
	
	/**
	 * Prepares the arguments to pass to the procedure called by apply (merges all of them into one list)
	 */
	private static SchemeObject prepareApplyArgs(SchemeObject args) {
		if (((Pair)args).cdr instanceof EmptyList) return ((Pair)args).car;
		
		return new Pair(((Pair)args).car, prepareApplyArgs(((Pair)args).cdr));		
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
		
		//Evaluate the cond form. We could convert it into a series of nested ifs,
		//but this method is more efficient.
		if (car.equals(Symbol.condSymbol)) {
			//Pointer to the list of remaining possibilities
			Pair currExpr = pcdr();
			
			while (!(currExpr.car instanceof EmptyList)) {
				//If we have reached the else clause or a true condition, return the result
				if (currExpr.pcar().car.equals(Symbol.elseSymbol)
					|| !currExpr.pcar().car.evaluate(env).equals(Boolean.FalseValue)) {
					return EvaluationResult.makeUnfinished(currExpr.pcar().pcdr().car, env);
				}
				
				//Advance the pointer
				currExpr = currExpr.pcdr();
			}
			//Reached the end, return a false constant (like in the (if), is this valid?)
			return EvaluationResult.makeFinished(Boolean.FalseValue);
		}
		
		//Evaluate the let form by extending the environment to contain
		//the new bindings and then evaluating the body of the let in the environment.
		if (car.equals(Symbol.letSymbol)) {
			Environment evalEnv = new Environment(env);
			
			//Zipped list of variables and their values
			SchemeObject varValues = pcdr().car;
			while (!(varValues instanceof EmptyList)) {
				Pair currPair = (Pair)((Pair)varValues).car();
				//If we evaluate the variable in the environment currently being created (evalEnv), we
				//get the code for the let* form.
				evalEnv.defineVariable(currPair.car(), ((Pair)(currPair.cdr())).car().evaluate(env));
				
				//Advance to the next binding
				varValues = ((Pair)varValues).cdr;
			}
			
			//Evaluate the expression in the resultant environment
			return EvaluationResult.makeUnfinished(pcdr().pcdr().car, evalEnv);
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
		
		//Evaluate the lambda symbol
		if (car.equals(Symbol.lambdaSymbol)) {
			return EvaluationResult.makeFinished(
					new CompoundProcedure(pcdr().car, pcdr().cdr)); 
		}
		
		//Evaluate the and symbol (short-circuiting, does not evaluate the rest of the conditions
		//if we already know the result).
		if (car.equals(Symbol.andSymbol)) {
			SchemeObject currList = cdr;
			
			while (!(currList instanceof EmptyList)) {
				//If one of the conditions is false, return false
				if (((Pair)currList).car.evaluate(env).equals(Boolean.FalseValue)) {
					return EvaluationResult.makeFinished(Boolean.FalseValue);
				}
				
				//Advance to the next condition
				currList = ((Pair)currList).cdr;
			}
			
			//Reached the end with all conditions true, return true.
			//(or should it be the result of the last condition?)
			return EvaluationResult.makeFinished(Boolean.TrueValue);
		}
		
		//Evaluate the or symbol (same as and, but with false and true swapped).
		if (car.equals(Symbol.orSymbol)) {
			SchemeObject currList = cdr;
			
			while (!(currList instanceof EmptyList)) {
				//If one of the conditions is true, return true
				if (!((Pair)currList).car.evaluate(env).equals(Boolean.FalseValue)) {
					return EvaluationResult.makeFinished(Boolean.TrueValue);
				}
				
				//Advance to the next condition
				currList = ((Pair)currList).cdr;
			}
			
			//Reached the end with all conditions false, return false.
			return EvaluationResult.makeFinished(Boolean.FalseValue);
		}
		
		//The apply form: apply the second element to the arguments formed by the list
		//in the third element (or the rest of the arguments to apply + the list in the last position)
		if (car.equals(Symbol.applySymbol)) {
			SchemeObject evaluatedArgs = evalAll(env, pcdr().pcdr());

			return EvaluationResult.makeUnfinished(
					new Pair(pcdr().car, prepareApplyArgs(evaluatedArgs)), env);
		}
		
		//Evaluates the eval form
		if (car.equals(Symbol.evalSymbol)) {
			Environment evalEnv = (Environment)pcdr().pcdr().car.evaluate(env);
			return EvaluationResult.makeUnfinished(pcdr().car.evaluate(env), evalEnv);
		}
		
		//Evaluate procedure application
		SchemeObject procedure = car.evaluate(env);
		
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
