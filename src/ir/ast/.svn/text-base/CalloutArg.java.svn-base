package decaf.ir.ast;

import decaf.ir.ASTVisitor;

public class CalloutArg extends AST {
	private String stringArg = null;
	private Expression exprArg = null;
	
	public CalloutArg(String arg) {
		this.stringArg = arg;
	}
	
	public CalloutArg(Expression expr) {
		this.exprArg = expr;
	}
	
	@Override
	public String toString() {
		if (stringArg == null) {
			return exprArg.toString();
		}
		else {
			return stringArg;
		}
	}
	
	public Expression getExpression() {
		return exprArg;
	}
	
	public void setExpression(Expression expr) {
		exprArg = expr;
	}
	
	public String getStringArg() {
		return stringArg;
	}
	
	public void setStringArg(String s) {
		stringArg = s;
	}
	
	public boolean isString() {
		if (stringArg != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
