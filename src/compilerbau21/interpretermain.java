package compilerbau21;

import compilerbau21.compiler.FileReader;
import compilerbau21.test.InterpreterTest;

public class InterpreterMain {

	public static void main(String[] args) throws Exception {
		System.err.println("BEGIN");
		compilerbau21.test.TestSuiteIntf test = new compilerbau21.test.TestSuite(FileReader.fromFileName(args[0]), new InterpreterTest());
		test.testRun();
		System.err.println("END");
	}

}

