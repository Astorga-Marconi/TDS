/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: GlobalVarLocation.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

import ir.ASTVisitor;

public class GlobalVarLocation extends Location {

	public GlobalVarLocation() {
		
	}

	public GlobalVarLocation(String id) {
		this.id = id;
	}

	public GlobalVarLocation(String id, Type t) {
		this.id = id;
		this.type = t;
	}
	
	@Override
	public String toString() {
		return id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
