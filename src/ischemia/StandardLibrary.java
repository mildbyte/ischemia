package ischemia;

//Some helper functions to test things out
public class StandardLibrary {
	private static String foldString = 
			"(define (fold f xs acc) (define (helper xs acc) (if (null? xs) acc (helper (cdr xs) (f (car xs) acc)))) (helper xs acc))";
	
	public static void installSTL(Environment env) {
		try {
			SchemeReader.parseString(foldString).evaluate(env);
		} catch (EvalException e) {
		} catch (ParseException e) {
		}
	}
}
