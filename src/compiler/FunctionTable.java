package compiler;

import java.util.HashMap;
import java.util.List;

public class FunctionTable implements FunctionTableIntf {

	private HashMap<String, FunctionInfo> m_functionMap;
	
	public FunctionTable() {
		m_functionMap = new HashMap<String, FunctionInfo>();
	}

	/**
	 * Creates a function with given Body under given Name in the hashmap (table).
	 * @param fctName Name of the Function
	 * @param body Body of the execution
	 */
	public void createFunction(String fctName, InstrBlock body, List<String> varList) {
		FunctionInfo fctInfo = new FunctionInfo(fctName, body, varList);
		m_functionMap.put(fctName, fctInfo);
	}

	/**
	 * Retrieves the function from the Hashmap (table).
	 * @param fctName Name of the Function
	 * @return FunctionInfo
	 */
	public FunctionInfo getFunction(String fctName) {
		FunctionInfo fct = m_functionMap.get(fctName);
		return fct;
	}
}
