package compiler;

import compiler.logicalexpression.LogicalExpressionReader;
import compiler.logicalexpression.LogicalExpressionReaderInterface;
import compiler.logicalexpression.instruction.BitNot;
import compiler.logicalexpression.instruction.Not;

public class ExprReader extends ExprReaderIntf {
    private final LogicalExpressionReaderInterface logicalExpressionReader;
    
    public ExprReader(SymbolTable symbolTable, LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception {
        super(symbolTable, lexer, compileEnv);
        logicalExpressionReader = new LogicalExpressionReader(lexer, compileEnv, this);
    }

    /**
     * Entry point for the expression reader!
     *
     * @throws Exception
     */
    public void getExpr() throws Exception {
        logicalExpressionReader.getLogicalOrExpression();
    }

    public void getAtomicExpr() throws Exception {
        // int number = 0;
        Token token = m_lexer.lookAheadToken();
        if (token.m_type == Token.Type.INTEGER) {
            m_lexer.advance();
            // number = token.m_intValue;
            InstrIntf numberInstr = new Instr.PushNumberInstr(token.m_intValue);
            m_compileEnv.addInstr(numberInstr);
        } else if (token.m_type == Token.Type.LPAREN) {
            m_lexer.advance();
            //number = getExpr();
            getExpr();
            m_lexer.expect(Token.Type.RPAREN);
        } else if (token.m_type == Token.Type.IDENT) {
            m_lexer.advance();
            //Symbol var = m_symbolTable.getSymbol(token.m_stringValue);
            //number = var.m_number;
            InstrIntf variableInstr = new Instr.VariableInstr(token.m_stringValue);
            m_compileEnv.addInstr(variableInstr);
        } else if (token.m_type == Token.Type.CALL) {
        	m_lexer.expect(Token.Type.CALL); // CALL
    		String functionName = m_lexer.lookAheadToken().m_stringValue; // value of IDENT
    		m_lexer.expect(Token.Type.IDENT);
    		m_lexer.expect(Token.Type.LPAREN);

    		FunctionInfo f_info = m_compileEnv.getFunctionTable().getFunction(functionName);

    		int varCounter = 0;

    		// GET PARAMS
    		while (m_lexer.lookAheadToken().m_type != Token.Type.EOF
    				&& m_lexer.lookAheadToken().m_type != Token.Type.RPAREN) {
    			if (m_lexer.lookAheadToken().m_type == Token.Type.COMMA) {
    				m_lexer.advance();
    			} else {	
    				getExpr();
    				m_compileEnv.addInstr(new Instr.AssignInstr(f_info.varNames.get(varCounter)));
    				varCounter++;
    			}
    			
    		}
    		m_lexer.expect(Token.Type.RPAREN);

    		InstrIntf callInstr = new Instr.CallInstr(f_info);
    		m_compileEnv.addInstr(callInstr);
        } else {
            throw new ParserException("Unexpected Token: ", token.toString(), m_lexer.getCurrentLocationMsg(), "numerical expression");
        }
        // return number;
    }

    /**
     * Grammar
     * unary : atomic
     * unary : [!~-] atomic
     *
     * @throws Exception
     */
    public void getUnaryExpr() throws Exception {

        Token token = m_lexer.lookAheadToken();
        if (!(token.m_type == TokenIntf.Type.MINUS || token.m_type == TokenIntf.Type.BITNOT || token.m_type == TokenIntf.Type.NOT)) {
            getAtomicExpr();
            return;
        }
        m_lexer.advance();
        // create instruction and store it.
        InstrIntf instruction;
        switch (token.m_type) {
            case MINUS:
            	instruction = new Instr.UnaryMinusInstr();
            	break;
            case BITNOT:
            	instruction = new BitNot();
            	break;
            case NOT:
            	instruction = new Not();
            	break;
            default:
            	instruction = null;
        };
        // add instruction to code block
        getUnaryExpr();
        m_compileEnv.addInstr(instruction);
    }

    public void getProduct() throws Exception {
        getUnaryExpr();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == Token.Type.MUL || token.m_type == Token.Type.DIV) {
            m_lexer.advance();
            if (token.m_type == Token.Type.MUL) {
                getUnaryExpr();
                InstrIntf mulInstr = new Instr.MultiplyInstr();
                // add instruction to code block
                m_compileEnv.addInstr(mulInstr);
            } else {
                getUnaryExpr();
                InstrIntf divInstr = new Instr.DivisionInstr();
                // add instruction to code block
                m_compileEnv.addInstr(divInstr);
            }
            token = m_lexer.lookAheadToken();
        }
    }

    public void getSum() throws Exception {
        getProduct();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == Token.Type.PLUS || token.m_type == Token.Type.MINUS) {
            m_lexer.advance();
            if (token.m_type == Token.Type.PLUS) {
                getProduct();
                InstrIntf addInstr = new Instr.AddInstr();
                // add instruction to code block
                m_compileEnv.addInstr(addInstr);
            } else {
                getProduct();
                InstrIntf subInstr = new Instr.SubInstr();
                // add instruction to code block
                m_compileEnv.addInstr(subInstr);
            }
            token = m_lexer.lookAheadToken();
        }
    }

	public void getCompare() throws Exception {
		getSum();
		Token token = m_lexer.lookAheadToken(); 
		while (token.m_type == Token.Type.LESS || token.m_type == Token.Type.LESSEQ || token.m_type == Token.Type.GREATER || token.m_type == Token.Type.GREATEREQ || token.m_type == Token.Type.EQ || token.m_type == Token.Type.NOTEQ) {
			m_lexer.advance();
			if(token.m_type == Token.Type.LESS) {
				getSum();
				InstrIntf lessInstr = new Instr.LessInstr();
				m_compileEnv.addInstr(lessInstr);
			} else if(token.m_type == Token.Type.LESSEQ) {
				getSum();
				InstrIntf lessEqualInstr = new Instr.LessEqualInstr();
				m_compileEnv.addInstr(lessEqualInstr);
			} else if(token.m_type == Token.Type.GREATER) {
				getSum();
				InstrIntf greaterInstr = new Instr.GreaterInstr();
				m_compileEnv.addInstr(greaterInstr);
			} else if(token.m_type == Token.Type.GREATEREQ) {
				getSum();
				InstrIntf greaterEqualInstr = new Instr.GreaterEqualInstr();
				m_compileEnv.addInstr(greaterEqualInstr);
			} else if(token.m_type == Token.Type.EQ) {
				getSum();
				InstrIntf equalInstr = new Instr.EqualInstr();
				m_compileEnv.addInstr(equalInstr);
			} else if(token.m_type == Token.Type.NOTEQ) {
				getSum();
				InstrIntf notEqualInstr = new Instr.NotEqualInstr();
				m_compileEnv.addInstr(notEqualInstr);
			}
			token = m_lexer.lookAheadToken(); 
		}
		//return number;
	}

}
