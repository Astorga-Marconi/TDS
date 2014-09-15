package decaf.ir.ast;

import java.util.ArrayList;
import java.util.List;
import decaf.ir.ASTVisitor;

public class FieldDecl extends MemberDecl {
	private List<Field> fields;
	private Type type;
	
	public FieldDecl() {
		fields = new ArrayList<Field>();
	}
	
	public FieldDecl(Field f, Type t) {
		fields = new ArrayList<Field>();
		type = t;
		addField(f);
		f.setType(t);
	}
	
	public FieldDecl(List<Field> f, Type t) {
		if (f != null) {
			fields = f;
			for (Field field: f) {
				if (field.getArrayLength() != null) {
					// Array
					if (t.equals(Type.INT)) {
						field.setType(Type.INTARRAY);
					} else if (t.equals(Type.BOOLEAN)) {
						field.setType(Type.BOOLEANARRAY);
					}
				} else {
					field.setType(t);
				}
			}
		}
		else {
			fields = new ArrayList<Field>();
		}
		
		type = t;
	}
	
	public void addField(Field f) {
		fields.add(f);
		if (f.getArrayLength() != null) {
			// Array
			if (type.equals(Type.INT)) {
				f.setType(Type.INTARRAY);
			} else if (type.equals(Type.BOOLEAN)) {
				f.setType(Type.BOOLEANARRAY);
			}
		} else {
			f.setType(type);
		}
	}
	
	public void setFields(List<Field> f) {
		fields = f;
		for (Field field: f) {
			if (field.getArrayLength() != null) {
				// Array
				if (type.equals(Type.INT)) {
					field.setType(Type.INTARRAY);
				} else if (type.equals(Type.BOOLEAN)) {
					field.setType(Type.BOOLEANARRAY);
				}
			} else {
				field.setType(type);
			}
		}
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
		for (Field field: fields) {
			field.setType(type);
		}
	}
	
	@Override
	public String toString() {
		String rtn = type.toString() + " ";
		for (Field f: fields) {
			rtn += f.toString() + ", ";
		}
		
		if (fields.size() > 0) {
			rtn = rtn.substring(0, rtn.length() - 2);
		}
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
