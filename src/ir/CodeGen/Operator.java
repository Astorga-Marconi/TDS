/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Operator.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class: Enumerado del operador del codigo de tres direcciones.
 */

package ir.CodeGen;

public enum Operator {
	PLUS, 
	MINUS,
	MULT,
	DIV,
	MOD,
	NEG,
	LT, 
	GT,
	LTEQ,
	GTEQ,
	EQEQ, 
	NOTEQ, 
	ANDAND,
	NOT,
	OROR,
	PLUSEQ,
	MINUSEQ,
	EQ,
	RET,
	CMP,
	JE,
	JNE,
	JGE,
	JL,
	JLE,
	JMP,
	ARRAYVALUE,
	LABEL,   
	INC,
	STRING,
	INITFLOATLOCATION,
	INITLOCALFLOAT,
	INITGLOBALVAR,
	INITGLOBALARRAY,
	PUSH,
	CALL,
	METHODRET,
	DELPARAMS,
	METHODLABEL,
	METHODEND,
	FLOATPLUS,
	FLOATMINUS,
	FLOATMULT,
	FLOATDIV,
	FLOATMOD;

	@Override
	public String toString() {
		switch(this) {
			case PLUS:
				return "PLUS";
			case MINUS:
				return "MINUS";
			case MULT:
				return "MULT";
			case DIV:
				return "DIV";
			case MOD:
				return "MOD";
			case NEG:
				return "NEG";
			case LT:
				return "LT";
			case GT:
				return "GT";
			case LTEQ:
				return "LTEQ";
			case GTEQ:
				return "GTEQ";
			case EQEQ:
				return "EQEQ";
			case NOTEQ:
				return "NOTEQ";
			case ANDAND:
				return "AND";
			case NOT:
				return "NOT";
			case OROR:
				return "OR";
			case PLUSEQ:
				return "PLUSEQ";
			case MINUSEQ:
				return "MINUSEQ";
			case EQ:
				return "EQ";
			case RET:
				return "RET";
			case CMP:
				return "CMP";
			case JNE:
				return "JNE";
			case JGE:
				return "JGE";
			case JMP:
				return "JMP";
			case ARRAYVALUE:
				return "ARRAYVALUE";
			case LABEL:
				return "LABEL";	
			case STRING:
				return "STRING";
			case INITFLOATLOCATION:
				return "INITFLOATLOCATION";
			case INITLOCALFLOAT:
				return "INITLOCALFLOAT";
			case INITGLOBALVAR:
				return "INITGLOBALVAR";
			case INITGLOBALARRAY:
				return "INITGLOBALARRAY";
			case PUSH:
				return "PUSH";
			case CALL:
				return "CALL";
			case METHODRET:
				return "METHODRET";
			case DELPARAMS:
				return "DELPARAMS";
			case METHODLABEL:
				return "METHODLABEL";
			case METHODEND:
				return "METHODEND";
			case FLOATPLUS:
				return "FLOATPLUS";
			case FLOATMINUS:
				return "FLOATMINUS";
			case FLOATMULT:
				return "FLOATMULT";
			case FLOATDIV:
				return "FLOATDIV";
			case FLOATMOD:
				return "FLOATMOD";
		}
		return null;
	}
}