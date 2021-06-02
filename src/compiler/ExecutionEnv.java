package compiler;

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
    private FunctionTable m_functionTable;
    private boolean m_trace;

    public ExecutionEnv(FunctionTable functionTable, SymbolTable symbolTable, OutputStream outStream, boolean trace) throws Exception {
		m_symbolTable = symbolTable;
		m_numberStack = new Stack<Integer>();
		m_executionStack = new Stack<Iterator<InstrIntf>>();
		m_outStream = new OutputStreamWriter(outStream, "UTF-8");
		m_functionTable = functionTable;
		m_trace = trace;
	}
	
	public void pushNumber(int number) {
		m_numberStack.push(new Integer(number));
	}
	
	public int popNumber() {
		Integer number = m_numberStack.pop();
		return number.intValue();
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
            if (m_trace) {
                nextInstr.trace(getOutputStream());
                m_outStream.flush();
            }
            nextInstr.execute(this);
        }
    }
	
	public OutputStreamWriter getOutputStream() {
		return m_outStream;
	}

    @Override
    public void pushFunction(FunctionInfo f) {
        this.m_executionStack.push(m_instrIter);
        setInstrIter(f.m_body.getIterator());
    }

    @Override
    public void popFunction() {
    	setInstrIter(this.m_executionStack.pop());
    }
}
