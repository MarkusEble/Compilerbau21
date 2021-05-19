package compilerbau21.compiler.logicalexpression;

public interface LogicalExpressionReaderInterface {
    /**
     * checks for OR and creates instruction
     * calls AND
     *
     * @throws Exception
     */
    void getLogicalOrExpression() throws Exception;

    /**
     * checks for AND
     * calls Bitwise OR
     */
    void getLogicalAnd() throws Exception;

    /**
     * checks for Bitwise OR
     * calls Bitwise AND
     *
     * @throws Exception
     */
    void getBitwiseOr() throws Exception;

    /**
     * @throws Exception
     */
    void getBitwiseAnd() throws Exception;
}
