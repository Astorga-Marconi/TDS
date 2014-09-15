package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class TypeEvaluationVisitor implements ASTVisitor<Type> {
	
	private List<Error> errors;

	@Override
	public Type visit(AssignStmt stmt) {

	}

	@Override
	public Type visit(BinOpExpr expr) {
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
