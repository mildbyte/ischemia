package ischemia;

public abstract class SchemeObject {
	public abstract SchemeObject eval() throws EvalException;
	public abstract String print();
}
