package compilerbau21.compiler;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Stack;

public class ExecutionEnv implements ExecutionEnvIntf {
    private SymbolTable m_symbolTable;
    private Stack<Integer> m_numberStack;
    private Stack<Iterator<InstrIntf>> m_executionStack;
    private Iterator<InstrIntf> m_instrIter;
    private OutputStreamWriter m_outStream;
    private final boolean trace;

    public ExecutionEnv(SymbolTable symbolTable, OutputStream outStream, boolean trace) throws Exception {
        m_symbolTable = symbolTable;
        m_numberStack = new Stack<>();
        m_executionStack = new Stack<>();
        m_outStream = new OutputStreamWriter(outStream, "UTF-8");
        this.trace = trace;
    }

    public void pushNumber(int number) {
        m_numberStack.push(number);
    }

    public int popNumber() {
        return m_numberStack.pop();
    }

    public Symbol getSymbol(String symbolName) {
        return m_symbolTable.getSymbol(symbolName);
    }

    public void setInstrIter(Iterator<InstrIntf> instrIter) { // instrIter == program counter
        m_instrIter = instrIter;
    }

    public void execute(Iterator<InstrIntf> instrIter) throws Exception {
        m_instrIter = instrIter;
        while (m_instrIter.hasNext()) {
            InstrIntf nextInstr = m_instrIter.next();
            if (trace) {
                nextInstr.trace(getOutputStream());
                m_outStream.flush();
            }
            nextInstr.execute(this);
        }
    }

    public OutputStreamWriter getOutputStream() {
        return m_outStream;
    }
}
