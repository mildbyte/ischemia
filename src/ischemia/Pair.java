package ischemia;

public class Pair extends SchemeObject {
	private SchemeObject car;
	private SchemeObject cdr;
	
	public Pair(SchemeObject car, SchemeObject cdr) {
		this.car = car;
		this.cdr = cdr;
	}
	
	public SchemeObject car() {return car;}
	public SchemeObject cdr() {return cdr;}
	
	public void setCar(SchemeObject car) {this.car = car;}
	public void setCdr(SchemeObject cdr) {this.cdr = cdr;}

	public SchemeObject eval(Environment env) throws EvalException {
		if (car.equals(Symbol.quoteSymbol)) {
			return ((Pair)cdr).car;
		}
		
		if (car.equals(Symbol.setSymbol)) {
			env.setVariableValue(((Pair)cdr).car, ((Pair)((Pair)cdr).cdr).car);
			return Symbol.okSymbol;
		}
		
		if (car.equals(Symbol.defineSymbol)) {
			env.defineVariable(((Pair)cdr).car, ((Pair)((Pair)cdr).cdr).car);
			return Symbol.okSymbol;
		}		
		
		throw new EvalException("Cannot evaluate expression!");
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
