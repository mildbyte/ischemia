package ischemia;

public class EmptyList extends SchemeObject {
	private static EmptyList list = new EmptyList(); 
	
	public SchemeObject eval() {
		return this;
	}

	public String print() {
		return "()";
	}
	
	private EmptyList() {}
	
	public static EmptyList makeEmptyList() {return list;}
}
