package compiler.while_loop;

import compiler.*;
import compiler.Instr.JumpCondInstr;
import compiler.Instr.JumpInstr;
import compiler.TokenIntf.Type;

import java.util.Stack;

public class WhileLoopReader implements WhileLoopReaderIntf {
    private final CompileEnvIntf compileEnv;
    private final StmtReaderIntf stmtReader;
    private final ExprReaderIntf exprReader;
    private final LexerIntf lexer;

    private InstrBlock postBlock;


    public WhileLoopReader(CompileEnvIntf compileEnv, StmtReaderIntf stmtReader, ExprReaderIntf exprReader, LexerIntf lexer) {
        this.compileEnv = compileEnv;
        this.stmtReader = stmtReader;
        this.exprReader = exprReader;
        this.lexer = lexer;
    }

    @Override
    public void readWhileLoop() throws Exception {

        final InstrBlock prevBlock = compileEnv.getCurrentBlock();
        final InstrBlock whileHeadBlock = compileEnv.createBlock();
        final InstrBlock whileBodyBlock = compileEnv.createBlock();
        final InstrBlock postBlock = compileEnv.createBlock();
        this.postBlock = postBlock;

        final Instr.JumpInstr jumpHead = new Instr.JumpInstr(whileHeadBlock);
        final Instr.JumpCondInstr jumpCond = new Instr.JumpCondInstr(whileBodyBlock, postBlock);

//      beginnen im prevBlock
        lexer.advance();
        lexer.expect(Token.Type.LPAREN);
        prevBlock.addInstr(new Instr.JumpInstr(whileHeadBlock));

        compileEnv.setCurrentBlock(whileHeadBlock);
        exprReader.getExpr();
        lexer.expect(Token.Type.RPAREN);
        whileHeadBlock.addInstr(jumpCond);

//        condInstr.execute(this.stmtReader.getM_executionEnv());

        compileEnv.setCurrentBlock(whileBodyBlock);
        stmtReader.getBlockStmt();
        compileEnv.getCurrentBlock().addInstr(jumpHead);

        compileEnv.setCurrentBlock(postBlock);
    }

    @Override
    public void readDoWhileLoop() throws Exception {

        final InstrBlock prevBlock = compileEnv.getCurrentBlock();
        final InstrBlock whileHeadBlock = compileEnv.createBlock();
        final InstrBlock whileBodyBlock = compileEnv.createBlock();
        final InstrBlock postBlock = compileEnv.createBlock();
        this.postBlock = postBlock;

        final Instr.JumpInstr jumpHead = new Instr.JumpInstr(whileHeadBlock);
        final Instr.JumpInstr jumpBody = new Instr.JumpInstr(whileBodyBlock);
        final Instr.JumpCondInstr jumpCond = new Instr.JumpCondInstr(whileBodyBlock, postBlock);

        //      beginnen im prevBlock
        lexer.advance(); // do / while eingelesen
        prevBlock.addInstr(jumpBody);

        compileEnv.setCurrentBlock(whileBodyBlock);
        stmtReader.getBlockStmt();
        whileBodyBlock.addInstr(jumpHead);

        compileEnv.setCurrentBlock(whileHeadBlock);
        lexer.expect(Token.Type.WHILE);
        lexer.expect(Token.Type.LPAREN);
        exprReader.getExpr();
        lexer.expect(Token.Type.RPAREN);
        lexer.expect(Token.Type.SEMICOL);
        compileEnv.getCurrentBlock().addInstr(jumpCond);

        compileEnv.setCurrentBlock(postBlock);
    }

    @Override
    public void breakWhileLoop() throws Exception {
        final Instr.JumpInstr jumpPost = new Instr.JumpInstr(this.postBlock);

        lexer.advance();
        lexer.expect(Token.Type.SEMICOL);
        //stmtReader.getStmtList();
        //lexer.expect(Token.Type.RBRACE);

        compileEnv.getCurrentBlock().addInstr(jumpPost);
//        compileEnv.setCurrentBlock(this.postBlock);
    }
}