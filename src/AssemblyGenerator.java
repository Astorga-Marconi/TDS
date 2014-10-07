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
						assemblyCode.add("cmpl		$0, " + instr.getLeftOperand() + "(%rbp)\n");
						assemblyCode.add("je 		.L2\n");
						assemblyCode.add("cmpl		$0, " + instr.getRightOperand() + "(%rbp)\n");
						assemblyCode.add("je 		.L2\n");
						assemblyCode.add("movl		$1, %eax\n");
						assemblyCode.add("jmp		.L3\n");
						assemblyCode.add(".L2:\n");
						assemblyCode.add("movl		$0, %eax\n");
						assemblyCode.add(".L3:\n");
						assemblyCode.add("movl		%eax, " + instr.getResult() + "(%rbp)\n");	
				case OROR:
						assemblyCode.add("cmpl		$0, " + instr.getLeftOperand() + "(%rbp)\n");
						assemblyCode.add("jne 		.L2\n");
						assemblyCode.add("cmpl		$0, " + instr.getRightOperand() + "(%rbp)\n");
						assemblyCode.add("je 		.L3\n");
						assemblyCode.add(".L2: \n");
						assemblyCode.add("movl		$1, %eax\n");
						assemblyCode.add("jmp 		.L4\n");
						assemblyCode.add(".L3:\n");
						assemblyCode.add("movl		$0, %eax\n");
						assemblyCode.add(".L4:\n");
						assemblyCode.add("movl		%eax, " + instr.getResult() + "(%rbp)\n");
			  	case MOD:
						assemblyCode.add("MOD");
				case PLUS:
						assemblyCode.add("movl		" + instr.getLeftOperand() + "(%rbp), %eax \n");
						assemblyCode.add("movl		" + instr.getRightOperand() + "(%rbp), %edx \n");					
						assemblyCode.add("addl		%eax, %edx \n");
						assemblyCode.add("movl		%edx, " + instr.getResult() + "(%rbp)\n");
				case MINUS:
						assemblyCode.add("movl		" + instr.getRightOperand() + "(%rbp), %eax \n");
						assemblyCode.add("movl		" + instr.getLeftOperand() + "(%rbp), %edx \n");					
						assemblyCode.add("subl		%eax, %edx \n");
						assemblyCode.add("movl		%edx, " + instr.getResult() + "(%rbp)\n");			
				case MULT:
						assemblyCode.add("movl		" + instr.getLeftOperand() + "(%rbp), %eax \n");
						assemblyCode.add("movl		" + instr.getRightOperand() + "(%rbp), %edx \n");					
						assemblyCode.add("imull	%eax, %edx \n");
						assemblyCode.add("movl		%edx, " + instr.getResult() + "(%rbp)\n");	
				case DIV:
						assemblyCode.add("DIV");
				case NOT:
						assemblyCode.add("NOT");					
			}
			assemblyCode.add("\n");		
		}
	}


}