/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: MethodCallStmt.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;
import java.util.*;

public class MethodCallStmt extends Statement {

	private String id;
	private List<Expression> parameters;

	public MethodCallStmt(String id) {
		this.id = id;
		parameters = new LinkedList<Expression>();
	}

	public MethodCallStmt(String id, List<Expression> params) {
		this.id = id;
		parameters = params;
	}

	public void setId(String newId){
		id = newId;
	}

	public String getId(){
		return id;
	}

	public void setParameters(List<Expression> newParams) {
		parameters = newParams;
	}

	public List<Expression> getParameters() {
		return parameters;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
