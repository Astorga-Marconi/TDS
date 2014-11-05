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
import ir.ast.*;

public class AssemblyGenerator {

	private List<InstrCode> instrList;
	private List<String> assemblyCode;

	private FileWriter file = null;
	private PrintWriter pw = null;

	private Integer labelsIdGen = 0;

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
            //pw.println("	.file 	" + nameFile +".ctds");

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
				case NEG:
					negInstrAssembly(instr);
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
				case JGE:
					jgeInstrAssembly(instr);
					break;
				case JL:
					jlInstrAssembly(instr);
					break;
				case JLE:
					jleInstrAssembly(instr);
					break;
				case JMP:
					jmpInstrAssembly(instr);
					break;
				case ARRAYVALUE:
					arrayValueInstrAssembly(instr);
					break;
				case LABEL:
					labelInstrAssembly(instr);
					break;
				case INC:
					incInstrAssembly(instr);
					break;
				case STRING:
					stringInstrAssembly(instr);
					break;
				case INITFLOATLOCATION:
					initFloatLocationInstrAssembly(instr);
					break;
				case INITLOCALFLOAT:
					initLocalFloatInstrAssembly(instr);
					break;
				case INITGLOBALVAR:
					initGlobalVarInstrAssembly(instr);
					break;
				case INITGLOBALARRAY:
					initGlobalArrayInstrAssembly(instr);
					break;
				case PUSH:
					pushInstrAssembly(instr);
					break;
				case CALL:
					callInstrAssembly(instr);
					break;
				case METHODRET:
					methodRetInstrAssembly(instr);
					break;
				case DELPARAMS:
					delparamsInstrAssembly(instr);
					break;
			}
		}
	}

	private void methodLabelInstrAssembly(InstrCode instr) {
		pw.println("	.text	");
		pw.println("	.globl	" + instr.getResult());
		pw.println("	.type	" + instr.getResult() + ", @function");
		pw.println(instr.getResult() + ":");
		pw.println("	pushl 	%ebp");
		pw.println("	movl 	%esp, %ebp");
		pw.println("	subl	$200, %esp "); // cada metodo tendria q saber que offset poner
											 // Puse 200 por ahora , pero ahi que cambiarlo	
	}

	private void methodEndInstrAssembly(InstrCode instr) {
		pw.println("	leave");
		pw.println("	ret");
	}

	private void plusInstrAssembly(InstrCode instr) {
		if (instr.getResult().getType() == Type.TINT){
			if (instr.getRightOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getRightOperand() + ", %eax");
			} else {
				pw.println("	movl	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			}
			pw.println("	movl	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %edx");
			pw.println("	addl	%eax, %edx");
			pw.println("	movl	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else if (instr.getResult().getType() == Type.TFLOAT) {
			pw.println("	flds 	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)	# suma de float");
			pw.println("	flds 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
			pw.println("	faddp 	%st, %st(1)");
			pw.println("	fstps 	" + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else {
			pw.println("SUMA: LA OPERACION NO TIENE ASIGNADO UN TIPO DE RESULTADO");
		}
	} 

	private void minusInstrAssembly(InstrCode instr) {
		if (instr.getResult().getType() == Type.TINT){
			if (instr.getRightOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getRightOperand() + ", %eax");
			} else {
				pw.println("	movl	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			}
			pw.println("	movl	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %edx");
			pw.println("	subl	%eax, %edx");
			pw.println("	movl	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else if (instr.getResult().getType() == Type.TFLOAT) {
			pw.println("	flds 	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)	# resta de float");
			pw.println("	flds 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
			pw.println("	fsubrp 	%st, %st(1)");
			pw.println("	fstps 	" + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else {
			pw.println("RESTA: LA OPERACION NO TIENE ASIGNADO UN TIPO DE RESULTADO");
		}
	}

	private void multInstrAssembly(InstrCode instr) {
		if (instr.getResult().getType() == Type.TINT){
			pw.println("	movl    " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			pw.println("	imull   " + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
			pw.println("	movl    %eax, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else if (instr.getResult().getType() == Type.TFLOAT) {
			pw.println("	flds 	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)	# multiplicacion de float");
			pw.println("	flds 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
			pw.println("	fmulp 	%st, %st(1)");
			pw.println("	fstps 	" + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else {
			pw.println("MULTIPLICACION: LA OPERACION NO TIENE ASIGNADO UN TIPO DE RESULTADO");
		}
	}

	private void divInstrAssembly(InstrCode instr) {
		if (instr.getResult().getType() == Type.TINT){
			pw.println("	movl    " + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
			pw.println("	movl	%eax, %edx");
			pw.println("	sarl	%31, %edx");
			pw.println("	idivl   " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
			pw.println("	movl     %eax, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else if (instr.getResult().getType() == Type.TFLOAT) {
			pw.println("	flds 	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)	# division de float");
			pw.println("	flds 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
			pw.println("	fdivrp 	%st, %st(1)");
			pw.println("	fstps 	" + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else {
			pw.println("DIVISION: LA OPERACION NO TIENE ASIGNADO UN TIPO DE RESULTADO");
		}
	}

	private void modInstrAssembly(InstrCode instr) {
		if (instr.getResult().getType() == Type.TINT){
			pw.println("	movl    " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			pw.println("	cltd ");
			pw.println("	idivl   " + ((Location)instr.getLeftOperand()).getOffset());
			pw.println("	movl    %edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else if (instr.getResult().getType() == Type.TFLOAT) {
			
		} else {
			pw.println("MODULO: LA OPERACION NO TIENE ASIGNADO UN TIPO DE RESULTADO");
		}
	}

	private void negInstrAssembly(InstrCode instr) {
		if (instr.getResult().getType() == Type.TINT){
			if (instr.getLeftOperand() instanceof VarLocation) {
				pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
			} else if (instr.getLeftOperand() instanceof Literal) {
				pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
			}
			pw.println("	negl 	%eax");
			pw.println("	movl 	%eax, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else if (instr.getResult().getType() == Type.TFLOAT) {
			pw.println("	flds 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)	# negacion de float");
			pw.println("	fchs");
			pw.println("	fstps 	" + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		} else {
			pw.println("NEGACION: LA OPERACION NO TIENE ASIGNADO UN TIPO DE RESULTADO");
		}
	}

	private void ltInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
		} else if (instr.getLeftOperand() instanceof Location){
			pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		}
		if (instr.getRightOperand() instanceof IntLiteral) {
			pw.println("	cmpl 	%eax, $" + instr.getRightOperand());
		} else if (instr.getRightOperand() instanceof Location){
			pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		}
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endEqEq" + Integer.toString(labelsIdGen++);
		pw.println("	jge 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void gtInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
		} else if (instr.getLeftOperand() instanceof Location){
			pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		}
		if (instr.getRightOperand() instanceof IntLiteral) {
			pw.println("	cmpl 	%eax, $" + instr.getRightOperand());
		} else if (instr.getRightOperand() instanceof Location){
			pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		}
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endEqEq" + Integer.toString(labelsIdGen++);
		pw.println("	jle 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void lteqInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
		} else if (instr.getLeftOperand() instanceof Location){
			pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		}
		if (instr.getRightOperand() instanceof IntLiteral) {
			pw.println("	cmpl 	%eax, $" + instr.getRightOperand());
		} else if (instr.getRightOperand() instanceof Location){
			pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		}
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endEqEq" + Integer.toString(labelsIdGen++);
		pw.println("	jg 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void gteqInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
		} else if (instr.getLeftOperand() instanceof Location){
			pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		}
		if (instr.getRightOperand() instanceof IntLiteral) {
			pw.println("	cmpl 	%eax, $" + instr.getRightOperand());
		} else if (instr.getRightOperand() instanceof Location){
			pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		}
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endEqEq" + Integer.toString(labelsIdGen++);
		pw.println("	jl 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void eqeqInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
		} else if (instr.getLeftOperand() instanceof Location){
			pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		}
		if (instr.getRightOperand() instanceof IntLiteral) {
			pw.println("	cmpl 	%eax, $" + instr.getRightOperand());
		} else if (instr.getRightOperand() instanceof Location){
			pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		}
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endEqEq" + Integer.toString(labelsIdGen++);
		pw.println("	jne 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void noteqInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	movl 	$" + instr.getLeftOperand() + ", %eax");
		} else if (instr.getLeftOperand() instanceof Location){
			pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		}
		if (instr.getRightOperand() instanceof IntLiteral) {
			pw.println("	cmpl 	%eax, $" + instr.getRightOperand());
		} else if (instr.getRightOperand() instanceof Location){
			pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		}
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endEqEq" + Integer.toString(labelsIdGen++);
		pw.println("	je 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void andandInstrAssembly(InstrCode instr) {
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endAnd" + Integer.toString(labelsIdGen++);
		pw.println("	movl 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %eax");
		pw.println("	cmpl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		pw.println("	jne 	." + labelFalse);
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void ororInstrAssembly(InstrCode instr) {
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endAnd" + Integer.toString(labelsIdGen++);
		pw.println("	cmpl 	$1, " + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
		pw.println("	je 	." + labelTrue);
		pw.println("	cmpl 	$1, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
		pw.println("	je 	." + labelTrue);
		pw.println("	jmp 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void notInstrAssembly(InstrCode instr) {
		String labelTrue = "isTrue" + Integer.toString(labelsIdGen++);
		String labelFalse = "isFalse" + Integer.toString(labelsIdGen++);
		String labelEnd = "endNot" + Integer.toString(labelsIdGen++);
		pw.println("	cmpl 	$1, " + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
		pw.println("	je 	." + labelFalse);
		pw.println("	." + labelTrue + ":");
		pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	jmp 	." + labelEnd);
		pw.println("	." + labelFalse + ":");
		pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		pw.println("	." + labelEnd + ":");
	}

	private void pluseqInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	addl 	$" + instr.getLeftOperand() + ", " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		}
	}

	private void minuseqInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	subl 	$" + instr.getLeftOperand() + ", " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		}
		//pw.println("movl		" + instr.getRightOperand() + "(%rbp), %eax");
		//pw.println("movl		" + instr.getLeftOperand() + "(%rbp), %edx");					
		//pw.println("subl		%eax, %edx");
		//pw.println("movl		%edx, " + instr.getResult() + "(%rbp)");
	}

	private void eqInstrAssembly(InstrCode instr) {
		if (instr.getResult() instanceof ArrayLocation) {	// Si se debe asignar a una direccion de un arreglo.
			//	Guardo la expression del ArrayLocation en eax.
			if (instr.getRightOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getRightOperand() + ", %eax");
			} else if (instr.getRightOperand() instanceof VarLocation) {
				pw.println("	movl	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			}
			//	Coloco la expression izquierda en el ArrayLocation.
			if (instr.getLeftOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getLeftOperand() + ", " + ((Location)instr.getResult()).getOffset() + "(%ebp,%eax,4)");
			} else if (instr.getLeftOperand() instanceof VarLocation) {
				pw.println("	movl	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %edx");
				pw.println("	movl 	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp,%eax,4)");
			}

		} else if (instr.getResult() instanceof GlobalArrayLocation) {	// Si se debe asignar a una direccion de un arreglo global.
			//	Guardo la expression del GlobalArrayLocation en eax.
			if (instr.getRightOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getRightOperand() + ", %eax");
			} else if (instr.getRightOperand() instanceof VarLocation) {
				pw.println("	movl	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			}
			//	Coloco la expression izquierda en el GlobalArrayLocation.
			if (instr.getLeftOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getLeftOperand() + ", " + ((Location)instr.getResult()).getId() + "(,%eax,4)");
			} else if (instr.getLeftOperand() instanceof VarLocation) {
				pw.println("	movl	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp), %edx");
				pw.println("	movl 	%edx, " + ((Location)instr.getResult()).getId() + "(,%eax,4)");
			}

		} else if (instr.getResult() instanceof VarLocation) {	// Si se debe asignar a una variable.
			if (instr.getLeftOperand() instanceof FloatLiteral) {

			} else if (instr.getLeftOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getLeftOperand() + ", " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
			} else if (instr.getLeftOperand() instanceof VarLocation) {
				pw.println("	movl 	" + ((Location)instr.getLeftOperand() ).getOffset() + "(%ebp), %edx");
				pw.println("	movl 	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
			} else if (instr.getLeftOperand() instanceof BoolLiteral){
				if (((""+instr.getLeftOperand()).equals("true"))) {
					pw.println("	movl 	$1, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
				} else if (((""+instr.getLeftOperand()).equals("false"))) {
					pw.println("	movl 	$0, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
				}
			} else if (instr.getLeftOperand() instanceof GlobalVarLocation) {
				pw.println("	movl	" + instr.getLeftOperand() + ", " + "%eax");
				pw.println("	movl	%eax, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
			} else {
				// Supongo que valor a asignar esta en edx.
				pw.println("	movl	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
			}
		} else if (instr.getResult() instanceof GlobalVarLocation) {
			if (instr.getLeftOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getLeftOperand() + ", " + instr.getResult());
			} else if (instr.getLeftOperand() instanceof VarLocation) {
				pw.println("	movl 	" + ((Location)instr.getLeftOperand() ).getOffset() + "(%ebp), %edx");
				pw.println("	movl 	%edx, " + instr.getResult());
			} else if (instr.getLeftOperand() instanceof BoolLiteral){
				//	FALTA
			} else if (instr.getLeftOperand() instanceof GlobalVarLocation) {
				//	FALTA
			} else {
				//	FALTA
			}
		}
	}

	private void retInstrAssembly(InstrCode instr) {
		if (instr.getResult() != null) {
			if (instr.getResult() instanceof Literal) {
				pw.println("	movl 	$" + instr.getResult() + ", %eax");
			} else if (instr.getResult() instanceof VarLocation) {
				pw.println("	movl 	" +  ((Location)instr.getResult()).getOffset() + "(%ebp), %eax");
			}
		} else {	// Si el Return no tiene parametros.
			pw.println("	mov 	$0, %eax");
		}
	}

	private void cmpInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof Literal && instr.getRightOperand() instanceof Literal)
			pw.println("	cmpl 	$" + instr.getLeftOperand() + ", $" + instr.getRightOperand());
		if (instr.getLeftOperand() instanceof VarLocation && instr.getRightOperand() instanceof BoolLiteral){
			if (((""+instr.getRightOperand()).equals("true"))) {
				pw.println("	cmpl 	$1, " + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
			} else if (((""+instr.getRightOperand()).equals("false"))) {
				pw.println("	cmpl 	$0, " + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
			}
		}
	}

	private void jneInstrAssembly(InstrCode instr) {
		pw.println("	jne 	." + instr.getResult());
	}

	private void jlInstrAssembly(InstrCode instr) {
		pw.println("	jl 	." + instr.getResult());
	}

	private void jleInstrAssembly(InstrCode instr) {
		pw.println("	jle 	." + instr.getResult());
	}

	private void jmpInstrAssembly(InstrCode instr) {
		pw.println("	jmp 	."+ instr.getResult());
	}

	private void arrayValueInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof GlobalArrayLocation) {
			if (instr.getRightOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getRightOperand() + ", %eax");
			} else if (instr.getRightOperand() instanceof VarLocation) {
				pw.println("	movl	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			}
			pw.println("	movl	" + ((Location)instr.getLeftOperand()).getId() + "(,%eax,4), %edx");
			pw.println("	movl 	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");

		} else if (instr.getLeftOperand() instanceof ArrayLocation) {
			if (instr.getRightOperand() instanceof IntLiteral) {
				pw.println("	movl	$" + instr.getRightOperand() + ", %eax");
			} else if (instr.getRightOperand() instanceof VarLocation) {
				pw.println("	movl	" + ((Location)instr.getRightOperand()).getOffset() + "(%ebp), %eax");
			}
			pw.println("	movl	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp,%eax,4), %edx");
			pw.println("	movl 	%edx, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
		}
	}

	private void labelInstrAssembly(InstrCode instr) {
		pw.println("." + instr.getResult() + ":");
	}

	private void jgeInstrAssembly(InstrCode instr) {
		pw.println("	jge 	." + instr.getResult());
	}

	private void incInstrAssembly(InstrCode instr) {
		pw.println("	addl 	$1 ," + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)	# incremento la variable del for");
	}

	private void stringInstrAssembly(InstrCode instr) {
		pw.println(instr.getLeftOperand() + ":");
		pw.println("	.string " + '"' + instr.getRightOperand() + '"');
	}

	private void initFloatLocationInstrAssembly(InstrCode instr) {
		pw.println("	movl 	." + instr.getLeftOperand() + ", %eax");
		pw.println("	movl 	%eax, " + ((Location)instr.getRightOperand()).getOffset() + "(%ebp)");
	}

	private void initLocalFloatInstrAssembly(InstrCode instr) {
		pw.println("." + instr.getLeftOperand() + ":");
		pw.println("	.float " + instr.getResult());
	}

	private void initGlobalVarInstrAssembly(InstrCode instr) {
		/* Codigo usado para inicializar con cero los valores, pero me dijo P que no hacia falta inicializarlo con 0
		pw.println("	.globl 	" + ((Location)instr.getLeftOperand()).getId());
		pw.println("	.data");
		pw.println("	.type 	" + ((Location)instr.getLeftOperand()).getId() + ", @object");
		pw.println("	.size 	" + ((Location)instr.getLeftOperand()).getId() + ", 4");
		pw.println(((Location)instr.getLeftOperand()).getId() + ":");
		pw.println("	.zero 	4");*/
		pw.println("	.comm	" + ((Location)instr.getLeftOperand()).getId() + ",4,4");
	}

	private void initGlobalArrayInstrAssembly(InstrCode instr) {
		pw.println("	.comm	" + ((Location)instr.getLeftOperand()).getId() + "," + ((GlobalArrayLocation)instr.getLeftOperand()).getSizeArray()*4 + ",4");
	}

	private void pushInstrAssembly(InstrCode instr) {
		if (instr.getLeftOperand() instanceof VarLocation) {
			pw.println("	push 	" + ((Location)instr.getLeftOperand()).getOffset() + "(%ebp)");
		}else if (instr.getLeftOperand() instanceof IntLiteral) {
			pw.println("	push 	$" + instr.getLeftOperand());			
		}
	}

	private void callInstrAssembly(InstrCode instr) {
		pw.println("	call 	" + instr.getLeftOperand());
	}

	private void methodRetInstrAssembly(InstrCode instr) {
		pw.println("	mov 	%eax, " + ((Location)instr.getResult()).getOffset() + "(%ebp)");
	}

	private void delparamsInstrAssembly(InstrCode instr) {
		int num = Integer.parseInt(""+instr.getLeftOperand()) * 4;
		pw.println("	addl 	$" + num + ", %esp");
	}

}
