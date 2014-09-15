package decaf.ir.ast;

import java.util.ArrayList;
import java.util.List;
import decaf.ir.ASTVisitor;

public class Block extends Statement {
	private List<Statement> statements;
	private List<VarDecl> variableDeclarations;
	private int blockId;
	
	public Block(int bId) {
		statements = new ArrayList<Statement>();
		variableDeclarations = new ArrayList<VarDecl>();
		blockId = bId;
	}
	
	public Block(int bId, List<Statement> s, List<VarDecl> f) {
		blockId = bId;
		statements = s;
		variableDeclarations = f;
	}
	
	public void addStatement(Statement s) {
		this.statements.add(s);
	}
	
	public void addFieldDeclaration(VarDecl f) {
		this.variableDeclarations.add(f);
	}
	
	public List<Statement> getStatements() {
		return this.statements;
	} 
	
	public List<VarDecl> getVarDeclarations() {
		return this.variableDeclarations;
	}
	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
		String rtn = "";
		
		for (VarDecl f: variableDeclarations) {
			rtn += f.toString() + '\n';
		}
		
		for (Statement s: statements) {
			rtn += s.toString() + '\n';
		}
		
		if (rtn.length() > 0) return rtn.substring(0, rtn.length() - 1); // remove last new line char
		
		return rtn; 
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
