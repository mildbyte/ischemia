package ischemia;

public class EmptyList extends SchemeObject {
	private static EmptyList list = new EmptyList(); 
	
	public SchemeObject eval() throws EvalException {
		throw new EvalException("Cannot evaluate the expression!");
	}

	public String print() {
		return "()";
	}
	
	private EmptyList() {}
	
	public static EmptyList makeEmptyList() {return list;}
}
