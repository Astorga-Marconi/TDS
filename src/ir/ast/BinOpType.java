package ir.ast;

public enum BinOpType {
	PLUS, 
	MINUS,
	MULT,
	DIV,
	MOD,
	LT, 
	GT,
	LTEQ,
	GTEQ,
	EQEQ, 
	NOTEQ, 
	ANDAND,
	NOT,
	OROR;
	
	@Override
	public String toString() {
		switch(this) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case MULT:
				return "*";
			case DIV:
				return "/";
			case MOD:
				return "%";
			case LT:
				return "<";
			case GT:
				return ">";
			case LTEQ:
				return "<=";
			case GTEQ:
				return ">=";
			case EQEQ:
				return "==";
			case NOTEQ:
				return "!=";
			case ANDAND:
				return "&&";
			case NOT:
				return "!";
			case OROR:
				return "||";
		}
		
		return null;
	}
}
