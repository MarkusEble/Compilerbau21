package compilerbau21.compiler.logicalexpression.instructions;

import compilerbau21.compiler.ExecutionEnvIntf;
import compilerbau21.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class BitNot implements InstrIntf {
    /**
     * execute this instruction
     *
     * @param env
     */
    @Override
    public void execute(ExecutionEnvIntf env) {
        int op0 = env.popNumber();
        env.pushNumber(~op0);
    }

    /**
     * trace this instruction
     *
     * @param os
     */
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("BITNOT\n");
    }
}
