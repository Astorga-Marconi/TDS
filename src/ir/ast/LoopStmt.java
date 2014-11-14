/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: LoopStmt.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public abstract class LoopStmt extends Statement {
	protected Block block;
    
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}