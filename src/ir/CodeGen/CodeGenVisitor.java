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
	private List<Expression> label;

	public CodeGenVisitor() {
		errors = new LinkedList<Error>();
		instrList = new LinkedList<InstrCode>();
		label = new LinkedList<Expression>();
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
		Expression initialValExpr = stmt.getInitialValue().accept(this);
		Expression forCondExpr = stmt.getCondition().accept(this);
		VarLocation loc = new VarLocation(stmt.getId());
		Expression forLabel = new VarLocation("forLabel" + Integer.toString(labelsIdGen++));
		Expression endForLabel = new VarLocation("endForLabel" + Integer.toString(labelsIdGen++));
		instrList.add(new InstrCode(Operator.EQ, initialValExpr, null, loc));
		instrList.add(new InstrCode(Operator.LABEL, null, null, forLabel));
		instrList.add(new InstrCode(Operator.CMP, loc, forCondExpr, null));
		instrList.add(new InstrCode(Operator.JGE, null, null, endForLabel));
		// Genero las instrucciones del bloque for.
		Expression forBlockInstrs = stmt.getForBlock().accept(this);
		instrList.add(new InstrCode(Operator.INC, loc, null, loc));
		instrList.add(new InstrCode(Operator.JMP, null, null, forLabel));
		instrList.add(new InstrCode(Operator.LABEL, null, null, endForLabel));
  		return null;
  	}
	
	public Expression visit(BreakStmt stmt) {
		instrList.add(new InstrCode(Operator.JMP, null, null,label.get(label.size()-2)));
		return null;
	}

	public Expression visit(ContinueStmt stmt) {
  		instrList.add(new InstrCode(Operator.JMP, null, null, label.get(label.size()-2)));
  		return null;
	} 

	public Expression visit(WhileStmt stmt) {
		Expression initialValStmt = stmt.getCondition().accept(this);
		Expression beginWhilelabel = new VarLocation("beginWhileLabel" + Integer.toString(labelsIdGen++));
		Expression endWhilelabel = new VarLocation("endWhileLabel" + Integer.toString(labelsIdGen++));
    	label.add(label.size(), beginWhilelabel);
    	label.add(label.size(), endWhilelabel);
		instrList.add(new InstrCode(Operator.LABEL, initialValStmt, null, beginWhilelabel));
    	Expression condition = stmt.getCondition().accept(this);	 
    	instrList.add(new InstrCode(Operator.CMP, condition, (new BoolLiteral("true")), null));
		instrList.add(new InstrCode(Operator.JNE, null, null, endWhilelabel));
		Expression block = stmt.getBlock().accept(this);
  	    instrList.add(new InstrCode(Operator.JMP, null, null, beginWhilelabel));	
		instrList.add(new InstrCode(Operator.LABEL, null, null, endWhilelabel));
   		label.remove(label.size()-1);
    	label.remove(label.size()-1);
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
    	Expression oper = expr.getExpression().accept(this);
  		VarLocation res = new VarLocation();
		instrList.add(new InstrCode(Operator.MINUS, oper, null, res));
    	return res;
	}
	
	public Expression visit (NotExpr expr)   {
    	Expression oper = expr.getExpression().accept(this);
  		Expression res = new VarLocation();
		instrList.add(new InstrCode(Operator.NOT, oper, null, res));
    	return res;
 	}

  	public Expression visit (ParentExpr expr) {
  		return expr.getExpression().accept(this);
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
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation();
    	Operator operator = null;
    	switch (expr.getOperator()) {
    		case GT:
    			operator = Operator.GT;
    		case LT:
    			operator = Operator.LT;
    		case LTEQ:
    			operator = Operator.LTEQ;
    		case GTEQ:
    			operator = Operator.GTEQ;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
  	}

  	public Expression visit (CondExpr expr)   {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation();  
    	Operator operator = null;
    	switch (expr.getOperator()) {
    		case ANDAND:
    			operator = Operator.ANDAND;
    		case OROR:
    			operator = Operator.OROR;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
  	}

  	public Expression visit (EqExpr expr)   {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation();  
    	Operator operator = null;
  		switch (expr.getOperator()) {
    		case EQEQ:
    			operator = Operator.EQEQ;
    		case NOTEQ:
    			operator = Operator.NOTEQ;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
  	}
  	
	//			visit literals
	
	public Expression visit(IntLiteral lit) {
		Expression res = new VarLocation("int" + Integer.toString(labelsIdGen++));
		//instrList.add(new InstrCode( , , , res));
    	return res;
	}
	
	public Expression visit(FloatLiteral lit) {
		Expression res = new VarLocation("float" + Integer.toString(labelsIdGen++));
		//instrList.add(new InstrCode( , , , res));
    	return res;
	}

	public Expression visit(BoolLiteral lit) {
		Expression res = new VarLocation("boolean" + Integer.toString(labelsIdGen++));	
		//instrList.add(new InstrCode( , , , res));
    	return res;
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
