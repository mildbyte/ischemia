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
		
		while (true) {
			System.out.print("> ");
			String input;
			
			try {
				input = in.readLine();
			} catch (IOException e) {
				return;
			}
			try {
				SchemeObject object = SchemeReader.read(input);
				System.out.print(object.eval().print());
				System.out.println();
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
