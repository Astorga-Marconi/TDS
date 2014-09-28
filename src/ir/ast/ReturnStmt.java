/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ReturnStmt.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class ReturnStmt extends Statement {
	private Expression expression; 
	
	public ReturnStmt(Expression expr) {
		this.expression = expr;
	}
	
	public ReturnStmt() {
		this.expression = null;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expr) {
		this.expression = expr;
	}
	
	@Override
	public String toString() {
		if (expression == null) {
			return "return";
		}
		else {
			return "return " + expression;
		}
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
