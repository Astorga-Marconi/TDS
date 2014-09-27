/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: RelExpr.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class RelExpr extends BinOpExpr {
    public RelExpr() {
        super();
        type = Type.TBOOLEAN;
    }

	public RelExpr(Expression lExpr, BinOpType op, Expression rExp){
	    super(lExpr, rExp);
        operator = op;
        type = Type.TBOOLEAN;
	}
	
	public RelExpr(BinOpType op){
        operator = op;
        type = Type.TBOOLEAN;
	}
	
	@Override
	public String toString() {
		return this.lOperand + " " + this.operator + " " + this.rOperand;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}