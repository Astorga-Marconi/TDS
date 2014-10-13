/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Pila.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class:  
 */
package ir.CodeGen;

public class Pila {
    private int a;  
    private int b;  

  	public Pila() {
      a = 0;
      b = 0;
  	}

	  public void reset() {
      b = 0;
	  }

    public String getLabel() {
      return "L"+(++a);
    }

  	public int getOffSet() {
    	b += 4;
    	return b;
  	}  
 
}