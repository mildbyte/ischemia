package ischemia;

public class PrimitiveProcedures {
	/**
	 * Adds two numbers together
	 */
	public static Procedure addProcedure = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			Pair pargs = (Pair)args;
			
			return EvaluationResult.makeFinished(
					new Fixnum(((Fixnum)pargs.car()).getNumber() +
					((Fixnum)((Pair)(pargs.cdr())).car()).getNumber()));
		}
	};
	
	public static void installProcedures(Environment env) {
		env.defineVariable(Symbol.plusSymbol, addProcedure);
		
	}
}
