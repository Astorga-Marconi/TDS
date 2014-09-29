/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: CodeGenVisitor.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class: Generador de codigo de tres direcciones
 */
package ir.CodeGen;

import java.util.LinkedList;
import java.util.List;
import ir.ASTVisitor;
import ir.ast.*;
import error.Error;

public class CodeGenVisitor implements ASTVisitor<Expression> {
	
	private List<Error> errors;
	private List<InstrCode> instrList ;

	public CodeGenVisitor() {
		errors = new LinkedList<Error>();
		instrList = new LinkedList<InstrCode>();
	}

	//			visit statements
	
	public Expression visit(AssignStmt stmt) {
		return null;
	}
	
  	public Expression visit(ReturnStmt stmt) {
  		return null;
  	}

	public Expression visit(IfStmt stmt)  {
  		return null;
  	}
	
	public Expression visit(ForStmt stmt) {
  		return null;
  	}
	
	public Expression visit(BreakStmt stmt) {
		return null;
	}

	public Expression visit(ContinueStmt stmt) {
		return null;
	} 

	public Expression visit(WhileStmt stmt) {
		return null;
	}
	
	public Expression visit(LoopStmt stmt) {
		return null;
	}
	
	public Expression visit(Block stmt) {
		return null;
	}
	
	public Expression visit(MethodCallStmt stmt) {
		return null;
	}

	public Expression visit(ExternInvkStmt stmt) {
		return null;
	}

	//			visit Expressions

	public Expression visit(BinOpExpr expr) {
		return null;
	}
	
	public Expression visit(MethodCallExpr expr) {
		return null;
	}
	
	public Expression visit(ExternInvkArgExpr expr) {
		return null;
	}
	
	public Expression visit(ExternInvkArgStringLit expr) {
		return null;
	}

	public Expression visit(NegativeExpr expr) {
    	return null;
	}
	
	public Expression visit (NotExpr expr)   {
    	return null;
 	}

  	public Expression visit (ParentExpr expr) {
    	return null;
  	}
	
  	public Expression visit (ArithExpr expr) {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation();
    	Operator operator = null;
    	switch (expr.getOperator()) {
    		case PLUS:
    			operator = Operator.PLUS;
    		case MINUS:
    			operator = Operator.MINUS;
    		case MULT:
    			operator = Operator.MULT;
    		case DIV:
    			operator = Operator.DIV;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
 	}

  	public Expression visit (RelExpr expr) {
      	return null;
  	}

  	public Expression visit (CondExpr expr)   {
    	return null;
  	}

  	public Expression visit (EqExpr expr)   {
    	return null;
  	}
  	
	//			visit literals
	
	public Expression visit(IntLiteral lit) {
		return null;
	}
	
	public Expression visit(FloatLiteral lit) {
		return null;
	}

	public Expression visit(BoolLiteral lit) {
		return null;
	}

	//			visit locations	
	
	public Expression visit(VarLocation loc) {
		return null;		
	}

	public Expression visit(SemicolonStmt stmt) {
		return null;
	}

	private void addError(AST a, String desc) {
		errors.add(new Error(0, 0, desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
