package compilerbau21;

import compilerbau21.compiler.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class VariableManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);
		FileReaderIntf fileReader = new FileReader(inputStream);
		LexerIntf lexer = new Lexer(fileReader);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
       // StmtReaderIntf stmtReader = new StmtReader(lexer, outStream);
		//stmtReader.getStmtList();
       // String output = outStream.toString("UTF-8");
       // System.out.println(output);
		System.out.println("END");
	}

}
