#!/bin/sh

# script para compilar el .cup, el .flex y los .java

# compila cup
cup SyntaxAnalyzer.cup

# compila jflex
jflex LexicalAnalyzer.flex

# compila todos los archivos .java
javac *.java

exit 0