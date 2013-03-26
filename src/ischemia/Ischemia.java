package ischemia;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ischemia {
	public static void main(String[] args) {
		System.out.println("Welcome to Ischemia.");
		System.out.println("Use Ctrl+C to exit.");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.print("> ");

			try {
				SchemeObject object = SchemeReader.read(in);
				if (object == null) return;
				SchemeObject evaluated = object.evaluate(Environment.getGlobalEnvironment());
				System.out.print(evaluated.print());
				System.out.println();
				
				//If an error was returned, exit the REPL.
				if (evaluated.equals(Symbol.exitingSymbol)) return;
			} catch (ParseException e) {
			 	System.out.println(e.getMessage());
			} catch (EvalException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
