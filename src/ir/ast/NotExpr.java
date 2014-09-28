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

public class NotExpr extends Expression {
	private BinOpType operator;
	private Expression expr;
	
	public NotExpr(BinOpType oper, Expression expr){
		this.operator = oper;
        this.expr = expr;
        type = Type.TBOOLEAN;
	}
	
	public BinOpType getOperator() {
		return this.operator;
	}

	public void setOperator(BinOpType oper) {
		this.operator = oper;
	}

    public Expression getExpression() {
        return expr;
    }

    public void setExpression(Expression e) {
        expr = e;
    }
    
	@Override
	public String toString() {
		return "!" + expr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}