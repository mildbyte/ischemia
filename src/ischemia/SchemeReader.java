package ischemia;

import java.io.BufferedReader;
import java.io.IOException;

//The Scheme lexer
public class SchemeReader {
	private static boolean isDelimiter(char c) {
		return (c == ' ' || c == '\t' || c == '\n');
	}
	
	/**
	 * 
	 * Returns one character from the reader without advancing it.
	 * 
	 */
	private static char peek(BufferedReader reader) throws IOException {
		reader.mark(1);
		char result = (char)reader.read();
		reader.reset();
		return result;
	}
	
	
	/**
	 * Reads the buffer until all whitespace has been consumed.
	 * 
	 */
	private static void skipWhitespace(BufferedReader reader) throws IOException {
		char current;
		while (true) {
			reader.mark(1);
			current = (char)reader.read();
			if (!isDelimiter(current)) {
				reader.reset();
				return;
			}
		}
	}
	
	
	/**
	 * 
	 * Reads a string from the buffer until it encounters a delimiter
	 * 
	 */
	private static String readToDelimiter(BufferedReader reader) throws IOException {
		String result = "";
		while (true) {
			reader.mark(1);
			char current = (char)reader.read();
			if (isDelimiter(current) || (current == ')')) {
				reader.reset();
				return result;
			}
			result += current;
		}
	}
	
	
	/**
	 * 
	 * Reads a pair from the reader
	 * 
	 */
	private static SchemeObject readPair(BufferedReader reader) throws ParseException,IOException {
		skipWhitespace(reader);
		char current = peek(reader);
		
		//If we see a closing bracket, we've reached the end of the list
		//(the empty list will be consed to the previous pair)
		if (current == ')') {
			current = (char)reader.read();
			return EmptyList.makeEmptyList();
		}
		
		//Read the first element of the pair
		SchemeObject car = read(reader);
		
		skipWhitespace(reader);
		current = peek(reader);		
		
		if (current == '.') { //Dotted pair
			current = (char)reader.read();
			current = peek(reader);
			if (!isDelimiter(current)) {
				throw new ParseException("Dot not followed by a delimiter!");
			}
			
			//Read the second object			
			SchemeObject cdr = read(reader);
			
			skipWhitespace(reader);
			
			//Make sure there are only two objects
			current = (char)reader.read();
			if (current != ')') {
				throw new ParseException("More than two objects in a pair or mismatched parentheses!");
			}
			
			return new Pair(car, cdr);
		} else { //Recurse on the rest of the list
			SchemeObject cdr = readPair(reader);
			return new Pair (car, cdr);
		}
	}
	
	/**
	 * 
	 * Reads one SchemeObject from the reader
	 * 
	 */
	public static SchemeObject read(BufferedReader reader) throws ParseException {
		String expression;
		char current;
		try {
			skipWhitespace(reader);
			current = (char)reader.read();
			
			//Found a string with escape characters
			if (current == '"') {
				expression = "";
				do {
					current = (char)reader.read();
					if (current ==  '"') break;
					if (current == '\\') {
						current = (char)reader.read();
						if (current == 'n') {
							current = '\n';
						} else if (!(current == '\\' || current == '"')) {
							throw new ParseException("Invalid escape character.");
						}
					}
					expression += current;
				} while (true);
				return new StringLiteral(expression);
			} else if (current == '(') { //Found a pair
				skipWhitespace(reader);
				return readPair(reader);
			} else if (current == '\'') {
				//Found a quote symbol, replace with the actual quote command
				return new Pair(Symbol.quoteSymbol, 
					   new Pair(read(reader), 
							    EmptyList.makeEmptyList()));
			} else {
				//Either a symbol, a fixnum, a boolean or a character
				expression = current + readToDelimiter(reader);
			}
		} catch (IOException e) {
			throw new ParseException("IO Exception!");
		}
		
		expression = expression.trim();
		
		//#\ + a character
		if (expression.startsWith("#\\")) {
			return Character.makeCharacter(expression);
		}
		
		//#t or #f
		if (expression.startsWith("#")) {
			return Boolean.makeBoolean(expression);
		}
		
		//Otherwise, try a fixnum, if we can't parse it, try making it
		//into a symbol.
		try {
			return new Fixnum(Integer.parseInt(expression));
		} catch(NumberFormatException e) {
			return Symbol.makeSymbol(expression);
		}
	}
}
