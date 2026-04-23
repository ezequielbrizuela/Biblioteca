# Sistema de Gestion de Biblioteca Universitaria

Aplicacion de consola desarrollada en Java para gestionar libros, estudiantes y prestamos de una biblioteca universitaria.

## Contenido del proyecto

- gestion de libros y estudiantes
- registro de prestamos y devoluciones
- calculo recursivo de multas
- manejo de excepciones personalizadas
- menu interactivo por consola
- documentacion del TP y diagrama UML

## Tecnologias

- Java 17
- Maven

## Estructura principal

```text
src/main/java/unlar/edu/ar/
+-- Main.java
+-- model/
+-- service/
+-- ui/
\-- exception/
```

## Como ejecutar

```bash
mvn compile
mvn exec:java
```

## Documentacion

- Lineamientos y estructura del proyecto: `docs/TP_Biblioteca_POO.pdf`
- Documentacion completa del trabajo: `docs/DOCUMENTACION.md`
- Diagrama de clases editable: `docs/BibliotecaDiagramaClases.drawio`
- Diagrama de clases en formato PDF: `docs/BibliotecaDiagramaClases.pdf`
- Resumen textual del UML: `docs/UML_Biblioteca.md`

## Datos de prueba cargados

### Libros

- `13110` - El Lenguaje de Programacion C
- `20133` - Clean Code
- `13235` - Java: The Complete Reference. McGraw-Hill
- `59651` - Code Conventions for the Java Programming Language.
- `13468` - The Pragmatic Programmer

### Estudiantes

- `EISI676` - Ana Perez
- `EISI1234` - Carlos Lopez
- `LSI4321` - Maria Garcia
