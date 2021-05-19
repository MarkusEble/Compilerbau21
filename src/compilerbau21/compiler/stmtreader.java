package compilerbau21.compiler;

public class StmtReader implements StmtReaderIntf {
    private SymbolTable m_symbolTable;
    private LexerIntf m_lexer;
    private ExprReader m_exprReader;
    private CompileEnvIntf m_compileEnv;

    public StmtReader(LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception {
        m_symbolTable = compileEnv.getSymbolTable();
        m_lexer = lexer;
        m_compileEnv = compileEnv;
        m_exprReader = new ExprReader(m_symbolTable, m_lexer, compileEnv);
    }

    public void getStmtList() throws Exception {
        while (m_lexer.lookAheadToken().m_type != Token.Type.EOF && m_lexer.lookAheadToken().m_type != Token.Type.RBRACE) {
            getStmt();
        }
    }

    public void getBlockStmt() throws Exception {
        m_lexer.expect(Token.Type.LBRACE);
        getStmtList();
        m_lexer.expect(Token.Type.RBRACE);
    }

    public void getStmt() throws Exception {
        Token token = m_lexer.lookAheadToken();
        if (token.m_type == Token.Type.IDENT) {
            getAssign();
        } else if (token.m_type == Token.Type.PRINT) {
            getPrint();
        }
    }

    public void getAssign() throws Exception {
        Token token = m_lexer.lookAheadToken();
        String varName = token.m_stringValue;
        m_lexer.advance();
        m_lexer.expect(Token.Type.ASSIGN);
        // int number = m_exprReader.getExpr();
        // Symbol var = m_symbolTable.createSymbol(varName);
        // var.m_number = number;
        m_exprReader.getExpr();
        m_symbolTable.createSymbol(varName);
        InstrIntf assignInstr = new Instr.AssignInstr(varName);
        m_compileEnv.addInstr(assignInstr);
        m_lexer.expect(Token.Type.SEMICOL);
    }

    public void getPrint() throws Exception {
        m_lexer.advance(); // PRINT
        //int number = m_exprReader.getExpr();
        //m_outStream.write(Integer.toString(number));
        //m_outStream.write('\n');
        m_exprReader.getExpr();
        InstrIntf printInstr = new Instr.PrintInstr();
        m_compileEnv.addInstr(printInstr);
        m_lexer.expect(Token.Type.SEMICOL);
    }


}
