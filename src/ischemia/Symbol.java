package ischemia;

import java.util.HashMap;

public class Symbol extends SchemeObject {
	private String value;
	private static HashMap<String, Symbol> symbolTable = new HashMap<>();
	
	public static Symbol quoteSymbol = makeActualSymbol("quote");
	public static Symbol setSymbol = makeActualSymbol("set!");
	public static Symbol defineSymbol = makeActualSymbol("define");
	public static Symbol okSymbol = makeActualSymbol("ok");
	
	private Symbol(String value) {this.value = value;}
	
	public String getValue() {return value;}

	public SchemeObject eval(Environment env) throws EvalException {
		return env.lookupValue(this);
	}

	public String print() {
		return value;
	}
	
	public boolean equals(Symbol symbol) {
		return (this.value == symbol.value);
	}
	
	public int hashCode() {return value.hashCode();}
	
	private static Symbol makeActualSymbol(String symbol) {
		if (symbolTable.containsKey(symbol)) return symbolTable.get(symbol);
		
		Symbol newSymbol = new Symbol(symbol);
		symbolTable.put(symbol, newSymbol);
		
		return newSymbol;		
	}
	
	public static Symbol makeSymbol(String symbol) throws ParseException {
		if (!symbol.matches("^[a-zA-Z0-9\\+-=/\\*\\!]+$")) {
			throw new ParseException("Invalid symbol!");
		}
		
		return makeActualSymbol(symbol);
	}

}
