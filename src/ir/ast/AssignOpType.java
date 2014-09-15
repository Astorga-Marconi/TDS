package ir.ast;

public enum AssignOpType {
	INCREMENT,
	DECREMENT,
	ASSIGN;
	
	@Override
	public String toString() {
		switch(this) {
			case INCREMENT:
				return "+=";
			case DECREMENT:
				return "-=";
			case ASSIGN:
				return "=";
		}
		
		return null;		
	}
}
