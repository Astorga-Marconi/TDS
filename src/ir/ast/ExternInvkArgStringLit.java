package ir.ast;

import ir.ASTVisitor;

public class ExternInvkArgStringLit extends ExternInvkArg {

	private String str;

	public ExternInvkArgStringLit(String s) {
		str = s;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
