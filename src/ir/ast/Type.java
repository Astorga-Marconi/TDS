package ir.ast;

public enum Type {
	INT,
	FLOAT,
	INTARRAY,
	VOID,
	UNDEFINED;
	
	@Override
	public String toString() {
		switch(this) {
			case INT:
				return "int";
			case FLOAT:
				return "float";
			case VOID:
				return "void";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "int[]";
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}
}
