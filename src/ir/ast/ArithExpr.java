/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ArithExpr.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class ArithExpr extends BinOpExpr {
	public ArithExpr(){
	    super();
	}   

	public ArithExpr(Expression l, BinOpType op, Expression r){
		super(l, r);
        super.operator = op;
	}
	
	public ArithExpr(BinOpType op){
        super.operator = op;
	}
	
	@Override
	public Type getType() {
		return this.getLeftOperand().getType();
	}

	@Override
	public String toString() {
		return lOperand + " " + operator.toString() + " " + rOperand;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}