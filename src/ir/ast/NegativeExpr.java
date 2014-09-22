package ir.ast;

import ir.ASTVisitor;

public class NegativeExpr extends Expression {
	private BinOpType binOpType;
	private Expression exp;

	public NegativeExpr(BinOpType m1, Expression e1){
		this.binOpType = m1;
		this.exp = e1;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}