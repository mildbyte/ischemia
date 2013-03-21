package ischemia;

public class StringLiteral extends SchemeObject {
	private String value;
	public String getValue() {return value;}
	public StringLiteral(String value) {this.value = value;}
	
	public SchemeObject eval() {
		return this;
	}

	public String print() {
		return "\"" + value + "\""; 
	}	
}
