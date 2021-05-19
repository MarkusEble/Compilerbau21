package compilerbau21.test;

import compilerbau21.compiler.FileReader;

public class LexerTestMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		compilerbau21.test.TestSuiteIntf test = new compilerbau21.test.TestSuite(FileReader.fromFileName(args[0]), new LexerTest());
		test.testRun();
		System.out.println("END");
	}

}

