package compilerbau21.test;

public class LexerTest implements compilerbau21.test.TestCaseIntf {


	public String executeTest(compilerbau21.compiler.FileReaderIntf fileReader) throws Exception {
        compilerbau21.compiler.Lexer lexer = new compilerbau21.compiler.Lexer(fileReader);
        compilerbau21.compiler.Token currentToken;
        String output = new String();
        do {
        	currentToken = lexer.lookAheadToken();
        	output += currentToken.toString() + "\n";
        	lexer.advance();
        } while (currentToken.m_type != compilerbau21.compiler.Token.Type.EOF);
		return output;
	}
}
