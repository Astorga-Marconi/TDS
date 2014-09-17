package ir.ast;

import ir.ASTVisitor;

public class MethodCallStmt extends Statement {

	public MethodCallStmt() {

	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
