package compilerbau21.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public abstract class TestSuiteIntf {
    compilerbau21.compiler.FileReaderIntf m_fileReader;
    TestCaseIntf m_testCase;

    // read and execute a sequence of compilerbau21.test cases
    abstract void readAndExecuteTestSequence() throws Exception;

    // read and execute one compilerbau21.test case
    abstract void readAndExecuteTestCase() throws Exception;

    // read the content of a compilerbau21.test section (after $IN or $OUT)
    abstract String readTestContent() throws Exception;

    // check that input is $IN and consume it
    abstract void readDollarIn() throws Exception;

    // check that output is $OUT and consume it
    abstract void readDollarOut() throws Exception;

    // creates a compilerbau21.test object from an input file
    TestSuiteIntf(compilerbau21.compiler.FileReaderIntf fileReader, TestCaseIntf testCase) {
        m_fileReader = fileReader;
        m_testCase = testCase;
    }

    // execute a compilerbau21.test case with the given input and compare result with expected output
    void executeTestCase(String input, String expectedOutput) throws Exception {
        String result = "";
        try {
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            compilerbau21.compiler.FileReaderIntf fileReader = new compilerbau21.compiler.FileReader(inputStream);
            result = m_testCase.executeTest(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result.equals(expectedOutput)) {
            System.out.println("TEST SUCCEEDED");
            System.out.print(input);
            System.out.println("ACTUAL OUTPUT");
            System.out.print(result);
        } else {
            System.out.println("TEST FAILED");
            System.out.print(input);
            System.out.println("EXPECTED OUTPUT");
            System.out.print(expectedOutput);
            System.out.println("ACTUAL OUTPUT");
            System.out.print(result);
            throw new Exception("TestFailure");
        }
    }

    // public interface to execute all tests
    public void testRun() throws Exception {
        readAndExecuteTestSequence();
    }

}
