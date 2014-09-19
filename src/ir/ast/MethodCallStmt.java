package ir.ast;

import ir.ASTVisitor;
import java.util.*;

public class MethodCallStmt extends Statement {

	private String id;
	private LinkedList<Expression> expressions;

	public MethodCallStmt(String id) {
		this.id = id;
	}

	public MethodCallStmt(String id, LinkedList<Expression> exprs) {
		this.id = id;
		expressions = exprs;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
