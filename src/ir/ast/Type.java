/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Type.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

public enum Type {
	TINT,
	TFLOAT,
	TBOOLEAN,
	TVOID,
	UNDEFINED,
	INTARRAY,
	FLOATARRAY,
	BOOLEANARRAY;
	
	@Override
	public String toString() {
		switch(this) {
			case TINT:
				return "int";
			case TFLOAT:
				return "float";
			case TBOOLEAN:
		        return "boolean";
			case TVOID:
				return "void";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "int[]";
			case FLOATARRAY:
				return "float[]";
			case BOOLEANARRAY:
				return "boolean[]";
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		if (this == Type.FLOATARRAY) {
			return true;
		}
		if (this == Type.BOOLEANARRAY) {
			return true;
		}
		
		return false;
	}
}
