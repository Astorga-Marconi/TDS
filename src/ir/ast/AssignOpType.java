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