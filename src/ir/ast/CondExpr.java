/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: CondExpr.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class CondExpr extends BinOpExpr {
    public CondExpr(){
        super();
        type = Type.TBOOLEAN;
    }

   	public CondExpr(Expression l, BinOpType op, Expression r){
	    super(l, r);   	
        super.operator = op;
        type = Type.TBOOLEAN;
	}	
	
	public CondExpr(BinOpType op) {
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