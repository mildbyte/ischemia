package ischemia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class OutputPort extends SchemeObject {
	private String filename;
	private BufferedWriter fileWriter;
	
	private static OutputPort stdout;
	
	public static OutputPort getStdout() {
		if (stdout == null) {
			stdout = new OutputPort();
			stdout.fileWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		}
		
		return stdout;
	}
	
	private OutputPort() {}
	
	protected EvaluationResult eval(Environment environment)
			throws EvalException {
		throw new EvalException("Cannot evaluate an output port!");
	}

	public String print() {
		return filename;
	}
	
	public void writeChar(char c) throws EvalException {
		try {
			fileWriter.write(c);
			fileWriter.flush();
		} catch (IOException e) {
			throw new EvalException("Error: cannot write into the file!");
		}
	}
	
	public void write(SchemeObject obj) throws EvalException {
		try {
			fileWriter.write(obj.print());
			fileWriter.write('\n');
			fileWriter.flush();
		} catch (IOException e) {
			throw new EvalException("Error while writing the object into the file!");
		}
	}
	
	public OutputPort(String filename) throws EvalException {
		try {
			fileWriter = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			throw new EvalException("Cannot open the file!");
		}
	}
	
	public void closeFile() throws EvalException {
		try {
			fileWriter.close();
		} catch (IOException e) {
			throw new EvalException("Error: cannot close the file!");
		}
	}
}
