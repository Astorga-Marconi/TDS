package ir.ast;

import ir.ASTVisitor;

public class ExternInvkArgStringLit extends ExternInvkArg {

	private String str;

	public ExternInvkArgStringLit(String s) {
		str = s;
	}

	public void setString(String s) {
		this.str = s;
	}

	public String getString() {
		return str;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
