package compilerbau21.compiler.logicalexpression.instructions;

import compilerbau21.compiler.ExecutionEnvIntf;
import compilerbau21.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class Or implements InstrIntf {

    /**
     * execute this instruction
     *
     * @param env
     */
    @Override
    public void execute(ExecutionEnvIntf env) {
        // pop operand 1 from value stack
        int op1 = env.popNumber();
        // pop operand 0 from value stack
        int op0 = env.popNumber();
        // execute OR
        if (op0 < 0 || op1 < 0)
            throw new IllegalArgumentException("One or both arguements were not allowed while parsing logical and.");

        if (op0 > 0 || op1 > 0) {
            env.pushNumber(1); // true
        } else {
            env.pushNumber(0); // false
        }
    }

    /**
     * trace this instruction
     *
     * @param os
     */
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("OR\n");
    }
}
