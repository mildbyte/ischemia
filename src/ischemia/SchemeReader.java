package ischemia;

public class SchemeReader {
	public static SchemeObject read(String expression) throws ParseException {
		expression = expression.trim();
		if (expression.startsWith("#")) {
			return Boolean.makeBoolean(expression);
		}
		return new Fixnum(Integer.parseInt(expression));
	}
}
