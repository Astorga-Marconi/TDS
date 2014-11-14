/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Literal.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.ast;

public abstract class Literal extends Expression {
	/*
	 * @return: returns Type of Literal instance
	 */
	public abstract Type getType();
}
