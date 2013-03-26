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
	
	private static Procedure interEnv = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(Environment.getGlobalEnvironment());
		}
	};
	
	private static Procedure nullEnv = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(EmptyList.makeEmptyList());
		}
	};
	
	private static Procedure initialEnv = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(Environment.getInitialEnvironment());
		}
	};
	
	private static Procedure load = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			//Load the Scheme code in the file into the REPL.
			String pathname = ((StringLiteral)((Pair)args).car()).getValue();
			
			InputPort inputPort = new InputPort(pathname);
			
			SchemeObject result = Symbol.loadedSymbol;
			
			//Read an object, stop if reached the end of file, evaluate the object.
			while(true) {
				SchemeObject readObject = inputPort.read();
				if (readObject instanceof EOFObject) 
					return EvaluationResult.makeFinished(result);
				
				//Return the last object evaluated.
				result = readObject.evaluate(environment);
			}
		}
	};
	
	/**
	 * Returns the port from the arguments or stdin if there are none.
	 */
	private static InputPort getInputPortFromArgs(SchemeObject args) {
		if (args instanceof EmptyList) return InputPort.getStdin();
		
		return (InputPort)pcar(args);
	}
	
	private static Procedure read = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(getInputPortFromArgs(args).read());
		}
	};
	
	private static Procedure readChar = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(getInputPortFromArgs(args).readChar());
		}
	};	
	
	private static Procedure peekChar = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(getInputPortFromArgs(args).peekChar());
		}
	};
	
	private static Procedure isInputPort = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					toBoolean((((Pair)args).car()) instanceof InputPort));
		}
	};
	
	private static Procedure openInputPort = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			StringLiteral path = (StringLiteral)pcar(args);
			return EvaluationResult.makeFinished(new InputPort(path.getValue()));
		}
	};
	
	private static Procedure closeInputPort = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			((InputPort)pcar(args)).closeFile();
			
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
	};
	
	private static Procedure isEOFObject = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					toBoolean(pcar(args) instanceof EOFObject));
		}
	};
	
	/**
	 * Returns either the output port or the stdout (if there is only one argument)
	 */
	
	private static OutputPort getOutputPortFromArgs(SchemeObject args) {
		if (pcdr(args) instanceof EmptyList) return OutputPort.getStdout();
		
		return (OutputPort)pcar(pcdr(args));
	}
	
	private static Procedure write = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			getOutputPortFromArgs(args).write(pcar(args));
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
	};

	private static Procedure writeChar = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			getOutputPortFromArgs(args).writeChar(((Character)pcar(args)).getValue());
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
	};

	private static Procedure isOutputPort = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			return EvaluationResult.makeFinished(
					toBoolean((((Pair)args).car()) instanceof OutputPort));
		}
	};
	
	private static Procedure openOutputPort = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			StringLiteral path = (StringLiteral)pcar(args);
			return EvaluationResult.makeFinished(new OutputPort(path.getValue()));
		}
	};
	
	private static Procedure closeOutputPort = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			((OutputPort)pcar(args)).closeFile();
			
			return EvaluationResult.makeFinished(Symbol.okSymbol);
		}
	};
	
	private static Procedure error = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			while (!(args instanceof EmptyList)) {
				write.evalProcedure(environment, new Pair(pcar(args), EmptyList.makeEmptyList()));
				args = pcdr(args);
			}
			
			return EvaluationResult.makeFinished(Symbol.exitingSymbol);
		}
	};
	
	//eval and apply are dummy functions that are not supposed to be evaluated,
	//they are only put here so that things like (define a apply) work.

	public static Procedure eval = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			throw new EvalException("Error: the body of eval is not supposed to be evaluated!");
		}
	};
	
	public static Procedure apply = new Procedure() {
		public EvaluationResult evalProcedure(Environment environment,
				SchemeObject args) throws EvalException {
			throw new EvalException("Error: the body of apply is not supposed to be evaluated!");
		}
	};	
	
	public static void installProcedures(SchemeObject env) {
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("null?"), isNull);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("boolean?"), isBoolean);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("integer?"), isInteger);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("symbol?"), isSymbol);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("char?"), isChar);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("string?"), isString);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("pair?"), isPair);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("procedure?"), isProcedure);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("char->integer"), charToInt);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("integer->char"), intToChar);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("number->string"), numberToString);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("string->number"), stringToNumber);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("symbol->string"), symbolToString);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("string->symbol"), stringToSymbol);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("+"), add);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("-"), subtract);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("*"), multiply);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("quotient"), quotient);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("remainder"), remainder);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("="), equals);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol(">"), greater);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("<"), smaller);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("cons"), cons);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("car"), car);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("cdr"), cdr);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("set-car!"), setCar);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("set-cdr!"), setCdr);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("list"), list);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("eq?"), eq);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("interaction-environment"), interEnv);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("null-environment"), nullEnv);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("environment"), initialEnv);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("load"), load);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("read"), read);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("read-char"), readChar);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("peek-char"), peekChar);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("input-port?"), isInputPort);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("open-input-file"), openInputPort);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("close-input-file"), closeInputPort);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("eof-object?"), isEOFObject);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("write"), write);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("write-char"), writeChar);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("output-port?"), isOutputPort);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("open-output-file"), openOutputPort); 
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("close-output-file"), closeOutputPort);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("error"), error);
		
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("eval"), eval);
		Environment.defineVariable(env, Symbol.unsafeMakeSymbol("apply"), apply);
	}
}
