package ischemia;

public class Boolean extends SchemeObject {
	private static Boolean True = new Boolean(true);
	private static Boolean False = new Boolean(false);
	
	private boolean value;
	public boolean getValue() {return value;}
	
	public String eval() {
		return value ? "#t" : "#f";
	}
	
	private Boolean(boolean value) {this.value = value;}
	
	public static Boolean makeBoolean(String expression) throws UnknownBooleanLiteralException {
		if (expression.equals("#t")) {
			return True;
		} else if (expression.equals("#f")) {
			return False;
		} else throw new UnknownBooleanLiteralException();
	}
}
