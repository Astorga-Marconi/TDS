#!/bin/bash

fileName=$1
name=${1%%.*}

echo "Borrando archivos antiguos ..."
rm codigoAssemblerGenerado.s
rm $name".out"

echo "Generando assembler ..."
javac */*.java && javac *.java && java Main $fileName

#echo "Assembler generado: "
#cat codigoAssemblerGenerado.s

echo "Compilando assembler ..."
gcc -o $name".out" codigoAssemblerGenerado.s printfloat.c

exit 0