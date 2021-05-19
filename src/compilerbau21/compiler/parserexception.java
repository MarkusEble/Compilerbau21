package compilerbau21.compiler;

public class ParserException extends Exception {
	private static final long serialVersionUID = 1L;

	public ParserException(String msg0, String msg1, String location, String expected) {
		super(buildMsg(msg0, msg1, location, expected));
	}

	private static String buildMsg(String msg0, String msg1, String location, String expected) {
		String msg = "\n" + msg0 + msg1 + "\n" + location;
		if (!expected.isEmpty()) {
			msg += "Expected: " + expected + "\n";
		}
		return msg;
	}
}

