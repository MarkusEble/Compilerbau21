package compiler.for_loop;

import compiler.*;

public class ForLoopReader implements ForLoopReaderIntf {

    private final CompileEnvIntf compileEnv;
    private final StmtReaderIntf stmtReader;
    private final ExprReaderIntf exprReader;
    private final LexerIntf lexer;

    public ForLoopReader(CompileEnvIntf compileEnv, StmtReaderIntf stmtReader, ExprReaderIntf exprReader, LexerIntf lexer) {
        this.compileEnv = compileEnv;
        this.stmtReader = stmtReader;
        this.exprReader = exprReader;
        this.lexer = lexer;
    }

    public void readForLoop() throws Exception {
        final InstrBlock entry = compileEnv.getCurrentBlock();
        final InstrBlock forProlog = compileEnv.createBlock();
        final InstrBlock forConditionCheck = compileEnv.createBlock();
        final InstrBlock forBody = compileEnv.createBlock();
        final InstrBlock forIteration = compileEnv.createBlock();
        final InstrBlock forExit = compileEnv.createBlock();

        lexer.advance();
        lexer.expect(Token.Type.LPAREN);

        // forProlog
        entry.addInstr(new Instr.JumpInstr(forProlog));
        compileEnv.setCurrentBlock(forProlog);
        stmtReader.getStmt();
        forProlog.addInstr(new Instr.JumpInstr(forConditionCheck));

        // forConditionCheck
        compileEnv.setCurrentBlock(forConditionCheck);
        exprReader.getExpr();
        lexer.expect(Token.Type.SEMICOL);
        forConditionCheck.addInstr(new Instr.JumpCondInstr(forBody, forExit));

        // forIteration
        compileEnv.setCurrentBlock(forIteration);
        stmtReader.getStmt();
        forIteration.addInstr(new Instr.JumpInstr(forConditionCheck));
        lexer.expect(Token.Type.RPAREN);

        // forBody
        compileEnv.setCurrentBlock(forBody);
        stmtReader.getBlockStmt();
        forBody.addInstr(new Instr.JumpInstr(forIteration));

        compileEnv.setCurrentBlock(forExit);

    }

}
