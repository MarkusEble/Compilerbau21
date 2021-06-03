package compiler;

public class Lexer implements LexerIntf {
	private FileReaderIntf m_reader;
	private Token m_nextToken;
	
	public Lexer(FileReaderIntf reader) throws Exception {
		m_reader = reader;
		advance();
	}
	
	public Token lookAheadToken() {
		return m_nextToken;
	}

	public void advance() throws Exception {
		Token.Type tokenType = getTokenType(m_reader.lookAheadChar());
		m_nextToken = new Token();
		m_nextToken.m_type = tokenType;
		if (tokenType == Token.Type.IDENT) {
			String ident = getIdent();
			m_nextToken.m_stringValue = ident;
			if (ident.equals("PRINT")) {
				m_nextToken.m_type = Token.Type.PRINT;
			} else if (ident.equals("CALL")) {
				m_nextToken.m_type = Token.Type.CALL;
			} else if (ident.equals("FUNCTION")) {
				m_nextToken.m_type = Token.Type.FUNCTION;
			} else if (ident.equals("IF")) {
				m_nextToken.m_type = Token.Type.IF;
			} else if (ident.equals("ELSE")) {
				m_nextToken.m_type = Token.Type.ELSE;
			} else if (ident.equals("WHILE")) {
				m_nextToken.m_type = Token.Type.WHILE;
			} else if (ident.equals("DO")) {
				m_nextToken.m_type = Token.Type.DO;
			} else if (ident.equals("FOR")) {
				m_nextToken.m_type = Token.Type.FOR;
			} else if (ident.equals("SWITCH")) {
				m_nextToken.m_type = Token.Type.SWITCH;
			} else if (ident.equals("CASE")) {
				m_nextToken.m_type = Token.Type.CASE;
			} else if (ident.equals("RETURN")) {
				m_nextToken.m_type = Token.Type.RETURN;
			}
		} else if (tokenType == Token.Type.INTEGER) {
			int number = getNumber();
			m_nextToken.m_intValue = number;
		} else if (tokenType == Token.Type.ASSIGN) {
			m_reader.advance();
			if (m_reader.lookAheadChar() == '=') {
				m_nextToken.m_type = Token.Type.EQ;
				m_reader.advance();
			}
		} else if (tokenType == Token.Type.BITAND) {
			m_reader.advance();
			if (m_reader.lookAheadChar() == '&') {
				m_nextToken.m_type = Token.Type.AND;
				m_reader.advance();
			}
		} else if (tokenType == Token.Type.BITOR) {
			m_reader.advance();
			if (m_reader.lookAheadChar() == '|') {
				m_nextToken.m_type = Token.Type.OR;
				m_reader.advance();
			}
		} else if (tokenType == Token.Type.NOT) {
			m_reader.advance();
			if (m_reader.lookAheadChar() == '=') {
				m_nextToken.m_type = Token.Type.NOTEQ;
				m_reader.advance();
			}
		} else if (tokenType == Token.Type.LESS) {
			m_reader.advance();
			if (m_reader.lookAheadChar() == '=') {
				m_nextToken.m_type = Token.Type.LESSEQ;
				m_reader.advance();
			}
		} else if (tokenType == Token.Type.GREATER) {
			m_reader.advance();
			if (m_reader.lookAheadChar() == '=') {
				m_nextToken.m_type = Token.Type.GREATEREQ;
				m_reader.advance();
			}
		} else {
			m_reader.advance();
		}
		while (isWhiteSpace(m_reader.lookAheadChar())) {
			m_reader.advance();
		}
	}
	
	public void expect(Token.Type tokenType) throws Exception {
		if (tokenType == m_nextToken.m_type) {
			advance();
		} else {
			throw new ParserException("Unexpected Token: ", m_nextToken.toString(), getCurrentLocationMsg(), Token.type2String(tokenType));
		}
	}
	
	public Token.Type getTokenType(char firstChar) throws Exception {
		if (firstChar == 0) {
			return Token.Type.EOF;
		} else if ('a' <= firstChar && firstChar <= 'z') {
			return Token.Type.IDENT;
		} else if ('A' <= firstChar && firstChar <= 'Z') {
			return Token.Type.IDENT;
		} else if ('0' <= firstChar && firstChar <= '9') {
			return Token.Type.INTEGER;
		} else if (firstChar == '+') {
			return Token.Type.PLUS;
		} else if (firstChar == '-') {
			return Token.Type.MINUS;
		} else if (firstChar == '*') {
			return Token.Type.MUL;
		} else if (firstChar == '/') {
			return Token.Type.DIV;
		} else if (firstChar == '(') {
			return Token.Type.LPAREN;
		} else if (firstChar == ')') {
			return Token.Type.RPAREN;
		} else if (firstChar == '=') {
			return Token.Type.ASSIGN;
		} else if (firstChar == ';') {
			return Token.Type.SEMICOL;
		} else if (firstChar == '&') {
			return Token.Type.BITAND;
		} else if (firstChar == '|') {
			return Token.Type.BITOR;
		} else if (firstChar == '~') {
			return Token.Type.BITNOT;
		} else if (firstChar == '!') {
			return Token.Type.NOT;
		} else if (firstChar == '!') {
			return Token.Type.BITXOR;
		} else if (firstChar == '^') {
			return Token.Type.LPAREN;
		} else if (firstChar == ')') {
			return Token.Type.RPAREN;
		} else if (firstChar == '=') {
			return Token.Type.ASSIGN;
		} else if (firstChar == ',') {
			return Token.Type.COMMA;
		} else if (firstChar == '{') {
			return Token.Type.LBRACE;
		} else if (firstChar == '}') {
			return Token.Type.RBRACE;
		} else if (firstChar == '<') {
			return Token.Type.LESS;
		} else if (firstChar == '>') {
			return Token.Type.GREATER;
        } else if (firstChar == ':') {
            return Token.Type.COLON;
        } else {
			throw new ParserException("Unexpected character: ", Character.toString(firstChar), m_reader.getCurrentLocationMsg(), "");
		}
	}

	private String getIdent() throws Exception {
		String ident = "";
		char nextChar = m_reader.lookAheadChar();
		do  {
			ident += nextChar;
			m_reader.advance();
			nextChar = m_reader.lookAheadChar();
		} while (isIdentifierPart(nextChar));
		return ident;
	}

	public boolean isIdentifierPart(char c) {
		boolean isPart = ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
		return isPart;
	}

	private int getNumber() throws Exception {
		int number = 0;
		char nextChar = m_reader.lookAheadChar();
		do {
			number *= 10;
			number += (int)(nextChar - '0');
			m_reader.advance();
			nextChar = m_reader.lookAheadChar();
		} while (isDigit(nextChar));
		return number;
	}

	public boolean isDigit(char c) {
		return ('0' <= c && c <= '9');
	}

	public boolean isWhiteSpace(char c) {
		return (c == ' ' || c == '\n' || c == '\r' || c == '\t');
	}

	public String getCurrentLocationMsg() {
		return m_reader.getCurrentLocationMsg();
	}
}
