/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * Tabla de simbolos, para matener la información de los simbolos (identificadores) de un programa.
 * File Name: SymbolTable.java
 * To Create: javac SymbolTable.java
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
import ir.ast.*;
import java.util.LinkedList;
import java.util.List;

public class SymbolTable{
	
	private List<List<Descriptor>> levels;
	
	private int amountLevels;
	
	public SymbolTable(){
		// Constructor
		levels = new LinkedList<List<Descriptor>>();
		amountLevels = 0;
	}

	public int getCurrentLevel() {
		return (amountLevels-1);
	}
	
	public void insertNewBlock(){
		List level = new LinkedList<Descriptor>();
		levels.add(level);
		amountLevels++;
	}
		
	public void closeBlock(){
		amountLevels--;
		levels.remove(amountLevels);
	}
	
	public void insertSymbol(Type type, String name){
		Descriptor d = new VarDescriptor(type, name);
		insertNewDescriptor(d);
	}

	/**
	 * Insert the descriptor in the current level at the table.
	 * The name Id of the descriptor must be unique in the level.
	 * @param descriptor
	 */
	public void insertNewDescriptor(Descriptor descriptor){
		boolean idExist = false;
		for (int i = 0; i < levels.get(amountLevels-1).size() ; i++) {
			if (levels.get(amountLevels-1).get(i).getName() == descriptor.getName()) {
				idExist = true;
				break;
			}			
		}
		if (!idExist) {
			levels.get(amountLevels-1).add(descriptor);
		} else {
			throw new IllegalArgumentException("There is already a Descriptor with the same ID");			
		}
	}

	public void insertNewMethod(FunctionDescriptor descriptor){
		if (descriptor.getName().equals("main") && descriptor.getParameters().size() != 0) {
			System.out.println("El metodo Main no puede contener parametros.");
		}
		if (search(descriptor.getName()) == null) {
			levels.get(amountLevels-1).add(descriptor);
		} else {
			System.out.println("There is already a Method with the same ID");
		}
	}


	
	/**
	 * Search a descriptor in the levels.
	 * @param id 
	 * @return The Descriptor found, or otherwise null.
	 */
	public Descriptor search(String id){
		for (int i = amountLevels-1; i >= 0; i--)
		{
			for (int j = 0; j < levels.get(i).size(); j++)
			{
				if (levels.get(i).get(j).getName().equals(id)) {
					return levels.get(i).get(j);
				}
			}
		}
		return null;
	}

	/**
	 * Search a descriptor in the current level.
	 * @param id 
	 * @return The Descriptor found, or otherwise null.
	 */
	public Descriptor searchInCurrentLevel(String id){
		int i = amountLevels-1;
		for (int j = 0; j < levels.get(i).size(); j++)
		{
			if (levels.get(i).get(j).getName().equals(id)) {
				return levels.get(i).get(j);
			}
		}
		return null;
	}
	
	public void showTable(){
		for (int i = 0; i < levels.size(); i++)
		{
			System.out.println("Level " + i + ": ");
			for (int j = 0; j < levels.get(i).size(); j++)
			{
				System.out.println(levels.get(i).get(j).toString());
			}
		}
		
	}
	
}
