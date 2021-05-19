package compilerbau21;

import compilerbau21.compiler.*;

import java.io.FileInputStream;

public class LexerManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);
		FileReaderIntf fileReader = new FileReader(inputStream);
		LexerIntf lexer = new Lexer(fileReader);
        Token currentToken;
        StringBuilder output = new StringBuilder();
        do {
        	currentToken = lexer.lookAheadToken();
        	output.append(currentToken.toString()).append("\n");
        	lexer.advance();
        } while (currentToken.m_type != Token.Type.EOF);
        System.out.print(output);
		System.out.println("END");
	}

}
