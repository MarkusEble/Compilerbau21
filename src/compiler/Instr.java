package compiler;

import java.io.OutputStreamWriter;
import java.io.IOException;

public abstract class Instr implements InstrIntf {

	public static class PushNumberInstr implements InstrIntf {
		int m_number;
		
		public PushNumberInstr(int number) {
			m_number = number;
		}

		public void execute(ExecutionEnvIntf env) {
			env.pushNumber(m_number);
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("NUMBER: ");
			os.write(Integer.toString(m_number));
			os.write("\n");
		}

	}

	public static class VariableInstr implements InstrIntf {
		String m_name;
		
		public VariableInstr(String name) {
			m_name = name;
		}

		public void execute(ExecutionEnvIntf env) {
			//Symbol var = m_symbolTable.getSymbol(token.m_stringValue);
			Symbol var = env.getSymbol(m_name);
			//number = var.m_number;
			env.pushNumber(var.m_number);
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("VARIABLE: ");
			os.write(m_name);
			os.write("\n");
		}
	}

	public static class AssignInstr implements InstrIntf {
		String m_name;
		
		public AssignInstr(String name) {
			m_name = name;
		}

		public void execute(ExecutionEnvIntf env) {
			// int number = m_exprReader.getExpr();
			int number = env.popNumber();
			// Symbol var = m_symbolTable.createSymbol(varName);
			Symbol var = env.getSymbol(m_name);
			var.m_number = number;
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("ASSIGN: ");
			os.write(m_name);
			os.write("\n");
		}
	}

	public static class PrintInstr implements InstrIntf {
		
		public PrintInstr() {
		}

		public void execute(ExecutionEnvIntf env) {
			int op = env.popNumber();
			try {
				env.getOutputStream().write(Integer.toString(op));
				env.getOutputStream().write('\n');
				env.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("PRINT\n");
		}
	}

	public static class AddInstr implements InstrIntf {
		
		public AddInstr() {
		}

		public void execute(ExecutionEnvIntf env) {
			// pop operand 1 from value stack
			int op1 = env.popNumber();
			// pop operand 0 from value stack
			int op0 = env.popNumber();
			// execute add
			int result = op0 + op1;
			// push result onto value stack
			env.pushNumber(result);
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("ADD\n");
		}
	}

	public static class SubInstr implements InstrIntf {
		
		public SubInstr() {
		}

		public void execute(ExecutionEnvIntf env) {
			int op1 = env.popNumber();
			int op0 = env.popNumber();
			int result = op0 - op1;
			env.pushNumber(result);
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("SUB\n");
		}
	}

	public static class UnaryMinusInstr implements InstrIntf {
		
		public UnaryMinusInstr() {
		}
        // Runtime des Programms
		public void execute(ExecutionEnvIntf env) {
			int op0 = env.popNumber();
			int result = -op0;
			env.pushNumber(result);
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("MINUS\n");
		}
	}

	public static class MultiplyInstr extends Instr {

		public MultiplyInstr() {
		}

		@Override
		public void execute(ExecutionEnvIntf env) {
			// pop operand 1 from value stack
			int op1 = env.popNumber();
			// pop operand 0 from value stack
			int op0 = env.popNumber();
			// execute mul
			int result = op0 * op1;
			// push result onto value stack
			env.pushNumber(result);
		}

		@Override
		public void trace(OutputStreamWriter os) throws Exception {
			os.write("MUL\n");
		}

	}

	public static class DivisionInstr extends Instr {

		public DivisionInstr() {
		}

		@Override
		public void execute(ExecutionEnvIntf env) {
			// pop operand 1 from value stack
			int op1 = env.popNumber();
			// pop operand 0 from value stack
			int op0 = env.popNumber();
			// execute div
			int result = op0 / op1;
			// push result onto value stack
			env.pushNumber(result);
		}

		@Override
		public void trace(OutputStreamWriter os) throws Exception {
			os.write("DIV\n");
		}

	}

	public static class JumpInstr implements InstrIntf {
		InstrBlock m_target;
		
		public JumpInstr(InstrBlock target) {
			m_target = target;
		}

		public void execute(ExecutionEnvIntf env) {
			env.setInstrIter(m_target.getIterator());
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("JUMP\n");
		}
	}

	public static class JumpCondInstr implements InstrIntf {
		InstrBlock m_targetTrue;
		InstrBlock m_targetFalse;
		
		public JumpCondInstr(InstrBlock targetTrue, InstrBlock targetFalse) {
			m_targetTrue = targetTrue;
			m_targetFalse = targetFalse;
		}

		public void execute(ExecutionEnvIntf env) {
			int condition = env.popNumber();
			if (condition != 0) {
				env.setInstrIter(m_targetTrue.getIterator());
			} else {
				env.setInstrIter(m_targetFalse.getIterator());
			}
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("JUMP COND\n");
		}
	}

	public static class ReturnInstr implements InstrIntf {
		String m_name;

		public ReturnInstr() {
		}

		public void execute(ExecutionEnvIntf env) {
			env.popFunction();
		}

		public void trace(OutputStreamWriter os) throws Exception {
			os.write("RETURN: ");
			os.write(m_name);
			os.write("\n");
		}
	}

}
