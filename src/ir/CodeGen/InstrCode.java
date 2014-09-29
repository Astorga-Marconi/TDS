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

	private BinOpType operator;
	private Expression leftOperand;
	private Expression rightOperand;
	private Expression result;

	public InstrCode () {
	}

	public InstrCode (BinOpType newOperator, Expression newLeftOperand, Expression newRightOperand, Expression res) {
		operator = newOperator;
		leftOperand = newLeftOperand;
		rightOperand = newRightOperand;
		result = res;
	}

	public BinOpType getOperator() {
		return operator;
	}

	public void setOperator(BinOpType oper) {
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


}
