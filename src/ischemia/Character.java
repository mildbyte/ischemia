package ischemia;

public class Character extends SchemeObject {
	private char value;
	public char getValue() {return value;}
	
	public static Character makeCharacter (String expression) throws ParseException {
		if (expression.equals("#\\newline")) {return new Character('\n');}
		if (expression.equals("#\\space")) {return new Character(' ');}
		if (expression.length() == 3) {return new Character(expression.charAt(2));}
		
		throw new ParseException("Unknown character literal!");
	}
	
	private Character(char value) {this.value = value;}
	
	public SchemeObject eval() {
		return this;
	}

	public String print() {
		String result = "#\\";
		
		switch(value) {
		case '\n': return result + "newline";
		case ' ': return result + "space";
		default: return result + value;
		}
	}

}