package ischemia;

public class PrimitiveProcedures {
	private static Boolean toBoolean(boolean a) {
		return a? Boolean.TrueValue : Boolean.FalseValue;
	}
	
	private static SchemeObject pcar(SchemeObject p) {
		return ((Pair)p).car();
	}

	private static SchemeObject pcdr(SchemeObject p) {
		return ((Pair)p).cdr();
	}
	
	//Basic number arithmetic
	private static Procedure add = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			int result = 0;
			
			//Add all the numbers in the list
			while (!(args instanceof EmptyList)) {
				result += ((Fixnum)((Pair)args).car()).getNumber();
				args = ((Pair)args).cdr();
			}
			
			return EvaluationResult.makeFinished(new Fixnum(result));
		}
	};

	private static Procedure subtract = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			int result = ((Fixnum)((Pair)args).car()).getNumber();
			args = ((Pair)args).cdr();
			
			//Subtract all the numbers in the list from the first number
			while (!(args instanceof EmptyList)) {
				result -= ((Fixnum)((Pair)args).car()).getNumber();
				args = ((Pair)args).cdr();
			}
			
			return EvaluationResult.makeFinished(new Fixnum(result));
		}
	};

	private static Procedure multiply = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			//As a side effect, calling (*) returns 1.			
			int result = 1;
			
			//Multiply all the numbers in the list
			while (!(args instanceof EmptyList)) {
				result *= ((Fixnum)((Pair)args).car()).getNumber();
				args = ((Pair)args).cdr();
			}
			
			return EvaluationResult.makeFinished(new Fixnum(result));
		}
	};
	
	private static Procedure quotient = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			Pair pargs = (Pair)args;
			
			return EvaluationResult.makeFinished(
					new Fixnum(((Fixnum)pargs.car()).getNumber() /
					((Fixnum)((Pair)(pargs.cdr())).car()).getNumber()));
		}
	};	
	
	private static Procedure remainder = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			Pair pargs = (Pair)args;
			
			return EvaluationResult.makeFinished(
					new Fixnum(((Fixnum)pargs.car()).getNumber() %
					((Fixnum)((Pair)(pargs.cdr())).car()).getNumber()));
		}
	};	
	
	private static Procedure equals = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			//If any two values are not equal to each other, return false
			while (!(((Pair)((Pair)args)).cdr() instanceof EmptyList)) {
				//oh god the brackets...
				if (!(((Fixnum)(((Pair)args).car())).getNumber()
						 == ((Fixnum)(((Pair)((Pair)args).cdr()).car())).getNumber())) {
					return EvaluationResult.makeFinished(Boolean.FalseValue);
				}
				args = ((Pair)args).cdr();
			}
			
			return EvaluationResult.makeFinished(Boolean.TrueValue);
		}
	};	
	
	private static Procedure greater = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			//If a value is not greater than the one before it, return false
			while (!(((Pair)((Pair)args)).cdr() instanceof EmptyList)) {
				if (!(((Fixnum)(((Pair)args).car())).getNumber()
						 > ((Fixnum)(((Pair)((Pair)args).cdr()).car())).getNumber())) {
					return EvaluationResult.makeFinished(Boolean.FalseValue);
				}
				args = ((Pair)args).cdr();
			}
			
			return EvaluationResult.makeFinished(Boolean.TrueValue);
		}
	};	
	
	private static Procedure smaller = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			//If a value is not smaller than the one before it, return false
			while (!(((Pair)((Pair)args)).cdr() instanceof EmptyList)) {
				if (!(((Fixnum)(((Pair)args).car())).getNumber()
						 < ((Fixnum)(((Pair)((Pair)args).cdr()).car())).getNumber())) {
					return EvaluationResult.makeFinished(Boolean.FalseValue);
				}
				args = ((Pair)args).cdr();
			}
			
			return EvaluationResult.makeFinished(Boolean.TrueValue);
		}
	};	
	
	//Various procedures for checking the type of an object
	
	private static Procedure isNull = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof EmptyList));
		}
	};
	
	private static Procedure isBoolean = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof Boolean));
		}
	};
	
	private static Procedure isSymbol = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof Symbol));
		}
	};
	
	private static Procedure isString = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof StringLiteral));
		}
	};
	
	private static Procedure isInteger = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof Fixnum));
		}
	};	

	private static Procedure isChar = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof Character));
		}
	};
	
	private static Procedure isPair = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof Pair));
		}
	};

	private static Procedure isProcedure = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args) instanceof Procedure));
		}
	};
	
	//Procedures for type conversion
	private static Procedure charToInt = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					new Fixnum((int)((Character)pcar(args)).getValue()));
		}
	};
	
	private static Procedure intToChar = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					new Character((char)((Fixnum)pcar(args)).getNumber()));
		}
	};
	
	private static Procedure numberToString = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					new StringLiteral(((Fixnum)pcar(args)).print()));
		}
	};
	
	private static Procedure stringToNumber = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			int result;
			try {
				result = Integer.parseInt(((StringLiteral)pcar(args)).getValue());
			} catch (NumberFormatException e) {
				throw new EvalException("Unexpected character in the number!");
			}
			return EvaluationResult.makeFinished(new Fixnum(result));
		}
	};
	
	private static Procedure symbolToString = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					new StringLiteral(((Symbol)pcar(args)).getValue()));
		}
	};	

	private static Procedure stringToSymbol = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			SchemeObject result;
			try {
				result = Symbol.makeSymbol(((StringLiteral)pcar(args)).getValue());
			} catch (ParseException e) {
				throw new EvalException("Unexpected character in the symbol!");
			}
			return EvaluationResult.makeFinished(result);
		}
	};
	
	//Procedures for working with pairs and lists
	private static Procedure cons = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(new Pair(pcar(args), pcar(pcdr(args))));
		}
	};
	
	private static Procedure car = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(pcar(pcar(args)));
		}
	};

	private static Procedure cdr = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(pcdr(pcar(args)));
		}
	};
	
	private static Procedure setCar = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			((Pair)pcar(args)).setCar(pcar(pcdr(args)));
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
	};
	
	private static Procedure setCdr = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			((Pair)pcar(args)).setCdr(pcar(pcdr(args)));
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
	};
	
	private static Procedure list = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(args);
		}
	};
	
	private static Procedure eq = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(toBoolean(pcar(args).equals(pcar(pcdr(args)))));
		}
	};
	
	public static void installProcedures(Environment env) {
		env.defineVariable(Symbol.unsafeMakeSymbol("null?"), isNull);
		env.defineVariable(Symbol.unsafeMakeSymbol("boolean?"), isBoolean);
		env.defineVariable(Symbol.unsafeMakeSymbol("integer?"), isInteger);
		env.defineVariable(Symbol.unsafeMakeSymbol("symbol?"), isSymbol);
		env.defineVariable(Symbol.unsafeMakeSymbol("char?"), isChar);
		env.defineVariable(Symbol.unsafeMakeSymbol("string?"), isString);
		env.defineVariable(Symbol.unsafeMakeSymbol("pair?"), isPair);
		env.defineVariable(Symbol.unsafeMakeSymbol("procedure?"), isProcedure);
		
		env.defineVariable(Symbol.unsafeMakeSymbol("char->integer"), charToInt);
		env.defineVariable(Symbol.unsafeMakeSymbol("integer->char"), intToChar);
		env.defineVariable(Symbol.unsafeMakeSymbol("number->string"), numberToString);
		env.defineVariable(Symbol.unsafeMakeSymbol("string->number"), stringToNumber);
		env.defineVariable(Symbol.unsafeMakeSymbol("symbol->string"), symbolToString);
		env.defineVariable(Symbol.unsafeMakeSymbol("string->symbol"), stringToSymbol);
		
		env.defineVariable(Symbol.unsafeMakeSymbol("+"), add);
		env.defineVariable(Symbol.unsafeMakeSymbol("-"), subtract);
		env.defineVariable(Symbol.unsafeMakeSymbol("*"), multiply);
		env.defineVariable(Symbol.unsafeMakeSymbol("quotient"), quotient);
		env.defineVariable(Symbol.unsafeMakeSymbol("remainder"), remainder);
		env.defineVariable(Symbol.unsafeMakeSymbol("="), equals);
		env.defineVariable(Symbol.unsafeMakeSymbol(">"), greater);
		env.defineVariable(Symbol.unsafeMakeSymbol("<"), smaller);
		
		env.defineVariable(Symbol.unsafeMakeSymbol("cons"), cons);
		env.defineVariable(Symbol.unsafeMakeSymbol("car"), car);
		env.defineVariable(Symbol.unsafeMakeSymbol("cdr"), cdr);
		env.defineVariable(Symbol.unsafeMakeSymbol("set-car!"), setCar);
		env.defineVariable(Symbol.unsafeMakeSymbol("set-cdr!"), setCdr);
		env.defineVariable(Symbol.unsafeMakeSymbol("list"), list);
		
		env.defineVariable(Symbol.unsafeMakeSymbol("eq?"), eq);
	}
}
