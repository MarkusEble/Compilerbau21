import java.io.ByteArrayOutputStream;

import compiler.FileReaderIntf;
import test.TestCaseIntf;

public class FunctionTest implements TestCaseIntf {

	@Override
	public String executeTest(FileReaderIntf fileReader) throws Exception {
		compiler.CompileEnv compiler = new compiler.CompileEnv(fileReader);
		compiler.compile();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		compiler.execute(outStream);
		String output = outStream.toString("UTF-8");
		return output;
	}

}