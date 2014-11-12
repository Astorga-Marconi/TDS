#!/bin/sh

# Toma un parametro (el archivo a compilar)
fileName=$1

# Elimina .ctds
name=${1%%.*} 

echo "Borrando archivos antiguos ..."
rm codigoAssemblerGenerado.s
rm $name".out"

# Generando assembler
java Main $fileName

echo "Compilando assembler ..."
gcc -o $name".out" -m32 codigoAssemblerGenerado.s printfloat.c

exit 0