package ischemia;


/**
 * A self-evaluating string literal.
 *
 */
public class StringLiteral extends SchemeObject {
	private String value;
	public String getValue() {return value;}
	public StringLiteral(String value) {this.value = value;}
	
	public SchemeObject eval(Environment env) {
		return this;
	}

	public String print() {
		return "\"" + value + "\""; 
	}	
}
