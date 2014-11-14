#!/bin/sh

# Script para compilar el .cup, el .flex y los .java

# Compila cup
cup SyntaxAnalyzer.cup

# Compila jflex
jflex LexicalAnalyzer.flex

# Compila todos los archivos .java
javac *.java

exit 0