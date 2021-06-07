import compiler.CompileEnv;
import compiler.FileReaderIntf;
import test.TestCaseIntf;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class WhileLoopTest implements TestCaseIntf {

    @Override
    public String executeTest(FileReaderIntf fileReader) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        CompileEnv compiler = new CompileEnv(fileReader, false);
        compiler.compile();
        compiler.execute(outStream);
        return outStream.toString(String.valueOf(StandardCharsets.UTF_8));
    }
}
