package compilerbau21.compiler;

public interface StmtReaderIntf {

	// constructs a statement reader from a given lexer
	// public StmtIntf(LexerIntf lexer, OutputStream out) throws Exception;
	
    /**
     *  get statement list without {}
     */
	public void getStmtList() throws Exception;

	/**
	 *  get block statement including {}
	 */
	public void getBlockStmt() throws Exception;

	/**
	 *  get next statement
	 */
	public void getStmt() throws Exception;
	
	/**
	 *  get assign statement
	 */
	public void getAssign() throws Exception;

	/**
	 *  get print statement
	 */
	public void getPrint() throws Exception;
}
