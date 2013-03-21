package ischemia;

public class Pair extends SchemeObject {
	private SchemeObject car;
	private SchemeObject cdr;
	
	public Pair(SchemeObject car, SchemeObject cdr) {
		this.car = car;
		this.cdr = cdr;
	}
	
	private Pair pcar() {return (Pair)car;}
	private Pair pcdr() {return (Pair)cdr;}
	
	public SchemeObject car() {return car;}
	public SchemeObject cdr() {return cdr;}
	
	public void setCar(SchemeObject car) {this.car = car;}
	public void setCdr(SchemeObject cdr) {this.cdr = cdr;}

	public SchemeObject eval(Environment env) throws EvalException {
		SchemeObject toEval = this;
		
		while (true) {
			Pair pToEval = (Pair)toEval;			
			if (car.equals(Symbol.quoteSymbol)) {
				return (pToEval.pcdr().car);
			}
			
			if (car.equals(Symbol.setSymbol)) {
				env.setVariableValue(pToEval.pcdr().car, pToEval.pcdr().pcdr().car.eval(env));
				return Symbol.okSymbol;
			}
			
			if (car.equals(Symbol.defineSymbol)) {
				env.defineVariable(pToEval.pcdr().car, pToEval.pcdr().pcdr().car.eval(env));
				return Symbol.okSymbol;
			}
			
			if (car.equals(Symbol.ifSymbol)) {
				if (!pToEval.pcdr().car.eval(env).equals(Boolean.FalseValue)) {
					toEval = pToEval.pcdr().pcdr().car;
				} else {
					if (pcdr().pcdr().cdr.equals(EmptyList.makeEmptyList())) {
						return Boolean.FalseValue;
					}
					toEval = pToEval.pcdr().pcdr().pcdr().car;
				}
				if (toEval instanceof Pair) continue;
				return toEval.eval(env);				
			}
			throw new EvalException("Cannot evaluate expression!");			
		}
		
	}
	
	public String printPair() {
		String result = car.print();
		if (cdr instanceof Pair) {
			return result + " " + ((Pair)cdr).printPair();
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
