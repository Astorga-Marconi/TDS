#!/bin/sh

# script para compilar con gcc

# toma un parametro (el archivo a compilar)
dest=$1

# elimina .ctds
name=${1%%.*}

java Main $dest

# compila con gcc
gcc -o $name".out" -m32 codigoAssemblerGenerado.s printfloat.c

exit 0