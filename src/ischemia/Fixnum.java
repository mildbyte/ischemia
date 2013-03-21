package ischemia;

public class Fixnum extends SchemeObject {
	private int number;
	
	public int getNumber() {return number;}
	public Fixnum(int number) {this.number = number;}
	
	public SchemeObject eval() {return this;}
	public String print() {return Integer.toString(number);}
}
