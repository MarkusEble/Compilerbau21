package compilerbau21.compiler;

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
            switch (ident) {
                case "PRINT" -> m_nextToken.m_type = Token.Type.PRINT;
                case "CALL" -> m_nextToken.m_type = Token.Type.CALL;
                case "FUNCTION" -> m_nextToken.m_type = Token.Type.FUNCTION;
                case "IF" -> m_nextToken.m_type = Token.Type.IF;
                case "ELSE" -> m_nextToken.m_type = Token.Type.ELSE;
                case "WHILE" -> m_nextToken.m_type = Token.Type.WHILE;
                case "DO" -> m_nextToken.m_type = Token.Type.DO;
                case "FOR" -> m_nextToken.m_type = Token.Type.FOR;
                case "SWITCH" -> m_nextToken.m_type = Token.Type.SWITCH;
                case "CASE" -> m_nextToken.m_type = Token.Type.CASE;
            }
        } else if (tokenType == Token.Type.INTEGER) {
            m_nextToken.m_intValue = getNumber();
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
        }
        if ('a' <= firstChar && firstChar <= 'z') {
            return Token.Type.IDENT;
        }
        if ('A' <= firstChar && firstChar <= 'Z') {
            return Token.Type.IDENT;
        }
        if ('0' <= firstChar && firstChar <= '9') {
            return Token.Type.INTEGER;
        }
        if (firstChar == '+') {
            return Token.Type.PLUS;
        }
        if (firstChar == '-') {
            return Token.Type.MINUS;
        }
        if (firstChar == '*') {
            return Token.Type.MUL;
        }
        if (firstChar == '/') {
            return Token.Type.DIV;
        }
        if (firstChar == '(') {
            return Token.Type.LPAREN;
        }
        if (firstChar == ')') {
            return Token.Type.RPAREN;
        }
        if (firstChar == '=') {
            return Token.Type.ASSIGN;
        }
        if (firstChar == ';') {
            return Token.Type.SEMICOL;
        }
        if (firstChar == '&') {
            return Token.Type.BITAND;
        }
        if (firstChar == '|') {
            return Token.Type.BITOR;
        }
        if (firstChar == '~') {
            return Token.Type.BITNOT;
        }
        if (firstChar == '!') {
            return Token.Type.NOT;
        }
        if (firstChar == '^') {
            return Token.Type.BITXOR;
        }
        if (firstChar == ',') {
            return Token.Type.COMMA;
        }
        if (firstChar == '{') {
            return Token.Type.LBRACE;
        }
        if (firstChar == '}') {
            return Token.Type.RBRACE;
        }
        if (firstChar == '<') {
            return Token.Type.LESS;
        }
        if (firstChar == '>') {
            return Token.Type.GREATER;
        }
        throw new ParserException("Unexpected character: ", Character.toString(firstChar), m_reader.getCurrentLocationMsg(), "");
    }

    private String getIdent() throws Exception {
        StringBuilder ident = new StringBuilder();
        char nextChar = m_reader.lookAheadChar();
        do {
            ident.append(nextChar);
            m_reader.advance();
            nextChar = m_reader.lookAheadChar();
        } while (isIdentifierPart(nextChar));
        return ident.toString();
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
            number += (int) (nextChar - '0');
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
