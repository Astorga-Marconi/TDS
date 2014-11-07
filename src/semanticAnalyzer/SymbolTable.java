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
package semanticAnalyzer;

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

	public void insertNewVar(VarDescriptor descriptor){
		if (searchInCurrentLevel(descriptor.getName()) == null) {
			if (amountLevels > 1) {	// Se declara una variable local?
				levels.get(amountLevels-1).add(descriptor);
				VarLocation newArrayLoc = new VarLocation(descriptor.getName(), descriptor.getType());
				search(descriptor.getName()).setLocation(newArrayLoc);
			} else if (amountLevels == 1) {	// Se declara una variable global?
				levels.get(amountLevels-1).add(descriptor);
				GlobalVarLocation newArrayLoc = new GlobalVarLocation(descriptor.getName(), descriptor.getType());
				search(descriptor.getName()).setLocation(newArrayLoc);
			}
		} else {
			System.out.println("There is already a Variable with the same ID");
		}
	}

	public void insertNewArrayVar(ArrayVarDescriptor descriptor){
		if (searchInCurrentLevel(descriptor.getName()) == null) {
			if (descriptor.getSize() > 0) {
				if (amountLevels > 1) {	// Es un arreglo local.
					levels.get(amountLevels-1).add(descriptor);
					ArrayLocation newArrayLoc = new ArrayLocation(descriptor.getName(), descriptor.getType(), descriptor.getSize(), null);
					search(descriptor.getName()).setLocation(newArrayLoc);					
				} else if (amountLevels == 1) {	// Es un arreglo global
					levels.get(amountLevels-1).add(descriptor);
					GlobalArrayLocation newArrayLoc = new GlobalArrayLocation(descriptor.getName(), descriptor.getType(), descriptor.getSize(), null);
					search(descriptor.getName()).setLocation(newArrayLoc);
				}
			} else {
				System.out.println("Es tamaño de el arreglo no es correcto.");
			}
		} else {
			System.out.println("There is already a Variable with the same ID");
		}
	}

	public void insertParameters(List<VarDescriptor> params){
		int offsetParams = 8;
		VarLocation newVarLoc = null;
		for (VarDescriptor descriptor : params) {
			if (searchInCurrentLevel(descriptor.getName()) == null) {
				newVarLoc = new VarLocation(descriptor.getName(), descriptor.getType(), offsetParams);
				descriptor.setLocation(newVarLoc);
				levels.get(amountLevels-1).add(descriptor);
				offsetParams = offsetParams + 4;
			} else {
				System.out.println("Ya existe una variable con el nombre: " + descriptor.getName());
			}
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

	public boolean checkVar(String idMethod) {
		Descriptor d = (search(idMethod));
		if (d instanceof VarDescriptor) {
			return true;
		} else {
			System.out.println("No existe una variable con el nombre de " + idMethod + ".");
		}
		return false;
	}

	public boolean checkArrayVar(String idMethod) {
		Descriptor d = (search(idMethod));
		if (d instanceof ArrayVarDescriptor) {
			return true;
		} else {
			System.out.println("No existe un arreglo con el nombre de " + idMethod + ".");
		}
		return false;
	}

	public boolean checkMethodCall(String idMethod, LinkedList<Expression> params) {
		Descriptor d = (search(idMethod));
		if (d instanceof FunctionDescriptor) {
			FunctionDescriptor f = (FunctionDescriptor) d;
			if (f.getParameters().size() == params.size()) {
				for (int i = 0; i < params.size(); i++) {
					if (params.get(i).getType() != f.getParameters().get(i).getType()) {
						System.out.println("La llamada al metodo tiene parametros de tipo equivocado.");
						return false;
					}
				}
				return true;
			} else {
				System.out.println("La longitud de parametros no es la misma");
				return false;
			}
		} else {
			System.out.println("No existe un metodo con el nombre de " + idMethod + ".");
		}
		return false;
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
