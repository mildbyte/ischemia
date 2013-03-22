package ischemia;

//An integer number.
public class Fixnum extends SchemeObject {
	private int number;
	
	public int getNumber() {return number;}
	public Fixnum(int number) {this.number = number;}
	
	public EvaluationResult eval(Environment env) {return EvaluationResult.makeFinished(this);}
	public String print() {return Integer.toString(number);}
	
	public boolean equals(SchemeObject s) {
		return (s instanceof Fixnum) && number == ((Fixnum)s).number;
	}
}
