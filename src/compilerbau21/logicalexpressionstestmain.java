package compilerbau21;

import compilerbau21.compiler.FileReader;
import compilerbau21.test.LogicalExpressionsTest;
import compilerbau21.test.TestSuite;
import compilerbau21.test.TestSuiteIntf;

public class LogicalExpressionsTestMain {

    public static void main(String[] args) throws Exception {
        System.err.println("BEGIN");
        TestSuiteIntf test = new TestSuite(FileReader.fromFileName("LogicalExpressionsTestInput.txt"), new LogicalExpressionsTest());
        test.testRun();
        System.err.println("END");
    }
}
