package compilerbau21.test;

import compilerbau21.compiler.CompileEnv;
import compilerbau21.compiler.FileReaderIntf;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class LogicalExpressionsTest implements TestCaseIntf {
    @Override
    public String executeTest(FileReaderIntf fileReader) throws Exception {
        CompileEnv compiler = new CompileEnv(fileReader, false);
        compiler.compile();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        compiler.execute(outStream);
        return outStream.toString(StandardCharsets.UTF_8);
    }
}
