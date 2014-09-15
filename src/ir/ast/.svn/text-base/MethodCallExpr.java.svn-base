package decaf.ir.ast;

import java.util.ArrayList;
import java.util.List;
import decaf.ir.ASTVisitor;

public class MethodCallExpr extends CallExpr {
	private String name;
	private List<Expression> args;
	
	public MethodCallExpr(String name) {
		this.name = name;
		this.args = new ArrayList<Expression>();
	}

	public MethodCallExpr(String name, List<Expression> a) {
		this.name = name;
		this.args = a;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Expression> getArguments() {
		return args;
	}

	public void setArgs(List<Expression> args) {
		this.args = args;
	}
	
	@Override
	public String toString() {
		String rtn = name + "(";
		for (Expression arg: args) {
			rtn += arg + ", ";
		}
		
		if (!args.isEmpty()){
			rtn = rtn.substring(0, rtn.length()-2);
		}
		
		rtn += ")";
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
