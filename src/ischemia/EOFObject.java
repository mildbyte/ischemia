package ischemia;

public class EOFObject extends SchemeObject {
	private static EOFObject theObject;
	
	protected EvaluationResult eval(SchemeObject environment)
			throws EvalException {
		throw new EvalException("Cannot evaluate an EOF object!");
	}

	public String print() {
		return "EOF";
	}
	
	private EOFObject() {}
	
	public static EOFObject makeEOFObject() {
		if (theObject == null) theObject = new EOFObject();
		
		return theObject;
	}

}
