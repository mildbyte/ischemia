package ischemia;

import java.io.BufferedReader;
import java.io.IOException;

public class SchemeReader {
	public static SchemeObject read(BufferedReader reader) throws ParseException {
		String expression;
		char current;
		try {
			do {
				current = (char)reader.read();
			} while (current == ' ' || current == '\t' || current == '\n');
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
				do {
					current = (char)reader.read();
				} while (current == ' ' || current == '\t' || current == '\n');
				
				if (current != ')') throw new ParseException("Invalid empty list");
				return EmptyList.makeEmptyList();
			} else {
				expression = current + reader.readLine();
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
