/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: IfStmt.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class IfStmt extends Statement {
	private Expression condition;
	private Block ifBlock;
	private Block elseBlock;
	
	public IfStmt(Expression condi, Block ifBlo) {
		this.condition = condi;
		this.ifBlock = ifBlo;
		this.elseBlock = null;
	}
	
	public IfStmt(Expression condi, Block ifBlo, Block elseBlo) {
		this.condition = condi;
		this.ifBlock = ifBlo;
		this.elseBlock = elseBlo;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getIfBlock() {
		return ifBlock;
	}

	public void setIfBlock(Block ifBlock) {
		this.ifBlock = ifBlock;
	}

	public Block getElseBlock() {
		return elseBlock;
	}

	public void setElseBlock(Block elseBlock) {
		this.elseBlock = elseBlock;
	}
	
	@Override
	public String toString() {
		String rtn = "if " + condition + '\n' + ifBlock.toString();
		
		if (elseBlock != null) {
			rtn += "else \n" + elseBlock;
		}
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
} 