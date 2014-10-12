/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Descriptor.java
 * To Create: javac Descriptor.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package semanticAnalyzer;

import ir.ast.*;

public abstract class Descriptor {

		public String name;
		public Type type;

		public Descriptor(Type newType, String newName){
			name = newName;
			type = newType;
		}
		
		public void setName(String n){
			name = n;
		}
		
		public void setType(Type t){
			type = t;
		}
		
		public String getName(){
			return name;
		}
		
		public Type getType(){
			return type;
		}
}
