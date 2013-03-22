package ischemia;

//An integer number.
public class Fixnum extends SchemeObject {
	private int number;
	
	public int getNumber() {return number;}
	public Fixnum(int number) {this.number = number;}
	
	public SchemeObject eval(Environment env) {return this;}
	public String print() {return Integer.toString(number);}
}
