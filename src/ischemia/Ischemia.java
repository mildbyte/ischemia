package ischemia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ischemia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Ischemia.");
		System.out.println("Use Ctrl+C to exit.");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		Environment globalEnv = new Environment();
		PrimitiveProcedures.installProcedures(globalEnv);
		
		while (true) {
			System.out.print("> ");

			try {
				SchemeObject object = SchemeReader.read(in);
				System.out.print(object.evaluate(globalEnv).print());
				System.out.println();
			} catch (ParseException e) {
			 	System.out.println(e.getMessage());
			} catch (EvalException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
