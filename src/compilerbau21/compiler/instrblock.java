package compilerbau21.compiler;

import java.util.ArrayList;
import java.util.Iterator;

public class InstrBlock {
	private ArrayList<InstrIntf> m_instrList;

	public InstrBlock() {
		m_instrList = new ArrayList<InstrIntf>();
	}
	
	/**
	 * add instruction at end
	 */
	public void addInstr(InstrIntf instr) {
		m_instrList.add(instr);
	}

	/**
	 * get iterator over all instructions in block
	 * @return
	 */
	public Iterator<InstrIntf> getIterator() {
		return m_instrList.listIterator();
	}
	
}
