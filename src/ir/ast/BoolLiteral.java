/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: BoolLiteral.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;
import ir.ASTVisitor;

public class BoolLiteral extends Literal {
	private String rawValue;
	private Boolean value;
	
	/*
	 * Constructor for bool literal that takes a string as an input
	 * @param: String boolean
	 */
	public BoolLiteral(String val){
		rawValue = val; // Will convert to boolean value in semantic check
		value = Boolean.valueOf(val);
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return rawValue;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
} 