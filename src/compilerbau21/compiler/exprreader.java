package compilerbau21.compiler;

import compilerbau21.compiler.logicalexpression.LogicalExpressionReader;
import compilerbau21.compiler.logicalexpression.LogicalExpressionReaderInterface;
import compilerbau21.compiler.logicalexpression.instructions.BitNot;
import compilerbau21.compiler.logicalexpression.instructions.Not;

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
        InstrIntf instruction = switch (token.m_type) {
            case MINUS -> new Instr.UnaryMinusInstr();
            case BITNOT -> new BitNot();
            case NOT -> new Not();
            default -> null;
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
}
