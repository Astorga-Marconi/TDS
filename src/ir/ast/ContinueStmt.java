/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ContinueStmt.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class ContinueStmt extends Statement {

	public ContinueStmt() {
		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
