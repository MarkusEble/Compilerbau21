package compiler;

import compiler.TokenIntf.Type;

public class Token extends TokenIntf {
	public Type m_type;
	public int m_intValue; 
	public String m_stringValue;

	public String toString() {
		String s = type2String(m_type);
		if (m_type == Type.IDENT) {
			s += ' ';
			s += m_stringValue;
		} else if (m_type == Type.INTEGER) {
			s += ' ';
			s += Integer.toString(m_intValue);
		}
		return s;
	}
	
	static String type2String(Type type) {
		if (type == Type.EOF) {
			return "EOF";
		} else if (type == Type.IDENT) {
			return "IDENT";
		} else if (type == Type.INTEGER) {
			return "INTEGER";
		} else if (type == Type.PLUS) {
			return "PLUS";
		} else if (type == Type.MUL) {
			return "MUL";
		} else if (type == Type.LPAREN) {
			return "LPAREN";
		} else if (type == Type.RPAREN) {
			return "RPAREN";
		} else if (type == Type.ASSIGN) {
			return "ASSIGN";
		} else if (type == Type.SEMICOL) {
			return "SEMICOL";
		} else if (type == Type.PRINT) {
			return "PRINT";
		} else if (type == Type.COMMA) {
			return "COMMA";
		} else if (type == Type.LBRACE) {
			return "LBRACE";
		} else if (type == Type.RBRACE) {
			return "RBRACE";
		} else if (type == Type.CALL) {
			return "CALL";
		} else if (type == Type.FUNCTION) {
			return "FUNCTION";
		} else if (type == Type.RETURN) {
			return "RETURN";
		} else if (type == Type.IF) {
			return "IF";
		} else if (type == Type.ELSE) {
			return "ELSE";
		} else if (type == Type.WHILE) {
			return "WHILE";
		} else if (type == Type.DO) {
			return "DO";
		} else if (type == Type.FOR) {
			return "FOR";
		} else if (type == Type.SWITCH) {
			return "SWITCH";
		} else if (type == Type.CASE) {
			return "CASE";
		} else if (type == Type.LESS) {
			return "LESS";
		} else if (type == Type.LESSEQ) {
			return "LESSEQ";
		} else if (type == Type.GREATER) {
			return "GREATER";
		} else if (type == Type.GREATEREQ) {
			return "GREATEREQ";
		} else if (type == Type.EQ) {
			return "EQ";
		} else if (type == Type.NOTEQ) {
			return "NOTEQ";
		} else if (type == Type.AND) {
			return "AND";
		} else if (type == Type.OR) {
			return "OR";
		} else if (type == Type.NOT) {
			return "NOT";
		} else if (type == Type.BITAND) {
			return "BITAND";
		} else if (type == Type.BITOR) {
			return "BITOR";
		} else if (type == Type.BITNOT) {
			return "BITNOT";
        } else if (type == Type.BITXOR) {
            return "BITXOR";
        } else if (type == Type.COLON) {
            return "COLON";
        } else {
			return null;
		}
		
	}
}



