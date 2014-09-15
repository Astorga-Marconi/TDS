package ir.ast;

public enum BinOpType {
	PLUS, // Arithmetic
	MINUS,
	MULTIPLY,
	DIVIDE,
	MOD,
	LE, // Relational
	LEQ,
	GE,
	GEQ,
	NEQ, // Equal
	CEQ, 
	AND, // Conditional
	OR;
	
	@Override
	public String toString() {
		switch(this) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case MULTIPLY:
				return "*";
			case DIVIDE:
				return "/";
			case MOD:
				return "%";
			case LE:
				return "<";
			case LEQ:
				return "<=";
			case GE:
				return ">";
			case GEQ:
				return ">=";
			case CEQ:
				return "==";
			case NEQ:
				return "!=";
			case AND:
				return "&&";
			case OR:
				return "||";
		}
		
		return null;
	}
}
