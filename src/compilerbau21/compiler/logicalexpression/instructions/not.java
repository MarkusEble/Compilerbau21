package compilerbau21.compiler.logicalexpression.instructions;

import compilerbau21.compiler.ExecutionEnvIntf;
import compilerbau21.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class Not implements InstrIntf {
    /**
     * execute this instruction
     *
     * @param env
     */
    @Override
    public void execute(ExecutionEnvIntf env) {
        int op0 = env.popNumber();
        if (op0 < 0)
            throw new IllegalArgumentException("Argument to invert cannot be less than zero");

        if (op0 == 0) {
            env.pushNumber(1);
        } else {
            env.pushNumber(0);
        }
    }

    /**
     * trace this instruction
     *
     * @param os
     */
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("NOT\n");
    }
}
