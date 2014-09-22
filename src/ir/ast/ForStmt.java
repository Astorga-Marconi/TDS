/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ForStmt.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class ForStmt extends Statement {

	private String id;
	private Expression initialValue;
	private Expression condition;
	private Block forBlock;
	
	public ForStmt(String id, Expression val , Expression cond, Block forBlock) {
		this.id = id;
		this.initialValue = val;
		this.condition = cond;
		this.forBlock = forBlock;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInitialValue(Expression exp) {
		this.initialValue = exp;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public void setForBlock(Block bl) {
		this.forBlock = bl;
	}

	public String getId() {
		return id;
	}

	public Expression getInitialValue() {
		return initialValue;
	}

	public Expression getCondition() {
		return condition;
	}

	public Block getForBlock() {
		return forBlock;
	}

	@Override
	public String toString() {
		String rtn = "for " + id + " = " + initialValue.toString() + ", " + condition.toString() + '\n' + forBlock.toString();	
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
