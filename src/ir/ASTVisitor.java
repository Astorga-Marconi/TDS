/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: ASTVisitor.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package ir;

import ir.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {

// visit statements
	T visit(AssignStmt stmt);
	T visit(ReturnStmt stmt);
	T visit(IfStmt stmt);
	T visit(ForStmt stmt);
	T visit(BreakStmt stmt);
	T visit(ContinueStmt stmt);
	T visit(SemicolonStmt stmt);
	T visit(WhileStmt stmt);
	T visit(LoopStmt stmt);
	T visit(Block stmt);
	T visit(MethodCallStmt stmt);
	T visit(ExternInvkStmt stmt);
	
// visit expressions
	T visit(BinOpExpr expr);
	T visit(MethodCallExpr expr);
	T visit(ExternInvkExpr expr);
	T visit(ExternInvkArgExpr expr);
	T visit(ExternInvkArgStringLit expr);
	T visit(NegativeExpr expr);
	T visit(NotExpr expr);
	T visit(ParentExpr expr);
	T visit(ArithExpr expr);
	T visit(RelExpr expr);
	T visit(CondExpr expr);	
	T visit(EqExpr expr);
	
// visit literals	
	T visit(IntLiteral lit);
	T visit(FloatLiteral lit);
	T visit(BoolLiteral lit);

// visit locations	
	T visit(VarLocation loc);
	T visit(ArrayLocation loc);
	T visit(GlobalVarLocation loc);
	T visit(GlobalArrayLocation loc);
}
