/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ExternInvkExpr.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;
import java.util.*;

public class ExternInvkExpr extends Expression {

	private String idInvk;
	private Type type;
	private List<Expression> largs;

	public ExternInvkExpr() {
		
	}

	public ExternInvkExpr(String id, Type type) {
		this.idInvk = id;
		this.type = type;
		largs = new LinkedList<Expression>();
	}

	public ExternInvkExpr(String id, Type type, List<Expression> args) {
		this.idInvk = id;
		this.type = type;
		this.largs = args;
	}

	public String getId(){
		return idInvk;
	}

	public void setType(Type t) {
		type = t;
	}

	public Type getType() {
		return type;
	}

	public void setParameters(List<Expression> newParams) {
		largs = newParams;
	}

	public List<Expression> getParameters() {
		return largs;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
