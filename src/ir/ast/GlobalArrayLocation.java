/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: GlobalArrayLocation.java
 * Authors: Astorga Dario - Marconi Pablo
 */
package ir.ast;

import ir.ASTVisitor;

public class GlobalArrayLocation extends Location {

	private int sizeArray;
	private Expression expr;

	public GlobalArrayLocation() {
		
	}

	public GlobalArrayLocation(String id) {
		this.id = id;
	}

	public GlobalArrayLocation(String id, Type t, int size, Expression expr) {
		this.id = id;
		this.type = t;
		this.sizeArray = size;
		this.expr = expr;
	}

	public GlobalArrayLocation(GlobalArrayLocation l, Expression e) {
		// Constructor que copia un GlobalArrayLocation menos la expression, y le asigna una nueva.
		this.id = l.getId();
		this.type = l.getType();
		this.sizeArray = l.getSizeArray();
		this.expr = e;
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
