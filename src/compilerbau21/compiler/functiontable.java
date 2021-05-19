package compilerbau21.compiler;

import java.util.HashMap;

public class FunctionTable implements FunctionTableIntf {

	private HashMap<String, FunctionInfo> m_functionMap;
	
	public FunctionTable() {
		m_functionMap = new HashMap<String, FunctionInfo>();
	}

	// creates function with given name and body
	public void createFunction(String fctName, InstrBlock body) {
		FunctionInfo fctInfo = new FunctionInfo(fctName, body);
		m_functionMap.put(fctName, fctInfo);
	}

	public FunctionInfo getFunction(String fctName) {
		FunctionInfo fct = m_functionMap.get(fctName);
		return fct;
	}
}
