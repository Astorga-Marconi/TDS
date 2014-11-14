/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: FunctionDescriptor.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package semanticAnalyzer;

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
