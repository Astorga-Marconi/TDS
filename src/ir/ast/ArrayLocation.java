/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ArrayLocation.java
 * Authors: Astorga Dario - Marconi Pablo
 */
package ir.ast;
import ir.ASTVisitor;

public class ArrayLocation extends Location {

	private int blockId;
	private int sizeArray;
	private Expression expr;

	public ArrayLocation() {
		
	}

	public ArrayLocation(String id) {
		this.id = id;
		this.blockId = -1;
	}

	public ArrayLocation(String id, Type t, int size, Expression expr) {
		this.id = id;
		this.type = t;
		this.blockId = -1;
		this.sizeArray = size;
		this.expr = expr;
		maxoffset = maxoffset - (4 * size);
		this.offset = maxoffset + 4;
	}

	public ArrayLocation(ArrayLocation l, Expression e) {
		// Constructor que copia un ArrayLocation menos la expression, y le asigna una nueva.
		this.id = l.getId();
		this.type = l.getType();
		this.blockId = -1;
		this.sizeArray = l.getSizeArray();
		this.expr = e;
		this.offset = l.getOffset();
	}
	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public int getSizeArray() {
		return sizeArray;
	}

	public void setSizeArray(int size) {
		this.sizeArray = size;
	}

	public Expression getExpression() {
		return expr;
	}

	public void setExpression(Expression expr) {
		this.expr = expr;
	}
	
	@Override
	public String toString() {
		return id + "[" + expr + "]";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
