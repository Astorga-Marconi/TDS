package ir.ast;

import ir.ASTVisitor;

public class BreakStmt extends Statement {

	public BreakStmt() {
		
	}

	@Override
	public String toString() {
			return "break";
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
