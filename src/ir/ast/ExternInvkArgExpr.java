package ir.ast;

import ir.ASTVisitor;

public class ExternInvkArgExpr extends ExternInvkArg {

	private Expression expr;

	public ExternInvkArgExpr(Expression expr) {
		this.expr = expr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
