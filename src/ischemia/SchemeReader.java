package ischemia;

public class SchemeReader {
	public static SchemeObject read(String expression) {
		return new Fixnum(Integer.parseInt(expression));
	}
}
