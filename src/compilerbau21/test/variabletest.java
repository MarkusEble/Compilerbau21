package compilerbau21.test;

import java.io.ByteArrayOutputStream;

public class VariableTest implements compilerbau21.test.TestCaseIntf {

	public String executeTest(compilerbau21.compiler.FileReaderIntf fileReader) throws Exception {
		compilerbau21.compiler.LexerIntf lexer = new compilerbau21.compiler.Lexer(fileReader);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //compilerbau21.compiler.StmtReader stmt = new compilerbau21.compiler.StmtReader(lexer, outStream);
        //stmt.getStmtList();
        String output = outStream.toString("UTF-8");
        return output;
	}	
}
