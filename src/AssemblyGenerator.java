/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: AssemblyGenerator.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class: Clase encargada de la generacion del codigo Assembler a partir del codigo intermedio 
 */
package AssemblyGenerator;

import java.util.*;
import ir.CodeGen.*;
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

	public List<String> getAssemblyCode() {
		return assemblyCode;
	}

	public void generateAssembly() {
		if (instrList.size() == 0) throw new IllegalStateException("No existe codigo intermedio generado.");

		// Existe codigo intermedio generado
		// Encabezado (falta)

		for (InstrCode instr : instrList) {
			switch (instr.getOperator()) {

				case ANDAND:
						assemblyCode.add("ANDAND");
					
				case OROR:
						assemblyCode.add("OROR");
					

				case PLUS:
						assemblyCode.add("PLUS");
					
				case MINUS:
						assemblyCode.add("MINUS");
								
				case MULT:
						assemblyCode.add("MULT");
					
				case DIV:
						assemblyCode.add("DIV");
								
			}
			assemblyCode.add("\n");		
		}
	}


}