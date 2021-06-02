import compiler.FileReader;

public class FunctionMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		test.TestSuiteIntf test = new test.TestSuite(FileReader.fromFileName("ProgrammFctTestInput.txt"), new FunctionTest());
		test.testRun();
		System.out.println("END");
	}
	
}