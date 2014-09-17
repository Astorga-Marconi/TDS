package ir.ast;

import ir.ASTVisitor;

public class SemicolonStmt extends Statement {

	public SemicolonStmt() {
		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
