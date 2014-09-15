package decaf.ir.ast;

import decaf.ir.ASTVisitor;

public class InvokeStmt extends Statement {
	private CallExpr methodCall;
	
	public InvokeStmt(CallExpr e) {
		this.methodCall = e;
	}

	public CallExpr getMethodCall() {
		return methodCall;
	}

	public void setMethodCall(CallExpr methodCall) {
		this.methodCall = methodCall;
	}
	
	@Override
	public String toString() {
		return methodCall.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
