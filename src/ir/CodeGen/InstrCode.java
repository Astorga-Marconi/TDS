/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: InstCode.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class: Clase instruccion de Codigo de tres direcciones. 
 */
package ir.CodeGen;

import ir.ast.*;

public class InstrCode {

	private Operator operator;
	private Expression leftOperand;
	private Expression rightOperand;
	private Expression result;

	public InstrCode () {
	}

	public InstrCode (Operator newOperator, Expression newLeftOperand, Expression newRightOperand, Expression res) {
		operator = newOperator;
		leftOperand = newLeftOperand;
		rightOperand = newRightOperand;
		result = res;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator oper) {
		operator = oper;
	}

	public Expression getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(Expression op) {
		leftOperand = op;
	}

	public Expression getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(Expression op) {
		rightOperand = op;
	}

	public Expression getResult() {
		return result;
	}

	public void setResult(Expression res) {
		result = res;
	}

	public String toString() {
		String res = operator + ", " + leftOperand + ", " + rightOperand + ", " + result;
		return res;
	}


}
