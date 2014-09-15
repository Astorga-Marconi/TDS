package decaf.ir.ast;

import decaf.ir.ASTVisitor;

public class Parameter extends AST {
	private Type type;
	private String id;
	
	public Parameter(Type t, String i) {
		type = t;
		id = i;
	}
	
	public void setType(Type t) {
		type = t;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setId(String i) {
		id = i;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return type + " " + id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
