/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: AssignOpType.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

public enum AssignOpType {
	PLUSEQ,
	MINUSEQ,
	EQ;
	 
	@Override
	public String toString() {
		switch(this) {
			case PLUSEQ:
				return "+=";
			case MINUSEQ:
				return "-=";
			case EQ:
				return "=";
		}
		
		return null;		
	}
}