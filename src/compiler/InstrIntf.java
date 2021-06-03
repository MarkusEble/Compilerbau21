package compiler;

import java.io.OutputStreamWriter;

public interface InstrIntf {
	/**
	 * execute this instruction
	 */
	public void execute(ExecutionEnvIntf env);
	/**
	 * trace this instruction
	 */
	public void trace(OutputStreamWriter os) throws Exception;

}
