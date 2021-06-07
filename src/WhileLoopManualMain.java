import compiler.CompileEnv;

public class WhileLoopManualMain {
    public static void main(String[] args) throws Exception{
        System.out.println("BEGIN");
        CompileEnv compiler = new CompileEnv("WhileLoopManualInput.txt");
        compiler.compile();
        compiler.execute(System.out);
        System.out.println("END");

    }
}
