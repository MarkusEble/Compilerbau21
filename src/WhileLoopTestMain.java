import compiler.FileReader;
import test.TestSuite;
import test.TestSuiteIntf;

public class WhileLoopTestMain {
    public static void main(String[] args) throws Exception {
        System.out.println("BEGIN");
        TestSuiteIntf test = new TestSuite(FileReader.fromFileName("Compilerbau21/WhileLoopTestInput.txt"), new WhileLoopTest());
        test.testRun();
        System.out.println("END");
    }
}
