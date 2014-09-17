package ir.ast;

import ir.ASTVisitor;

public class NotExp extends Expression {
	private Expression exp;

	public NotExp(	, Expression e) {
		//this.
		this.exp = e;

	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}