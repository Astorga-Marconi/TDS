package ir.ast;

import java.util.ArrayList;
import java.util.List;
import ir.ASTVisitor;

public class Block extends Statement {
	private List<Statement> statements;
	private int blockId;
	
	public Block(int bId) {
		statements = new ArrayList<Statement>();
		blockId = bId;
	}
	
	public Block(int bId, List<Statement> s) {
		blockId = bId;
		statements = s;
	}
	
	public void addStatement(Statement s) {
		this.statements.add(s);
	}
		
	public List<Statement> getStatements() {
		return this.statements;
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
