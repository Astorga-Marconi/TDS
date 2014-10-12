package semanticAnalyzer;

import ir.ast.*;

public class ArrayVarDescriptor extends Descriptor {

	private Integer size;

	public ArrayVarDescriptor (Type newType, String newName, String newSize){
		super(newType, newName);
		size = Integer.parseInt(newSize);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer newSize) {
		size = newSize;
	}
		
}
