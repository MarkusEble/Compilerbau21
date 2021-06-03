import compiler.CompileEnv;

public class FunctionManualMain {
	
	public static void main(String[] args) throws Exception {
        System.out.println("BEGIN");
        CompileEnv compiler = new CompileEnv("ProgrammFctManualInput.txt");
        compiler.compile();
        compiler.execute(System.out);
        System.out.println("END");
	}

}