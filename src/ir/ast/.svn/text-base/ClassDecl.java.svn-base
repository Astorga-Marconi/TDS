package decaf.ir.ast;

import java.util.ArrayList;
import java.util.List;
import decaf.ir.ASTVisitor;

public class ClassDecl extends AST {
	private List<FieldDecl> fieldDeclarations;
	private List<MethodDecl> methodDeclarations;
	
	public ClassDecl() {
		fieldDeclarations = new ArrayList<FieldDecl>();
		methodDeclarations = new ArrayList<MethodDecl>();
	}
	
	public ClassDecl(List<FieldDecl> f, List<MethodDecl> m) {
		fieldDeclarations = f;
		methodDeclarations = m;
	}
	
	public void addFieldDecl(FieldDecl f) {
		fieldDeclarations.add(f);
	}
	
	public void addMethodDecl(MethodDecl m) {
		methodDeclarations.add(m);
	}
	
	public List<FieldDecl> getFieldDeclarations() {
		return fieldDeclarations;
	}
	
	public List<MethodDecl> getMethodDeclarations() {
		return methodDeclarations;
	}
	
	@Override
	public String toString() {
		String rtn = "CLASS \n";
		
		for (FieldDecl f: fieldDeclarations) {
			rtn += f.toString() + '\n';
		}
		
		for (MethodDecl m: methodDeclarations) {
			rtn += m.toString() + '\n';
		}
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
