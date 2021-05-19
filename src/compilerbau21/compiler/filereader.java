package compilerbau21.compiler;

import java.io.*;

public class FileReader implements FileReaderIntf {
    private InputStream m_inputStream;
    private Reader m_inputStreamReader;
    private String m_currentLine;
    private int m_currentLineNextPos;
    private boolean m_eofAfterCurrentLine;
    private char m_nextChar;
    
	public FileReader(InputStream inputStream) throws Exception {
		m_inputStream       = inputStream;
		m_inputStreamReader = new InputStreamReader(m_inputStream);
		m_currentLine = new String();
		m_currentLineNextPos = 0;
		m_eofAfterCurrentLine = false;
		advance();
	}
	
	public char lookAheadChar() {
		return m_nextChar;
	}
	
	public void advance() throws Exception {
		while (m_currentLineNextPos == m_currentLine.length()) {
			if (m_eofAfterCurrentLine) {
				m_nextChar = 0;
				return;
			} else {
				readNextLine();
				m_currentLineNextPos = 0;
			}
		}
		m_nextChar = m_currentLine.charAt(m_currentLineNextPos);
		m_currentLineNextPos++;
	}
	
	public void expect(char c) throws Exception {
		if (m_nextChar != c) {
			String msg = new String("unexpected character: ");
			msg += m_nextChar;
			msg += "\nExpected: ";
			msg += c;
			throw new Exception(msg);
		}
		advance();
	}

	public void expect(String s) throws Exception {
		for (int i=0; i<s.length(); ++i) {
			if (m_nextChar != s.charAt(i)) {
				String msg = new String("unexpected character: ");
				msg += m_nextChar;
				msg += "\nExpected: ";
				msg += s.charAt(i);
				throw new Exception(msg);
			}
			advance();
		}
	}

	public String getCurrentLocationMsg() {
		String location = m_currentLine;
		for (int i=0; i<m_currentLineNextPos; ++i) {
			location += ' ';
		}
		location += "^\n";
		return location;
	}

	private void readNextLine() throws Exception {
		m_currentLine = new String();
		while (true) {
			int nextChar = m_inputStreamReader.read();
			if (nextChar == -1) {
				m_eofAfterCurrentLine = true;
				return;
			} else if (nextChar == '\r') {
            	continue;
            } else if (nextChar == '\n') {
            	m_currentLine += '\n';
            	return;
            } else {
            	m_currentLine += (char)nextChar;
            }
		}
	}
	
	public static FileReaderIntf fromFileName(String fileName) throws Exception {
		FileInputStream inputStream = new FileInputStream(fileName);
		compilerbau21.compiler.FileReaderIntf fileReader = new compilerbau21.compiler.FileReader(inputStream);
		return fileReader;
	}
}
