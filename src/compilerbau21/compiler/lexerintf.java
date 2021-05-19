package compilerbau21.compiler;

public interface LexerIntf {
	
    // construct a Lexer from the given file reader
	// public LexerIntf(FileReaderIntf reader) throws Exception

	// look at the current token without consuming it
	public Token lookAheadToken();

	// consume current token and
	// advance to next token
	public void advance() throws Exception;
	
	// check if next token is the expected token
	// throw Exception if not	
	public void expect(Token.Type tokenType) throws Exception;
	
	// get the type of a token looking at the first char
	public Token.Type getTokenType(char firstChar) throws Exception;

	// read an identifer from in put
	// private String getIdent() throws Exception;

    // is the given character part of an identifier?       
	public boolean isIdentifierPart(char c);

	// private int getNumber() throws Exception {

	// is the given character a digit?
	public boolean isDigit(char c);

	// is the given character a whitespace?
	public boolean isWhiteSpace(char c);
	
	// describes the current location
	// consisting of the current line and
	// a caret to indicate the current character position
	public String getCurrentLocationMsg();
}
