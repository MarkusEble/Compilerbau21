package compilerbau21.test;


public class TestSuite extends TestSuiteIntf {
  
    public TestSuite(compilerbau21.compiler.FileReaderIntf fileReader, TestCaseIntf testCase) {
    	super(fileReader, testCase);
    }

	public void readAndExecuteTestSequence() throws Exception {
		while (m_fileReader.lookAheadChar() != 0) {
			readAndExecuteTestCase();
		}
	}
	
	public void readAndExecuteTestCase() throws Exception {
		readDollarIn();
		String input = readTestContent();
		readDollarOut();
		String output = readTestContent();
		executeTestCase(input, output);
	}
	
	public String readTestContent() throws Exception {
		String testContent = "";
		while (m_fileReader.lookAheadChar() != '$' && m_fileReader.lookAheadChar() != 0) {
			testContent += m_fileReader.lookAheadChar();
			m_fileReader.advance();
		}
		return testContent;
	}
	
	public void readDollarIn() throws Exception {
		m_fileReader.expect('$');
		m_fileReader.expect('I');
		m_fileReader.expect('N');
		m_fileReader.expect('\n');
	}
	
	public void readDollarOut() throws Exception {
		m_fileReader.expect('$');
		m_fileReader.expect('O');
		m_fileReader.expect('U');
		m_fileReader.expect('T');
		m_fileReader.expect('\n');
	}
}
