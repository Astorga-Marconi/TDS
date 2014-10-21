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

	private Expression initialValue;
	private Expression condition;
	private Block forBlock;
	private VarLocation idLocation;
	
	public ForStmt(VarLocation id, Expression val , Expression cond, Block forBlock) {
		this.idLocation = id;
		this.initialValue = val;
		this.condition = cond;
		this.forBlock = forBlock;
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

	public void setIdLocation(VarLocation id) {
		this.idLocation = id;
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

	public VarLocation getIdLocation() {
		return idLocation;
	}

	@Override
	public String toString() {
		String rtn = "for " + idLocation + " = " + initialValue.toString() + ", " + condition.toString() + '\n' + forBlock.toString();	
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
