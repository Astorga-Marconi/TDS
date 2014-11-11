package ir.ast;

public abstract class Location extends Expression {
	protected String id;
	
	static int maxoffset = -4;	// El maximo offset corriente creado.
	protected int offset;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setOffset(int o) {
		this.offset = o;
	}
	
	public int getOffset() {
		return this.offset;
	}

	public void setMaxOffset(int o) {
		maxoffset = o;
	}
}
