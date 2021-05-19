package compilerbau21.compiler;

public interface FunctionTableIntf {

    /*
     *  construct an empty function table	
     */
	// public FunctionTableIntf();

	/*
	 *  creates function with given name and body
	 */
	public void createFunction(String fctName, InstrBlock body);

	/*
	 *  get function for given fctName, returns null if no function with the given name was found
	 */
	public FunctionInfo getFunction(String fctName);
}
