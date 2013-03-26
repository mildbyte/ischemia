package ischemia;


/**
 * A self-evaluating string literal.
 *
 */
public class StringLiteral extends SchemeObject {
	private String value;
	public String getValue() {return value;}
	public StringLiteral(String value) {this.value = value;}
	
	public EvaluationResult eval(SchemeObject env) {
		return EvaluationResult.makeFinished(this);
	}

	public String print() {
		return "\"" + value + "\""; 
	}
	
	public boolean equals(SchemeObject s) {
		return ((s instanceof StringLiteral) && ((StringLiteral)s).value.equals(value));
	}
}
