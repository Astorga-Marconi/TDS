/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: Error.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
package error;

public class Error {
    
    private int line;
    private int col;
    private String desc;
    
    public Error (int l, int c, String d) {
        line = l;
        col = c;
        desc = d;
    }
    
    public String toString() {
        return ("Error en la linea " + line + ", columna " + col + ": " + desc);
    }
} 
