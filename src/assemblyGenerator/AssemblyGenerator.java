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
package assemblyGenerator;

import java.util.*;
import ir.CodeGen.*;
import ir.CodeGen.InstrCode;
import java.io.*;

public class AssemblyGenerator {

	private List<InstrCode> instrList;
	private List<String> assemblyCode;

	private FileWriter file = null;
	private PrintWriter pw = null;

	public AssemblyGenerator(){
		instrList = new LinkedList<InstrCode>();
		assemblyCode = new LinkedList<String>();
	}

	public AssemblyGenerator(List<InstrCode> l){
		instrList = l;
		assemblyCode = new LinkedList<String>();
	}

	public void setInstrList(List<InstrCode> l) {
		instrList = l;
	}

	public List<InstrCode> getInstrList() {
		return instrList;
	}

	public void generateAssembly(String nameFile) {
		if (instrList.size() == 0) throw new IllegalStateException("No existe codigo intermedio generado.");

        try 
        {
        	file = new FileWriter(nameFile + ".s");
            pw = new PrintWriter(file);

            /* -- Encabezado -- */  
            pw.println(".file " + nameFile +".ctds");
            pw.println(".text ");
            pw.println(".globl ");
            pw.println(".type ");

            genInstrAssembly();

            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void genInstrAssembly() {
		for (InstrCode instr : instrList) {
			switch (instr.getOperator()) {
				case PLUS:
						plusInstrAssembly(instr);
						break;
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
						assemblyCode.add("movl		" + instr.getLeftOperand() + "(%rbp), %eax \n");
						assemblyCode.add("cltd\n");
						assemblyCode.add("idivl	" + instr.getRightOperand() + "\n");
						assemblyCode.add("movl		%eax, " + instr.getResult() + "(%rbp)\n");
				case MOD:
						assemblyCode.add("movl		" + instr.getRightOperand() + "(%rbp), %eax \n");
						assemblyCode.add("cltd\n");
						assemblyCode.add("idivl	" + instr.getLeftOperand() + "\n");
						assemblyCode.add("movl		%edx, " + instr.getResult() + "(%rbp)\n");
				case LT:
			    		assemblyCode.add("movl		" + instr.getLeftOperand() + "(%rbp), %eax\n");
						assemblyCode.add("cmpl		" + instr.getRightOperand() + "(%rbp), %eax\n");
						assemblyCode.add("setl		%al\n");
						assemblyCode.add("movzbl 	%al, %eax\n");
						assemblyCode.add("movl		%eax, " + instr.getResult() + "(%rbp)\n");
				case GT:
			    		assemblyCode.add("movl		" + instr.getLeftOperand() +"(%rbp), %eax\n");
						assemblyCode.add("cmpl		" + instr.getRightOperand() + "(%rbp), %eax\n");
						assemblyCode.add("setg		%al\n");
						assemblyCode.add("movzbl	%al, %eax\n");
						assemblyCode.add("movl		%eax, " + instr.getResult() + "(%rbp)\n");
				case LTEQ:
						assemblyCode.add("LTEQ");
				case GTEQ:
						assemblyCode.add("GTEQ");
				case EQEQ:
						assemblyCode.add("EQEQ");
				case NOTEQ:
						assemblyCode.add("NOTEQ");
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
				case NOT:
						assemblyCode.add("cmpl		$0, " + instr.getLeftOperand() + "(%rbp) \n");
						assemblyCode.add("sete		%al \n");
						assemblyCode.add("movzbl	%al, %eax \n");
						assemblyCode.add("movl		%eax, " + instr.getResult() + "(%rbp) \n");
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
				case PLUSEQ:
						assemblyCode.add("PLUSEQ");  
				case MINUSEQ:
						assemblyCode.add("MINUSEQ");
				case EQ:
						assemblyCode.add("EQ");
				case RET:
						assemblyCode.add("RET");
				case CMP:
						assemblyCode.add("CMP");
				case JNE:
						assemblyCode.add("jne 		" + instr.getResult() + "\n");
				case JMP:
		    			assemblyCode.add("jmp 		"+ instr.getResult() + "\n");
				case LABEL:	
						assemblyCode.add("LABEL");
				case JGE:
						assemblyCode.add("JGE");	
				case INC:
						assemblyCode.add("INC");
				case PUSH:
						assemblyCode.add("PUSH");
				case CALL:
						assemblyCode.add("CALL");
				case DELPARAMS:
						assemblyCode.add("movl		" + instr.getLeftOperand() + "(%rbp), %edi\n");
						assemblyCode.add("movl	 	%edi, " + instr.getResult() + "(%rsp)\n");
			}
			assemblyCode.add("\n");		
		}
	}

	private void plusInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %edx");
		pw.println("addl		%eax, %edx");
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");		
	}

}