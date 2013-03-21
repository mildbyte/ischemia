package ischemia;

import java.io.BufferedReader;
import java.io.IOException;

public class SchemeReader {
	private static boolean isDelimiter(char c) {
		return (c == ' ' || c == '\t' || c == '\n');
	}
	private static char peek(BufferedReader reader) throws IOException {
		reader.mark(1);
		char result = (char)reader.read();
		reader.reset();
		return result;
	}
	
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
	
	private static SchemeObject readPair(BufferedReader reader) throws ParseException,IOException {
		skipWhitespace(reader);
		char current = peek(reader);
		
		if (current == ')') {
			current = (char)reader.read();
			return EmptyList.makeEmptyList();
		}
		
		SchemeObject car = read(reader);
		
		skipWhitespace(reader);
		current = peek(reader);		
		
		if (current == '.') {
			current = (char)reader.read();
			current = peek(reader);
			if (!isDelimiter(current)) {
				throw new ParseException("Dot not followed by a delimiter!");
			}
			
			SchemeObject cdr = read(reader);
			
			skipWhitespace(reader);
			
			current = (char)reader.read();
			if (current != ')') {
				throw new ParseException("More than two objects in a pair or mismatched parentheses!");
			}
			
			return new Pair(car, cdr);
		} else {
			SchemeObject cdr = readPair(reader);
			return new Pair (car, cdr);
		}
	}
	
	public static SchemeObject read(BufferedReader reader) throws ParseException {
		String expression;
		char current;
		try {
			skipWhitespace(reader);
			current = (char)reader.read();
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
			} else if (current == '(') {
				skipWhitespace(reader);
				return readPair(reader);
			} else {
				expression = current + readToDelimiter(reader);
			}
		} catch (IOException e) {
			throw new ParseException("IO Exception!");
		}
		
		expression = expression.trim();
		if (expression.startsWith("#\\")) {
			return Character.makeCharacter(expression);
		}
		
		if (expression.startsWith("#")) {
			return Boolean.makeBoolean(expression);
		}
		
		return new Fixnum(Integer.parseInt(expression));
	}
}
