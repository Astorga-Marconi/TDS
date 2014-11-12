#!/bin/sh

# compila cup
cup SyntaxAnalyzer.cup

# compila jflex
jflex LexicalAnalyzer.flex

# compila todos los archivos .java
javac *.java

exit 0