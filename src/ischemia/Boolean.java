package ischemia;

//A singleton encapsulating True or False
public class Boolean extends SchemeObject {
	public static Boolean TrueValue = new Boolean(true);
	public static Boolean FalseValue = new Boolean(false);
	
	private boolean value;
	public boolean getValue() {return value;}
	
	public SchemeObject eval(Environment env) {
		return this;
	}
	public String print() {
		return value ? "#t" : "#f";
	}
	
	private Boolean(boolean value) {this.value = value;}
	
	public static Boolean makeBoolean(String expression) throws ParseException {
		if (expression.equals("#t")) {
			return TrueValue;
		} else if (expression.equals("#f")) {
			return FalseValue;
		} else throw new ParseException("Unknown boolean literal.");
	}
}
