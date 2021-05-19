package compilerbau21;

import compilerbau21.compiler.FileReader;
import compilerbau21.test.VariableTest;

public class VariableTestMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		compilerbau21.test.TestSuiteIntf test = new compilerbau21.test.TestSuite(FileReader.fromFileName(args[0]), new VariableTest());
		test.testRun();
		System.out.println("END");
	}

}
