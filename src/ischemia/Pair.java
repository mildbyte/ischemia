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

	public SchemeObject eval() {
		return this;
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
