package compilerbau21.compiler.logicalexpression;

import compilerbau21.compiler.*;
import compilerbau21.compiler.logicalexpression.instructions.*;

public class LogicalExpressionReader implements LogicalExpressionReaderInterface {

    private final LexerIntf lexer;
    private final CompileEnvIntf compilerEnv;
    private final ExprReader expressionReader;

    /**
     * Parses a logical expression
     * valid operands:
     * ||, |, &&, &, ~, !
     * Grammar:
     * logicalExpression: ! EXPRESSION
     * logicalExpression: ~ EXPRESSION
     * logicalExpression: EXPRESSION && EXPRESSION
     * logicalExpression: EXPRESSION || EXPRESSION
     * logicalExpression: EXPRESSION & EXPRESSION
     * logicalExpression: EXPRESSION | EXPRESSION
     *
     * @see <a href='https://introcs.cs.princeton.edu/java/11precedence/'>Operator Precedence in Java</a>
     * Precedence:
     * 16 ()
     * 14 !~- (unary)
     * (ka) *
     * 11 + -
     * -----------------------------
     * ^ this is already implemented
     * -----------------------------
     * 7 &
     * 6 ^ (bitxor)
     * 5 |
     * 4 &&
     * 3 ||
     */

    public LogicalExpressionReader(LexerIntf lexer, CompileEnvIntf compilerEnv, ExprReader expressionReader) {
        this.lexer = lexer;
        this.compilerEnv = compilerEnv;
        this.expressionReader = expressionReader;
    }

    /**
     * checks for OR and creates instruction
     * calls AND
     *
     * @throws Exception
     */
    @Override
    public void getLogicalOrExpression() throws Exception {
        getLogicalBitXorExpression();
        Token token = lexer.lookAheadToken();
        while (token.m_type == Token.Type.OR) {
            lexer.advance();
            this.getLogicalBitXorExpression();
            InstrIntf orInstr = new Or();
            // add instruction to code block
            compilerEnv.addInstr(orInstr);
            token = lexer.lookAheadToken();
        }
    }

    private void getLogicalBitXorExpression() throws Exception {
        getLogicalAnd();
        Token token = lexer.lookAheadToken();
        while (token.m_type == Token.Type.BITXOR) {
            lexer.advance();
            this.getLogicalAnd();
            InstrIntf orInstr = new BitXor();
            // add instruction to code block
            compilerEnv.addInstr(orInstr);
            token = lexer.lookAheadToken();
        }
    }

    /**
     * checks for AND
     * calls Bitwise OR
     */
    @Override
    public void getLogicalAnd() throws Exception {
        this.getBitwiseOr();
        Token token = lexer.lookAheadToken();
        while (token.m_type == TokenIntf.Type.AND) {
            lexer.advance();
            this.getBitwiseOr();
            InstrIntf andInstruction = new And();
            // add instruction to code block
            compilerEnv.addInstr(andInstruction);
            token = lexer.lookAheadToken();
        }
    }

    /**
     * checks for Bitwise OR
     * calls Bitwise AND
     * @throws Exception
     */
    @Override
    public void getBitwiseOr() throws Exception {
        this.getBitwiseAnd();
        Token token = lexer.lookAheadToken();
        while (token.m_type == TokenIntf.Type.BITOR) {
            lexer.advance();
            this.getBitwiseAnd();
            InstrIntf andInstruction = new BitOr();
            // add instruction to code block
            compilerEnv.addInstr(andInstruction);
            token = lexer.lookAheadToken();
        }
    }

    /**
     * checks for Bitwise AND
     * calls SUM
     * @throws Exception
     */
    @Override
    public void getBitwiseAnd() throws Exception {
        expressionReader.getSum();
        Token token = lexer.lookAheadToken();
        while (token.m_type == TokenIntf.Type.BITAND) {
            lexer.advance();
            expressionReader.getSum();
            InstrIntf andInstruction = new BitAnd();
            // add instruction to code block
            compilerEnv.addInstr(andInstruction);
            token = lexer.lookAheadToken();
        }
    }
}
