/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: TypeEvaluationVisitor.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class: Chequeos de tipos, en condiciones, expresiones aritmeticas, etc
 */
package ir.semcheck;

import java.util.LinkedList;
import java.util.List;
import ir.ASTVisitor;
import ir.ast.*;
import error.Error;


// type checker, concrete visitor 
public class TypeCheckVisitor implements ASTVisitor<Type> {
	
	private List<Error> errors;

	public TypeCheckVisitor() {
		errors = new LinkedList<Error>();
	}

	//			visit statements
	
	public Type visit(AssignStmt stmt) {
		Type typeLocation = stmt.getLocation().accept(this);
		Type typeExpr = stmt.getExpression().accept(this);
		if (typeLocation != typeExpr) {
			addError(stmt, "Error de tipos en asignacion.");
		}
		return null;
	}
	
  	public Type visit(ReturnStmt stmt) {
    	if (stmt.getExpression() != null) {
        	Type expr = stmt.getExpression().accept(this);
    	}
  		return null;
  	}

	public Type visit(IfStmt stmt)  {
    	Type condition = stmt.getCondition().accept(this);
    	if (condition != Type.TBOOLEAN) {
    		addError(stmt,"La condicion de la sentencia If deberia ser de tipo Booleana.!");
    	}
    	Type blockIf = stmt.getIfBlock().accept(this);
    	if (stmt.getElseBlock() != null) {
    		Type blockElse = stmt.getElseBlock().accept(this);	
    	}
  		return null;
  	}
	
	public Type visit(ForStmt stmt) {
	  	Type typeForStmtExpr = stmt.getInitialValue().accept(this);
    	Type typeForStmtCondition = stmt.getCondition().accept(this);
    	Type tBlock = stmt.getForBlock().accept(this);
    	if ((typeForStmtExpr != Type.TINT) || (typeForStmtCondition != Type.TINT)) {
    		addError(stmt,"Las expresiones del For deberian ser de tipo INT");
    	}
  		return null;
  	}
	
	public Type visit(BreakStmt stmt) {
		return null;
	}

	public Type visit(ContinueStmt stmt) {
		return null;
	} 

	public Type visit(WhileStmt stmt) {
		Type typeWhileStmtCondition = stmt.getCondition().accept(this);
		Type tBlock = stmt.getBlock().accept(this);
		if (typeWhileStmtCondition != Type.TBOOLEAN) {
			addError(stmt,"La condicion de la sentencia While deberia ser de tipo TBOOLEAN"); 
		}
		return null;
	}
	
	public Type visit(LoopStmt stmt) {
		return null;
	}
	
	public Type visit(Block stmt) {
		if (stmt != null) {
			//System.out.println(stmt.toString());
		}else {
			System.out.println("stmt es null");
		}
		for (Statement s: stmt.getStatements()) {
			//System.out.println("hola");
			s.accept(this);
		}
		return null;
	}
	
	public Type visit(MethodCallStmt stmt) {
		return null;
	}

	public Type visit(ExternInvkStmt stmt) {
		return null;
	}

	//			visit Expressions

	public Type visit(BinOpExpr expr) {
		Type typeExprL = expr.getLeftOperand().accept(this);
		Type typeExprR = expr.getRightOperand().accept(this);
		if(typeExprL == typeExprR) {
			return typeExprL;
		}else {
			// ERROR: No son del mismo tipo
		}
		return null;
	}
	
	public Type visit(MethodCallExpr expr) {
		return expr.getType();
	}

	public Type visit(ExternInvkExpr expr) {
		return expr.getType();
	}
	
	public Type visit(ExternInvkArgExpr expr) {
		return null;
	}
	
	public Type visit(ExternInvkArgStringLit expr) {
		return null;
	}

	public Type visit(NegativeExpr expr) {
		Type typenegativeExpr = expr.getExpression().accept(this);
    	if (typenegativeExpr == Type.TBOOLEAN){
    		addError(expr,"La negacion no es aplicable a expresiones de tipo Boolean.");
    	}
    	return typenegativeExpr;
	}
	
	public Type visit (NotExpr expr)   {
    	Type oper = expr.getExpression().accept(this);
    	if (oper != Type.TBOOLEAN){
    		addError(expr,"El operando de la negacion (!) deben ser de tipo boolean");  
    	}
    	expr.setType(Type.TBOOLEAN);
    	return oper;
 	}

  	public Type visit (ParentExpr expr){
    	Type typeParentExpr = expr.getExpression().accept(this);
    	expr.setType(typeParentExpr);
    	return typeParentExpr;
  	}
	
