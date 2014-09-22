package ir.ast;

public enum Type {
	TINT,
	TFLOAT,
	TBOOLEAN,
	INTARRAY,
	FLOATARRAY,
	BOOLEANARRAY,
	VOID,
	UNDEFINED;
	
	@Override
	public String toString() {
		switch(this) {
			case TINT:
				return "int";
			case TFLOAT:
				return "float";
			case TBOOLEAN:
		        return "boolean";
			case VOID:
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
