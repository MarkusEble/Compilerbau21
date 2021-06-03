package compiler;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class CompileEnv implements CompileEnvIntf {
    private FileReaderIntf m_fileReader;
    private SymbolTable m_symbolTable;
    private FunctionTable m_functionTable;
    private Lexer m_lexer;
    private StmtReaderIntf m_stmtReader;
    private InstrBlock m_entry;
    private InstrBlock m_currentBlock;
    private ArrayList<InstrBlock> m_blockList;
    private final boolean m_trace;
    private int m_nextBlockId = 0;


    // cool kids would use a dedicated compile env config class for that...
    public CompileEnv(String fileName, boolean trace) throws Exception {
        m_trace = trace;
        FileInputStream inputStream = new FileInputStream(fileName);
        m_fileReader = new FileReader(inputStream);
        m_symbolTable = new SymbolTable();
        m_functionTable = new FunctionTable();
        m_lexer = new Lexer(m_fileReader);
        m_stmtReader = new StmtReader(m_lexer, this);
        m_blockList = new ArrayList<InstrBlock>();
    }

    public CompileEnv(FileReaderIntf fileReader, boolean trace) throws Exception {
        m_fileReader = fileReader;
        m_trace = trace;
        m_symbolTable = new SymbolTable();
        m_functionTable = new FunctionTable();
        m_lexer = new Lexer(m_fileReader);
        m_stmtReader = new StmtReader(m_lexer, this);
        m_blockList = new ArrayList<InstrBlock>();
    }

    public CompileEnv(String fileName) throws Exception {
        m_trace = false;
        FileInputStream inputStream = new FileInputStream(fileName);
        m_fileReader = new FileReader(inputStream);
        m_symbolTable = new SymbolTable();
        m_functionTable = new FunctionTable();
        m_lexer = new Lexer(m_fileReader);
        m_stmtReader = new StmtReader(m_lexer, this);
        m_blockList = new ArrayList<InstrBlock>();
    }

    public CompileEnv(FileReaderIntf fileReader) throws Exception {
        m_fileReader = fileReader;
        m_trace = false;
        m_symbolTable = new SymbolTable();
        m_functionTable = new FunctionTable();
        m_lexer = new Lexer(m_fileReader);
        m_stmtReader = new StmtReader(m_lexer, this);
        m_blockList = new ArrayList<InstrBlock>();
  }

    public void compile() throws Exception {
        m_entry = new InstrBlock("entry");
        m_blockList.add(m_entry);
        m_currentBlock = m_entry;
        m_stmtReader.getStmtList();
    }

    public void dump(OutputStream outStream) throws Exception {
    	OutputStreamWriter os = new OutputStreamWriter(outStream, "UTF-8");
		Iterator<InstrBlock> blockIter = m_blockList.listIterator();
	    while (blockIter.hasNext()) {
	        InstrBlock nextBlock = blockIter.next();
	        nextBlock.dump(os);
	        os.write("\n");
	    }
	    os.flush();
    }

    public void execute(OutputStream outStream) throws Exception {
        ExecutionEnv env = new ExecutionEnv(m_functionTable, m_symbolTable, outStream, m_trace);
        env.execute(m_entry.getIterator());
    }

    public void addInstr(InstrIntf instr) {
        m_currentBlock.addInstr(instr);
    }

    public InstrBlock createBlock() {
        InstrBlock newBlock = new InstrBlock(Integer.toString(m_nextBlockId));
        m_nextBlockId++;
        m_blockList.add(newBlock);
        return newBlock;
    }

    public InstrBlock createBlock(String name) {
        InstrBlock newBlock = new InstrBlock(name);
        m_blockList.add(newBlock);
        return newBlock;
    }

    public void setCurrentBlock(InstrBlock instrBlock) {
        m_currentBlock = instrBlock;
    }

    public InstrBlock getCurrentBlock() {
        return m_currentBlock;
    }

    public SymbolTable getSymbolTable() {
        return m_symbolTable;
    }

    public FunctionTable getFunctionTable() {
        return m_functionTable;
    }
}
