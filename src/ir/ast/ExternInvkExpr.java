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
	private LinkedList<Expression> largs;

	public ExternInvkExpr() {
		
	}

	public ExternInvkExpr(String id, Type type) {
		this.idInvk = id;
		this.type = type;
	}

	public ExternInvkExpr(String id, Type type, LinkedList<Expression> args) {
		this.idInvk = id;
		this.type = type;
		this.largs = args;
	}

	public String getId(){
		return idInvk;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
