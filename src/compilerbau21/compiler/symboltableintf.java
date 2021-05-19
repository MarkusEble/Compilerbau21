package compilerbau21.compiler;

public interface SymbolTableIntf {

    // construct an empty symbol table	
	// public SymbolTableIntf();

	// creates a symbol with the given name
	public Symbol createSymbol(String symbolName);

	// get symbol for given symbolName, returns null if no symbol with the given name was found
	public Symbol getSymbol(String symbolName);
}
