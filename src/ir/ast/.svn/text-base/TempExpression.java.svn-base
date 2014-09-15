package decaf.ir.ast;

import decaf.ir.ASTVisitor;

public class TempExpression extends AST {	
	private BinOpType operator; 
	private Expression rOperand;
	private TempExpression rightDeepChild;
	private boolean makeLeftDeep;

	public TempExpression(BinOpType op, Expression rOper) {
		operator = op;
		rOperand = rOper;
		setMakeLeftDeep(false);
	}

	public BinOpType getOperator() {
		return operator;
	}

	public void setOperator(BinOpType operator) {
		this.operator = operator;
	}
	
	public Expression getRightOperand() {
		return rOperand;
	}

	public void setRightOperand(Expression rOperand) {
		this.rOperand = rOperand;
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		// nothing
		return null;
	}

	public void setMakeLeftDeep(boolean makeLeftDeep) {
		this.makeLeftDeep = makeLeftDeep;
	}

	public boolean isMakeLeftDeep() {
		return makeLeftDeep;
	}

	public void setRightDeepChild(TempExpression rightDeepChild) {
		this.rightDeepChild = rightDeepChild;
		this.makeLeftDeep = true;
	}

	public TempExpression getRightDeepChild() {
		return rightDeepChild;
	}
}
