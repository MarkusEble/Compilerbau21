package compiler;

import java.io.OutputStreamWriter;
import java.util.Iterator;

public interface ExecutionEnvIntf {
	/**
	 *  push temporary on value stack
	 */
	public void pushNumber(int number);
    /**
     *  pop (consume) temporary from value stack	
     */
	public int popNumber();
	/**
	 *  push function on execution stack
	 */
	public void pushFunction(FunctionInfo function);
	/**
	 * pop function from execution stack
	 */
	public void popFunction();
	/**
	 *  get symbol from symbol table
	 */
	public Symbol getSymbol(String symbolName);
    /**
     *  set instruction iterator to the given block
     */
	public void setInstrIter(Iterator<InstrIntf> instrIter);
    /**
     *  execute instruction list	
     */
	public void execute(Iterator<InstrIntf> instrIter) throws Exception;
	/**
	 *  get output stream
	 */
	public OutputStreamWriter getOutputStream();
}
