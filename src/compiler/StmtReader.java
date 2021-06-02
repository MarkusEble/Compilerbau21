package compiler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import compiler.for_loop.ForLoopReader;
import compiler.for_loop.ForLoopReaderIntf;


public class StmtReader implements StmtReaderIntf {
    private SymbolTable m_symbolTable;
    private FunctionTable m_functionTable;
    private LexerIntf m_lexer;
    private ExprReader m_exprReader;
    private CompileEnvIntf m_compileEnv;
    private ForLoopReaderIntf m_forLoopReader;

	public StmtReader(LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception {
		m_symbolTable = compileEnv.getSymbolTable();
		m_functionTable = compileEnv.getFunctionTable();
		m_lexer = lexer;
		m_compileEnv = compileEnv;
		m_exprReader = new ExprReader(m_symbolTable, m_lexer, compileEnv);
		m_forLoopReader = new ForLoopReader(m_compileEnv, this, m_exprReader, m_lexer);
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
		} else if (token.m_type == Token.Type.FUNCTION){
			getFunctionDef();
		} else if (token.m_type == Token.Type.RETURN) {
			getReturn();
		} else if (token.m_type == TokenIntf.Type.FOR) {
			m_forLoopReader.readForLoop();
    } else if (token.m_type == TokenIntf.Type.SWITCH) {
      getSwitchCase();
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

	public void getReturn() throws Exception {
		m_lexer.advance(); // RETURN
		//int number = m_exprReader.getExpr();
		//m_outStream.write(Integer.toString(number));
		//m_outStream.write('\n');
		m_exprReader.getExpr();
		InstrIntf returnInstr = new Instr.ReturnInstr();
		m_compileEnv.addInstr(returnInstr);
		m_lexer.expect(Token.Type.SEMICOL);
	}

	public void getFunctionDef() throws Exception{
		List<String> varList = new ArrayList<String>();
		m_lexer.expect(Token.Type.FUNCTION); // FUNCTION
		String functionName = m_lexer.lookAheadToken().m_stringValue; // value of IDENT
		m_lexer.expect(Token.Type.IDENT);
		m_lexer.expect(Token.Type.LPAREN);

		// GET PARAMS
		if(m_lexer.lookAheadToken().m_type != Token.Type.EOF && m_lexer.lookAheadToken().m_type != Token.Type.RPAREN){
			Token token = m_lexer.lookAheadToken();
			if (token.m_type == Token.Type.IDENT) {
				m_symbolTable.createSymbol(token.m_stringValue);
				varList.add(token.m_stringValue);
				m_lexer.advance();
			}
		}
		while (m_lexer.lookAheadToken().m_type != Token.Type.EOF && m_lexer.lookAheadToken().m_type != Token.Type.RPAREN) {
			m_lexer.expect(Token.Type.COMMA);
			Token token = m_lexer.lookAheadToken();
			if (token.m_type == Token.Type.IDENT) {
				m_symbolTable.createSymbol(token.m_stringValue);
				varList.add(token.m_stringValue);
				m_lexer.advance();
			}
		}
		m_lexer.expect(Token.Type.RPAREN);
		//

		//Function Body
		m_lexer.expect(Token.Type.LBRACE);
		InstrBlock prevBlock = m_compileEnv.getCurrentBlock(); // Save previous Block reference
		InstrBlock block = m_compileEnv.createBlock(); // Create new Block
		m_compileEnv.setCurrentBlock(block); // Set new Block as active one
		//Fill Block with instructions
		getStmtList();
		m_lexer.expect(Token.Type.RBRACE);
		m_functionTable.createFunction(functionName, block, varList); //Save function name and InstructionBlock
		//

		m_compileEnv.setCurrentBlock(prevBlock); // Set previous Block as active one
	}

    public void getSwitchCase() throws Exception {
        m_lexer.advance();
        m_exprReader.getExpr();
        m_lexer.expect(TokenIntf.Type.LBRACE);

        Hashtable<Integer, InstrBlock> caseInstrBlocks = new Hashtable<>();
        int caseValue;
        InstrBlock blockBeforeSwitch = m_compileEnv.getCurrentBlock();
        InstrBlock followBlock = m_compileEnv.createBlock();

        while (m_lexer.lookAheadToken().m_type == TokenIntf.Type.CASE) {
            m_lexer.advance();
            caseValue = m_lexer.lookAheadToken().m_intValue;
            m_lexer.advance();
            m_lexer.expect(TokenIntf.Type.COLON);
            m_compileEnv.setCurrentBlock(m_compileEnv.createBlock()); // sets current CaseInstrBlock to add stmts to
            while (m_lexer.lookAheadToken().m_type != TokenIntf.Type.EOF && m_lexer.lookAheadToken().m_type != TokenIntf.Type.CASE && m_lexer.lookAheadToken().m_type != TokenIntf.Type.RBRACE) {
                getStmt();
            }
            m_compileEnv.addInstr(new Instr.JumpInstr(followBlock));
            caseInstrBlocks.put(caseValue, m_compileEnv.getCurrentBlock());
        }

        // Add defaultBlock to Hashtable
        m_compileEnv.setCurrentBlock(m_compileEnv.createBlock());
        m_compileEnv.addInstr(new Instr.JumpInstr(followBlock));
        caseInstrBlocks.put(-1, m_compileEnv.getCurrentBlock());

        m_lexer.expect(TokenIntf.Type.RBRACE);
        m_lexer.expect(TokenIntf.Type.SEMICOL);
        m_compileEnv.setCurrentBlock(blockBeforeSwitch); // resets the InstrBlock where the SwitchStatement needs to be added
        InstrIntf switchCaseInstr = new Instr.SwitchCaseInstr(caseInstrBlocks);
        m_compileEnv.addInstr(switchCaseInstr);
        m_compileEnv.setCurrentBlock(followBlock);
    }
}
