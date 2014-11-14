/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: NegativeExpr.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class NegativeExpr extends Expression {

	private Expression expr;

	public NegativeExpr(Expression e){
		this.expr = e;
		this.setType(e.getType());
	}

	public Expression getExpression() {
        return expr;
    }

    @Override
	public String toString() {
		return "-" + expr;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}