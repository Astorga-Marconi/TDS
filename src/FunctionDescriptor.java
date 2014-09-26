
import ir.ast.*;
import java.util.*;

public class FunctionDescriptor extends Descriptor {

	private LinkedList<VarDescriptor> parameters;

	public FunctionDescriptor(Type newType, String newName){
		super(newType, newName);
	}

	public FunctionDescriptor(Type newType, String newName, LinkedList<VarDescriptor> params){
		super(newType, newName);
		parameters = params;
	}

	public LinkedList<VarDescriptor> getParameters() {
		return parameters;
	}

	public void setParameters(LinkedList<VarDescriptor> params) {
		parameters = params;
	}

}
