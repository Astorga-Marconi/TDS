/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ParentExpr.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class ParentExpr extends Expression {
	private Expression expr;
	
	public ParentExpr(Expression expr, Type type){
        this.expr = expr;
        this.type = type;
	}
	
    public Expression getExpression() {
        return expr;
    }

	@Override
	public String toString() {
		return "(" + expr + ")";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
} 