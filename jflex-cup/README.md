
Descripción para construir la salida


Para compilar:
cup analyzerSyntax.cup
jflex analisisLexico.flex

Luego compilar los .java generados por la compilaciones anteriores haciendo:
javac *.java

Finalmente si quisieras correr los test por linea de comando hacemos:
java Main tests/"archivo test.ctds"

Para correr un test hacemos:
java -jar Analizador.jar tests/"archivo test.ctds" 
