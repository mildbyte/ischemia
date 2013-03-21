package ischemia;

import java.util.HashMap;

public class Symbol extends SchemeObject {
	private String value;
	private static HashMap<String, Symbol> symbolTable = new HashMap<>();
	
	public static Symbol quoteSymbol = makeActualSymbol("quote");
	
	private Symbol(String value) {this.value = value;}
	
	public String getValue() {return value;}

	public SchemeObject eval() throws EvalException {
		throw new EvalException("Cannot evaluate expression!");
	}

	public String print() {
		return value;
	}
	
	private static Symbol makeActualSymbol(String symbol) {
		if (symbolTable.containsKey(symbol)) return symbolTable.get(symbol);
		
		Symbol newSymbol = new Symbol(symbol);
		symbolTable.put(symbol, new Symbol(symbol));
		
		return newSymbol;		
	}
	
	public static Symbol makeSymbol(String symbol) throws ParseException {
		if (!symbol.matches("^[a-zA-Z0-9\\+-=/\\*\\!]+$")) {
			throw new ParseException("Invalid symbol!");
		}
		
		return makeActualSymbol(symbol);
	}
}
