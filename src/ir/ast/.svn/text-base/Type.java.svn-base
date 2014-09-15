package decaf.ir.ast;

public enum Type {
	INT,
	INTARRAY,
	BOOLEAN,
	BOOLEANARRAY,
	CHAR,
	VOID,
	UNDEFINED;
	
	@Override
	public String toString() {
		switch(this) {
			case INT:
				return "int";
			case CHAR:
				return "char";
			case BOOLEAN:
				return "bool";
			case VOID:
				return "void";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "int[]";
			case BOOLEANARRAY:
				return "boolean[]";
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.BOOLEANARRAY || this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}
}