  	public Type visit (ArithExpr expr)   {
    	Type leftOperand = expr.getLeftOperand().accept(this);
    	Type rightOperand = expr.getRightOperand().accept(this);
    	BinOpType oper = expr.getOperator();
    	if  (leftOperand == Type.TBOOLEAN || rightOperand == Type.TBOOLEAN){
    		addError(expr,"Los operando de una expresion aritmetica no deberian ser Booleanos.!");
    	} 
    	switch(oper){
      		case LT: case LTEQ: case GT: case GTEQ: case EQEQ: case NOTEQ:
      		case ANDAND: case OROR: case DIV: case MOD:
        	if(!(leftOperand == rightOperand)){
				addError(expr,"Error de tipos");  
         	}else {
            	expr.setType(leftOperand);
            	return leftOperand; 
         	}
      		case PLUS: case MINUS: case MULT:
        	if(!(leftOperand == rightOperand)){
            	addError(expr,"Error de tipos");  
         	}else {
            	expr.setType(leftOperand);
            	return leftOperand; 
         	}
    	}
    	return null;
 	 }

  	public Type visit (RelExpr expr) {
    	Type leftOperand = expr.getLeftOperand().accept(this);
    	Type rightOperand = expr.getRightOperand().accept(this);
    	BinOpType oper = expr.getOperator();
    	if  (leftOperand == Type.TBOOLEAN || rightOperand == Type.TBOOLEAN){
    		addError(expr,"Operandos de una expresion relacional no deberian ser de tipo Booleanos.!");
    	} 
    	switch(oper){
      		case DIV: case MINUS: case MULT: case PLUS: case EQEQ: case NOTEQ:
      		case ANDAND: case OROR: case MOD:
				addError(expr,"Error");  
      		default: expr.setType(Type.TBOOLEAN); 
      		return Type.TBOOLEAN;
    	}
  	}

  	public Type visit (CondExpr expr)   {
    	Type leftOperand = expr.getLeftOperand().accept(this);
    	Type rightOperand = expr.getRightOperand().accept(this);
    	BinOpType oper = expr.getOperator();
    	if ( (leftOperand != Type.TBOOLEAN)){
 			addError(expr,"Operandos de una expresion booleana deben ser del tipo Booleanos.!");
    	} 
    	if ( !(leftOperand == rightOperand)){
	    	addError(expr,"Operandos de la expresion condicional deberian ser del mismo tipo.!");  
    	}
    	if (!(oper == BinOpType.ANDAND || oper == BinOpType.OROR)){
	    	addError(expr,"Error.!");  
    	}
    	expr.setType(Type.TBOOLEAN);    
    	return Type.TBOOLEAN;
  	}

  	public Type visit (EqExpr expr)   {
    	Type leftOperand = expr.getLeftOperand().accept(this);
    	Type rightOperand = expr.getRightOperand().accept(this);
    	BinOpType oper = expr.getOperator();
    	if (!(oper == BinOpType.EQEQ || oper == BinOpType.NOTEQ)){
 		  addError(expr,"Error");
    	}
    	if (! (leftOperand == rightOperand)){
	    	addError(expr,"Operandos de una expresion de Equivalencia deberian ser del mismo tipo");  
    	} 
    	expr.setType(Type.TBOOLEAN);
    	return Type.TBOOLEAN;
  	}
  	
	//			visit literals
	
	public Type visit(IntLiteral lit) {
		return lit.getType();
	}
	
	public Type visit(FloatLiteral lit) {
		return lit.getType();
	}

	public Type visit(BoolLiteral lit) {
		return lit.getType();
	}

	//			visit locations	
	
	public Type visit(VarLocation loc) {
		return loc.getType();		
	}

	public Type visit(GlobalVarLocation loc) {
		return loc.getType();		
	}

	public Type visit(ArrayLocation loc) {
		if (loc.getExpression().getType() != Type.TINT) {
			addError(loc,"El indice del arreglo no es de tipo entero.");  
		}
		return loc.getType();
	}

	public Type visit(GlobalArrayLocation loc) {
		if (loc.getExpression().getType() != Type.TINT) {
			addError(loc,"El indice del arreglo no es de tipo entero.");  
		}
		return loc.getType();
	}

	public Type visit(SemicolonStmt stmt) {
		return null;
	}

	private void addError(AST a, String desc) {
		errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public void addError2(String desc) {	// Metodo momentáneo para realizar la prueba.
		errors.add(new Error(0, 0, desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
