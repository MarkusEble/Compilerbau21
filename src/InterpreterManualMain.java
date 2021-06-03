
import compiler.CompileEnv;

public class InterpreterManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		CompileEnv compiler = new CompileEnv(args[0]);
		compiler.compile();
		//compiler.dump(System.out);
		compiler.execute(System.out);
		System.out.println("END");
	}

}
