package ischemia;

//A self-evaluating character
public class Character extends SchemeObject {
	private char value;
	public char getValue() {return value;}
	
	public static Character makeCharacter (String expression) throws ParseException {
		if (expression.equals("#\\newline")) {return new Character('\n');}
		if (expression.equals("#\\space")) {return new Character(' ');}
		if (expression.length() == 3) {return new Character(expression.charAt(2));}
		
		throw new ParseException("Unknown character literal " + expression);
	}
	
	public Character(char value) {this.value = value;}
	
	public boolean equals(SchemeObject s) {
		return (s instanceof Character) && value == ((Character)s).value;
	}
	
	public EvaluationResult eval(Environment env) {
		return EvaluationResult.makeFinished(this);
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
