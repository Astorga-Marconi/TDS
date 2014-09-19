package ir.ast;

import ir.ASTVisitor;
import java.util.*;

public class ExternInvkStmt extends Statement {

	private String idInvk;
	private Type type;
	private LinkedList<Expression> largs;

	public ExternInvkStmt() {
		
	}

	public ExternInvkStmt(String id, Type type) {
		this.idInvk = id;
		this.type = type;
	}

	public ExternInvkStmt(String id, Type type, LinkedList<Expression> args) {
		this.idInvk = id;
		this.type = type;
		largs = args;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
