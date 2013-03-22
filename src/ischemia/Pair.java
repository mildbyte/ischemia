package ischemia;

/**
 * Encapsulates a LISP pair and its evaluation
 *
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
	
	
	/* (non-Javadoc)
	 * @see ischemia.SchemeObject#eval(ischemia.Environment)
	 */
	public SchemeObject eval(Environment env) throws EvalException {
		//The object we are evaluating (can change as we traverse if expressions.
		//Used to avoid recursing since Java doesn't have tail call optimization.
		SchemeObject toEval = this;
		
		while (true) {
			Pair pToEval = (Pair)toEval;
			
			//Unquote quoted lists when evaluating
			if (car.equals(Symbol.quoteSymbol)) {
				return (pToEval.pcdr().car);
			}
			
			//Mutate the value in the current environment if it exists
			if (car.equals(Symbol.setSymbol)) {
				env.setVariableValue(pToEval.pcdr().car, pToEval.pcdr().pcdr().car.eval(env));
				return Symbol.okSymbol;
			}
			
			//Define a variable in the current environment, overwriting it if it exists.
			if (car.equals(Symbol.defineSymbol)) {
				env.defineVariable(pToEval.pcdr().car, pToEval.pcdr().pcdr().car.eval(env));
				return Symbol.okSymbol;
			}
			
			//Evaluate the If symbol
			if (car.equals(Symbol.ifSymbol)) {
				//Anything that's not explicitly false is true
				if (!pToEval.pcdr().car.eval(env).equals(Boolean.FalseValue)) {
					toEval = pToEval.pcdr().pcdr().car;
				} else {
					//If there is no "false" parameter, return false
					if (pcdr().pcdr().cdr.equals(EmptyList.makeEmptyList())) {
						return Boolean.FalseValue;
					}
					//Otherwise, we will evaluate that expression
					toEval = pToEval.pcdr().pcdr().pcdr().car;
				}
				//If the thing we're evaluating next is a pair, we can use the
				//tail call optimization (jump to the beginning)
				if (toEval instanceof Pair) continue;
				
				//Otherwise, we have to recurse as we have no idea how to evaluate it
				return toEval.eval(env);				
			}
			throw new EvalException("Cannot evaluate expression!");			
		}
		
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
