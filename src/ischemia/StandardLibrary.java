package ischemia;

//Some helper functions to test things out
public class StandardLibrary {
	private static String foldString = 
			"(define (fold f xs acc) (if (null? xs) acc (fold f (cdr xs) (f (car xs) acc))))";
	
	public static void installSTL(Environment env) {
		try {
			SchemeReader.parseString(foldString).evaluate(env);
		} catch (EvalException e) {
		} catch (ParseException e) {
		}
	}
}
