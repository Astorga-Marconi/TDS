/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: TypeEvaluationVisitor.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir.semcheck;

import java.util.ArrayList;
import java.util.List;
import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class TypeEvaluationVisitor implements ASTVisitor<Type> {
	
	private List<Error> errors;

	//			visit statements
	@Override
	public Type visit(AssignStmt stmt) {
		Type typeLocation = stmt.getLocation().getType();
		Type typeExpr = stmt.getExpression().accept(this);

		if (typeLocation != typeExpr) {
			// ERROR: TYPE
		}else { 
			if (typeLocation == Type.TBOOLEAN){
				// ERROR: No es permitida la asignacio entre booleanos      
			} else {
				return typeExpr;
			}
		}
		return null;
	}

	@Override
	public Type visit(ReturnStmt stmt) {
		Type typeReturnExpr = stmt.getExpression().accept(this);
		// Podriamos ver de hacerlo en una clace parte 
		return null;
	}

	@Override
	public Type visit(IfStmt stmt) {
		Type typeIfStmt = stmt.getCondition().accept(this);
		if (typeIfStmt == Type.TBOOLEAN) {
			return Type.TBOOLEAN;
		}else { 
			//ERROR: Tipo
		}
		return null;
	}

	@Override
	public Type visit(ForStmt stmt) {
		// Hay q fijarse que la condicion sea boolean
		// Y que initialValue sea INT
		return null;
	}

	@Override
	public Type visit(BreakStmt stmt) {
		return null;
	}

	@Override
	public Type visit(ContinueStmt stmt) {
		return null;
	} 

	@Override
	public Type visit(WhileStmt stmt) {
		// Hay q fijarse que la condicion sea boolean
		// Hacer el accept del block
		Type = stmt.
		Type typeWhileStmtCondition = stmt.getCondition().accept(this);
		if (typeWhileStmtCondition == Type.TBOOLEAN) {
			return Type.TBOOLEAN;
		}else {
			// ERROR: 
		}
		return null;
	}

	@Override
	public Type visit(LoopStmt stmt) {
	}

	@Override
	public Type visit(MethodCallStmt stmt) {
	}

	@Override
	public Type visit(ExternInvkStmt stmt) {	
	}

	//			visit Expressions

	@Override
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

	@Override
	public Type visit(MethodCallExpr expr) {
	}

	@Override
	public Type visit(ExternInvkArgExpr expr) {
	}

	@Override
	public Type visit(ExternInvkArgStringLit expr) {
	}

	@Override
	public Type visit(NegativeExpr expr) {
	}

	//			visit literals
	@Override
	public Type visit(IntLiteral lit) {
		return lit.getType;
	}

	@Override
	public Type visit(FloatLiteral lit) {
		return lit.getType;
	}

	@Override
	public Type visit(BoolLiteral lit) {
		return lit.getType;
	}

	//			visit locations	
	@Override
	public Type visit(VarLocation loc) {
		return loc.;
	}

	private void addError(AST a, String desc) {
		errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
