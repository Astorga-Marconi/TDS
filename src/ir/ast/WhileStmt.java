/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: WhileStmt.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class WhileStmt extends LoopStmt {
	private Expression condition;
	
	public WhileStmt(Expression cond, Block block) {
		this.condition = cond;
		this.block = block;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}
	
	@Override
	public String toString() {
		String rtn = "while " + condition + '\n' + block.toString();
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}