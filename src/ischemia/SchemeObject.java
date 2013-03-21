package ischemia;

public abstract class SchemeObject {
	public abstract SchemeObject eval(Environment environment) throws EvalException;
	public abstract String print();
}
