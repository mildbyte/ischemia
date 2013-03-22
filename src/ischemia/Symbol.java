package ischemia;

import java.util.HashMap;

//A symbol singleton with a symbol table (to make sure we're not creating
//duplicate objects
public class Symbol extends SchemeObject {
	private String value;
	private static HashMap<String, Symbol> symbolTable = new HashMap<>();
	
	//The symbols that are available by default.
	public static Symbol quoteSymbol = makeActualSymbol("quote");
	public static Symbol setSymbol = makeActualSymbol("set!");
	public static Symbol defineSymbol = makeActualSymbol("define");
	public static Symbol okSymbol = makeActualSymbol("ok");
	public static Symbol ifSymbol = makeActualSymbol("if");
	public static Symbol plusSymbol = makeActualSymbol("+");
	
	private Symbol(String value) {this.value = value;}
	
	public String getValue() {return value;}
	
	public EvaluationResult eval(Environment env) throws EvalException {
		//For a variable, looks up its value in the environment.
		return EvaluationResult.makeFinished(env.lookupValue(this));
	}

	public String print() {
		return value;
	}
	
	public boolean equals(Symbol symbol) {
		return (this.value == symbol.value);
	}
	
	public int hashCode() {return value.hashCode();}
	
	/**
	 * Called by the creator of initial symbols since we know that those are valid
	 */
	private static Symbol makeActualSymbol(String symbol) {
		//Return the symbol from the table if it already exists,
		//create it otherwise.
		if (symbolTable.containsKey(symbol)) return symbolTable.get(symbol);
		
		Symbol newSymbol = new Symbol(symbol);
		symbolTable.put(symbol, newSymbol);
		
		return newSymbol;		
	}
	
	public static Symbol makeSymbol(String symbol) throws ParseException {
		//Now you have two problems.
		if (!symbol.matches("^[a-zA-Z0-9\\+-=/\\*\\!]+$")) {
			throw new ParseException("Invalid symbol!");
		}
		
		return makeActualSymbol(symbol);
	}

}
