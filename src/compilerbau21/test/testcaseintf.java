package compilerbau21.test;

public interface TestCaseIntf {
	
	// executes compilerbau21.test case on
	String executeTest(compilerbau21.compiler.FileReaderIntf fileReader) throws Exception;
}
