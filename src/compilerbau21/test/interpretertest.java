package compilerbau21.test;

import compilerbau21.compiler.CompileEnv;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class InterpreterTest implements compilerbau21.test.TestCaseIntf {

    public String executeTest(compilerbau21.compiler.FileReaderIntf fileReader) throws Exception {
        CompileEnv compiler = new CompileEnv(fileReader, false);
        compiler.compile();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        compiler.execute(outStream);
        return outStream.toString(StandardCharsets.UTF_8);
    }
}
