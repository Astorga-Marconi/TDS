/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: EqExpr.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class EqExpr extends BinOpExpr {
    public EqExpr(){
        super();
    }

	public EqExpr(Expression l, BinOpType op, Expression r){
	    super(l, r);   	
        super.operator = op;
        type = Type.TBOOLEAN;
	}
	
	public EqExpr(BinOpType op){
        super.operator = op;
        type = Type.TBOOLEAN;        
	}
	
	@Override
	public String toString() {
		return lOperand + " " + operator + " " + rOperand;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}