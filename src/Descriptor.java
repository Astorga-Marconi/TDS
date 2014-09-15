/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Descriptor.java
 * To Create: javac Descriptor.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
public class Descriptor {

		private String name;
		private String type;

		public Descriptor(String newType, String newName){
			name = newName;
			type = newType;
		}
		
		public void setName(String n){
			name = n;
		}
		
		public void setType(String t){
			type = t;
		}
		
		public String getName(){
			return name;
		}
		
		public String getType(){
			return type;
		}
}
