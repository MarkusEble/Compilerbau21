package compilerbau21.compiler;

public abstract class ExprReaderIntf {
	SymbolTable m_symbolTable;
    LexerIntf m_lexer;
    CompileEnvIntf m_compileEnv;

	public ExprReaderIntf(SymbolTable symbolTable, LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception {
		m_symbolTable = symbolTable;
		m_lexer = lexer;
		m_compileEnv = compileEnv;
	}
	
	// read atomic expression
	abstract public void getAtomicExpr() throws Exception;

	// read unary expression
	abstract public void getUnaryExpr() throws Exception;
	
	// read product
	abstract public void getProduct() throws Exception;

	// read expression
	abstract public void getExpr() throws Exception;
}
