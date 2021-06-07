package compiler;

public abstract class TokenIntf {
	public enum Type {
		EOF,
		IDENT,
		INTEGER,
		PLUS,
		MINUS,
		MUL,
		DIV,
		LPAREN,
		RPAREN,
		ASSIGN,
		SEMICOL,
		PRINT,
		COMMA,
		LBRACE,
		RBRACE,
		CALL,
		FUNCTION,
		RETURN,
		IF,
		ELSE,
		WHILE,
		DO,
		FOR,
		SWITCH,
		CASE,
		LESS,
		LESSEQ,
		GREATER,
		GREATEREQ,
		EQ,
		NOTEQ,
		AND,
		OR,
		NOT,
		BITAND,
		BITOR,
		BITNOT,
        BITXOR,		
        COLON,
        BREAK,
 }

	public Type m_type;
	public int m_intValue; 
	public String m_stringValue;

	// returns a string representation of the current token
	public abstract String toString();
	
	// returns a string representaiton of the given token type
	// static String type2String(Type type);		
}
