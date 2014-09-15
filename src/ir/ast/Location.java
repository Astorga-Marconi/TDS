package ir.ast;

public abstract class Location extends Expression {
	protected String id;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
