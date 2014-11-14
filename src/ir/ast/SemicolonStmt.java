/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: SemicolonStmt.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class SemicolonStmt extends Statement {

	public SemicolonStmt() {
		
	}

	@Override
	public String toString() {
		return ";\n"; 
	} 
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
