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
	private Integer labelsIdGen = 0;

	public CodeGenVisitor() {
		errors = new LinkedList<Error>();
		instrList = new LinkedList<InstrCode>();
	}

	//			visit statements
	
	public Expression visit(AssignStmt stmt) {
		Expression loc = stmt.getLocation();
		Expression expr = stmt.getExpression().accept(this);
		Expression resExpr = new VarLocation();
		switch (stmt.getOperator()) {
    		case EQ:
    			instrList.add(new InstrCode(Operator.EQ, expr, null, loc));
    		case PLUSEQ:
    			instrList.add(new InstrCode(Operator.PLUS, loc, expr, resExpr));
    			instrList.add(new InstrCode(Operator.EQ, resExpr, null, loc));
    		case MINUSEQ:
    			instrList.add(new InstrCode(Operator.MINUS, loc, expr, resExpr));
    			instrList.add(new InstrCode(Operator.EQ, resExpr, null, loc));
    	}
		return null;
	}
	
  	public Expression visit(ReturnStmt stmt) {
  		Expression retExpr = null;
  		if (stmt.getExpression() != null) {
  			retExpr = stmt.getExpression().accept(this);
  		}
  		instrList.add(new InstrCode(Operator.RET, null, null, retExpr));
  		return null;
  	}

	public Expression visit(IfStmt stmt)  {
		Expression ifCond = stmt.getCondition().accept(this);
		Expression endIfLabel = new VarLocation("endIfLabel" + Integer.toString(labelsIdGen++));
		instrList.add(new InstrCode(Operator.CMP, ifCond, (new BoolLiteral("true")), null));
		if (stmt.getElseBlock() == null) {	// Si no tengo un else
			instrList.add(new InstrCode(Operator.JNE, null, null, endIfLabel));
			// Genero las instrucciones del bloque if
			Expression ifInstrs = stmt.getIfBlock().accept(this);
			instrList.add(new InstrCode(Operator.LABEL, null, null, endIfLabel));
		} else {	// Si tengo un else
			Expression elseLabel = new VarLocation("elseLabel" + Integer.toString(labelsIdGen++));
			instrList.add(new InstrCode(Operator.JNE, null, null, elseLabel));
			// Genero las instrucciones del bloque if
			Expression ifInstrs = stmt.getIfBlock().accept(this);
			instrList.add(new InstrCode(Operator.JMP, null, null, endIfLabel));
			instrList.add(new InstrCode(Operator.LABEL, null, null, elseLabel));
			// Genero las instrucciones del bloque else.
			Expression elseInstrs = stmt.getElseBlock().accept(this);
			instrList.add(new InstrCode(Operator.LABEL, null, null, endIfLabel));
		}
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
