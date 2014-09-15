package decaf.ir.ast;

import java.util.ArrayList;
import java.util.List;

import decaf.ir.ASTVisitor;

public class MethodDecl extends MemberDecl {
	private Type returnType;
	private String id;
	private List<Parameter> parameters;
	private Block body;
	
	public MethodDecl() {
	}
	
	public MethodDecl(Type t, String i) {
		returnType = t;
		id = i;
		parameters = new ArrayList<Parameter>();
	}
	
	public MethodDecl(Type t, String i, Parameter p) {
		returnType = t;
		id = i;
		parameters = new ArrayList<Parameter>();
		addParameter(p);
	}
	
	public MethodDecl(Type t, String i, List<Parameter> p) {
		returnType = t;
		id = i;
		if (p != null)
			parameters = p;
		else
			parameters = new ArrayList<Parameter>();
	}
	
	public void setReturnType(Type t) {
		returnType = t;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public void setId(String i) {
		id = i;
	}
	
	public String getId() {
		return id;
	}
	
	public void addParameter(Parameter param) {
		parameters.add(param);
	}
	
	public void setParameters(List<Parameter> p) {
		parameters = p;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public Block getBlock() {
		return body;
	}

	public void setBlock(Block block) {
		this.body = block;
	}

	@Override
	public String toString() {
		String rtn = returnType + " " + id + "(" + parameters + ")\n";
		rtn += body.toString();
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
