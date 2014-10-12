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
            pw.println("	.file 	" + nameFile +".ctds");
            pw.println("	.text	");

            genInstrAssembly();

            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void genInstrAssembly() {
		for (InstrCode instr : instrList) {
			switch (instr.getOperator()) {
				case METHODLABEL:
					methodLabelInstrAssembly(instr);
					break;
				case METHODEND:
					methodEndInstrAssembly(instr);
					break;
				case PLUS:
					plusInstrAssembly(instr);
					break;
				case MINUS:
					minusInstrAssembly(instr);
					break;
				case MULT:
					multInstrAssembly(instr);
					break;
				case DIV:
					divInstrAssembly(instr);
					break;
				case MOD:
					modInstrAssembly(instr);
					break;
				case LT:
					ltInstrAssembly(instr);
					break;
				case GT:
					gtInstrAssembly(instr);
					break;
				case LTEQ:
					lteqInstrAssembly(instr);
					break;
				case GTEQ:
					gteqInstrAssembly(instr);
					break;
				case EQEQ:
					eqeqInstrAssembly(instr);
					break;
				case NOTEQ:
					noteqInstrAssembly(instr);
					break;
				case ANDAND:
					andandInstrAssembly(instr);
					break;
				case NOT:
					notInstrAssembly(instr);
					break;
				case OROR:
					ororInstrAssembly(instr);
					break;
				case PLUSEQ:
					pluseqInstrAssembly(instr);
					break;
				case MINUSEQ:
					minuseqInstrAssembly(instr);
					break;
				case EQ:
					eqInstrAssembly(instr);
					break;
				case RET:
					retInstrAssembly(instr);
					break;
				case CMP:
					cmpInstrAssembly(instr);
					break;
				case JNE:
					jneInstrAssembly(instr);
					break;
				case JMP:
					jmpInstrAssembly(instr);
					break;
				case LABEL:
					labelInstrAssembly(instr);
					break;
				case JGE:
					jgeInstrAssembly(instr);
					break;
				case INC:
					incInstrAssembly(instr);
					break;
				case PUSH:
					pushInstrAssembly(instr);
					break;
				case CALL:
					callInstrAssembly(instr);
					break;
				case DELPARAMS:
					delparamsInstrAssembly(instr);
					break;
			}
		}
	}

	private void methodLabelInstrAssembly(InstrCode instr) {
		pw.println("	.globl	" + instr.getResult());
		pw.println("	.type	" + instr.getResult() + ", @function");
		pw.println(instr.getResult() + ":");
		pw.println("	pushl 	%ebp");
		pw.println("	movl 	%esp, %ebp");
	}

	private void methodEndInstrAssembly(InstrCode instr) {
		pw.println("	popl	%ebp");
		pw.println("	leave");
		pw.println("	ret");
	}

	private void plusInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %edx");
		pw.println("addl		%eax, %edx");
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");		
	}

	private void minusInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %edx");
		pw.println("subl		%eax, %edx");
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");
	}

	private void multInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %edx");
		pw.println("imull		%eax, %edx");
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");
	}

	private void divInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("cltd");
		pw.println("idivl		" + instr.getRightOperand());
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void modInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("cltd");
		pw.println("idivl		" + instr.getLeftOperand());
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");
	}

	private void ltInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("cmpl		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("setl		%al");
		pw.println("movzbl 		%al, %eax");
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void gtInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() +"(%rbp), %eax");
		pw.println("cmpl		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("setg		%al");
		pw.println("movzbl		%al, %eax");
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void lteqInstrAssembly(InstrCode instr) {
		pw.println("mov		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("cmp		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("setle 	%al");
		pw.println("movzbl %al, %eax");
		pw.println("mov		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void gteqInstrAssembly(InstrCode instr) {
		pw.println("mov		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("cmp		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("setge 	%al");
		pw.println("movzb %al, %eax");
		pw.println("mov		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void eqeqInstrAssembly(InstrCode instr) {
		pw.println("mov 	" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("cmp		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("sete		%al");
		pw.println("movzb	%al, %eax");
		pw.println("mov		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void noteqInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("cmpl		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("setne 	%al");
		pw.println("movzbl %al, %eax");
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void andandInstrAssembly(InstrCode instr) {
		pw.println("cmpl		$0, " + instr.getLeftOperand() + "(%rbp)");
		pw.println("je 			.L2");
		pw.println("cmpl		$0, " + instr.getRightOperand() + "(%rbp)");
		pw.println("je 			.L2");
		pw.println("movl		$1, %eax");
		pw.println("jmp			.L3");
		pw.println(".L2:");
		pw.println("movl		$0, %eax");
		pw.println(".L3:");
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void notInstrAssembly(InstrCode instr) {
		pw.println("cmpl		$0, " + instr.getLeftOperand() + "(%rbp)");
		pw.println("sete		%al");
		pw.println("movzbl		%al, %eax");
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void ororInstrAssembly(InstrCode instr) {
		pw.println("cmpl		$0, " + instr.getLeftOperand() + "(%rbp)");
		pw.println("jne 		.L2");
		pw.println("cmpl		$0, " + instr.getRightOperand() + "(%rbp)");
		pw.println("je 			.L3");
		pw.println(".L2:");
		pw.println("movl		$1, %eax");
		pw.println("jmp 		.L4");
		pw.println(".L3:");
		pw.println("movl		$0, %eax");
		pw.println(".L4:");
		pw.println("movl		%eax, " + instr.getResult() + "(%rbp)");
	}

	private void pluseqInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %eax");
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %edx");					
		pw.println("addl		%eax, %edx");
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");
	}

	private void minuseqInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %edx");					
		pw.println("subl		%eax, %edx");
		pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");
	}

	private void eqInstrAssembly(InstrCode instr) {

	}

	private void retInstrAssembly(InstrCode instr) {
		if (instr.getResult() != null) 
	 		pw.println("movl		" + instr.getResult() + "(%rbp), %eax");
	 	else 
			pw.println("mov 		$0, %eax");
	}

	private void cmpInstrAssembly(InstrCode instr) {
		pw.println("mov " + instr.getRightOperand() + "(%rbp), %eax");
		pw.println("cmp " + instr.getLeftOperand() + "(%rbp), %eax");
	}

	private void jneInstrAssembly(InstrCode instr) {
		pw.println("jne 		" + instr.getResult());
	}

	private void jmpInstrAssembly(InstrCode instr) {
		pw.println("jmp 		"+ instr.getResult());
	}

	private void labelInstrAssembly(InstrCode instr) {
		pw.println("." + instr.getResult() + " ");   
	}

	private void jgeInstrAssembly(InstrCode instr) {

	}

	private void incInstrAssembly(InstrCode instr) {

	}

	private void pushInstrAssembly(InstrCode instr) {

	}

	private void callInstrAssembly(InstrCode instr) {
		pw.println("call 	" + instr.getLeftOperand() + " ");		
  		if (instr.getResult() != null)
  			pw.println("mov 	%eax, " + instr.getResult() + "(%rbp)  ");
	}

	private void delparamsInstrAssembly(InstrCode instr) {
		pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %edi");
		pw.println("movl	 	%edi, " + instr.getResult() + "(%rsp)");
	}

}