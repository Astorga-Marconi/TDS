package AssemblyGenerator;

import java.util.*;
import ir.CodeGen.InstrCode;

public class AssemblyGenerator {

	private List<InstrCode> instrList;
	private List<String> assemblyCode;

	public AssemblyGenerator(){
		instrList = new LinkedList<InstrCode>();
		assemblyCode = new LinkedList<String>();
	}

	public void setInstrList(List<InstrCode> l) {
		instrList = l;
	}

	public List<InstrCode> getInstrList() {
		return instrList;
	}

	public void setAssemblyList(List<String> l) {
		assemblyCode = l;
	}

	public List<String> getAssemblyList() {
		return assemblyCode;
	}

	public void generateAssembly() {
		if (instrList.size() == 0) throw new IllegalStateException("No existe codigo intermedio generado.");
	}

}