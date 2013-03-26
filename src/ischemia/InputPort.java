package ischemia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputPort extends SchemeObject {
	private String filename;
	private BufferedReader fileReader;
	
	protected EvaluationResult eval(Environment environment)
			throws EvalException {
		throw new EvalException("Cannot evaluate an input port!");
	}

	public String print() {
		return filename;
	}
	
	public SchemeObject readChar() throws EvalException {
		int nextChar;
		try {
			nextChar = fileReader.read();
		} catch (IOException e) {
			throw new EvalException("Error: cannot read the file!");
		}
		
		if (nextChar == -1) return EOFObject.makeEOFObject();
		
		return new Character((char)nextChar);
	}
	
	public SchemeObject peekChar() throws EvalException {
		int nextChar;
		
		try {
			fileReader.mark(1);
			nextChar = fileReader.read();
			fileReader.reset();
		} catch (IOException e) {
			throw new EvalException("Error: cannot read the file!");
		}
		
		if (nextChar == -1) return EOFObject.makeEOFObject();
		
		return new Character((char)nextChar);
	}
	
	public SchemeObject read() throws EvalException {
		SchemeObject readObj;
		try {
			readObj = SchemeReader.read(fileReader);
		} catch (ParseException e) {
			throw new EvalException("Error while parsing the file!");
		}
		
		if (readObj == null) return EOFObject.makeEOFObject();
		
		return readObj;
	}
	
	public InputPort(String filename) throws EvalException {
		try {
			fileReader = new BufferedReader(new FileReader(filename));
		} catch (IOException e) {
			throw new EvalException("Cannot open the file!");
		}
	}
	
	public void closeFile() throws EvalException {
		try {
			fileReader.close();
		} catch (IOException e) {
			throw new EvalException("Error: cannot close the file!");
		}
	}

}
