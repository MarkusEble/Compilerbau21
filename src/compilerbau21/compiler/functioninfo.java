package compilerbau21.compiler;

public class FunctionInfo {
	public String m_name;
	public InstrBlock m_body;
	public FunctionInfo(String name, InstrBlock body) {
		m_name = name;
		m_body = body;
	}
}
