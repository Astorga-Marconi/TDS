package decaf.ir.ast;

import decaf.ir.ASTVisitor;

public class ArrayLocation extends Location {
	private Expression expr;
	private int size;
	
	public ArrayLocation(String id, Expression expr) {
		this.id = id;
		this.expr = expr;
		this.size = -1;
	}
	
	public void setExpr(Expression expr) {
		this.expr = expr;
	}
	
	public Expression getExpr() {
		return expr;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return id + "[" + expr + "]";
		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
