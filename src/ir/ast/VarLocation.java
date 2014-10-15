/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: VarLocation.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class VarLocation extends Location {
	private int blockId;

	public VarLocation() {
		
	}

	public VarLocation(String id) {
		this.id = id;
		this.blockId = -1;
		this.offset = maxoffset;
		maxoffset = maxoffset - 4;
	}

	public VarLocation(String id, Type t) {
		this.id = id;
		this.type = t;
		this.blockId = -1;
		this.offset = maxoffset;
		maxoffset = maxoffset - 4;
	}
	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
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
