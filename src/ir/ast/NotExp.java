/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: NotExp.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class NotExp extends Expression {
	private BinOpType binOpType;
	private Expression exp;

	public NotExp(BinOpType b, Expression e) {
		this.binOpType = b;
		this.exp = e;
	}

	public BinOpType  getBinOpType() {
		return binOpType;
	}

	public Expression  getExpression() {
		return exp; 
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}