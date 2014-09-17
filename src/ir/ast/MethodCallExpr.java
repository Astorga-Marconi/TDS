package ir.ast;

import ir.ASTVisitor;

public class MethodCallExpr extends Expression {

	public MethodCallExpr() {

	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
