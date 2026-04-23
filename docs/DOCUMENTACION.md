# Sistema de Gestion de Biblioteca Universitaria

## 1. Descripcion general

Este proyecto implementa una aplicacion de consola para gestionar una biblioteca universitaria. Permite:

- registrar libros y estudiantes
- registrar prestamos
- registrar devoluciones con posible multa
- buscar libros por titulo
- listar prestamos activos de un estudiante
- ejecutar casos de prueba solicitados por el TP

El sistema fue desarrollado en Java con Maven, aplicando Programacion Orientada a Objetos, Collections Framework y manejo de excepciones personalizadas.

## 2. Relacion con el PDF del TP

### 2.1 Objetivo educativo

El proyecto fue estructurado para cumplir con el objetivo del enunciado:

- encapsulamiento mediante atributos privados con getters y setters
- modelado de entidades del dominio
- uso de Collections Framework
- manejo de excepciones personalizadas
- aplicacion de recursividad para el calculo de multas

### 2.2 Requisitos funcionales

#### Modelado de entidades

Se implementaron las clases:

- `Libro`
- `Estudiante`
- `Prestamo`

#### Estructuras de datos

En `BibliotecaService` se utilizan exactamente las colecciones:

- `ArrayList<Libro>` para el catalogo
- `HashMap<String, Estudiante>` para el registro de estudiantes
- `HashSet<Prestamo>` para los prestamos activos

#### Excepciones personalizadas

Se implementaron:

- `LibroNoDisponibleException`
- `EstudianteNoEncontradoException`
- `LimitePrestamosExcedidoException`

#### Funcionalidades principales

El sistema permite:

- registrar prestamos validando disponibilidad y limite de 3 libros por estudiante
- registrar devoluciones y calcular multa
- buscar libros por titulo de forma parcial e insensible a mayusculas
- listar prestamos activos por estudiante

#### Recursividad

El metodo `calcularMulta(int diasRetraso, double valorLibro)` fue implementado de forma recursiva, con tope de 30 dias.

#### Restricciones tecnicas

Se cumple con:

- atributos privados
- getters y setters
- constructores por defecto y parametrizados
- `toString()` en las clases principales

### 2.3 Entregables

#### Codigo fuente

El proyecto esta organizado en los paquetes:

- `unlar.edu.ar.model`
- `unlar.edu.ar.exception`
- `unlar.edu.ar.service`
- `unlar.edu.ar.ui`

#### Diagrama UML

Se incluye un diagrama editable en draw.io:

- `docs/DiagramaClases.drawio`

Tambien se conserva una version textual resumida:

- `docs/UML_Biblioteca.md`

y también en archivo PDF:

- `docs/BibliotecaDiagramaClases.pdf`

#### Main con casos de prueba

La clase principal incluye demostraciones de:

- carga de al menos 5 libros y 3 estudiantes
- prestamos exitosos
- captura de las 3 excepciones personalizadas
- calculo de multa con retraso de 15 dias
- analisis de la pila de llamadas recursiva

Archivo:

- `src/main/java/unlar/edu/ar/Main.java`

## 3. Estructura del proyecto

```text
biblioteca_poo/
+-- src/
|   +-- main/
|       +-- java/
|           +-- unlar/edu/ar/
|               +-- Main.java
|               +-- model/
|               |   +-- Libro.java
|               |   +-- Estudiante.java
|               |   \-- Prestamo.java
|               +-- exception/
|               |   +-- LibroNoDisponibleException.java
|               |   +-- EstudianteNoEncontradoException.java
|               |   \-- LimitePrestamosExcedidoException.java
|               +-- service/
|               |   \-- BibliotecaService.java
|               \-- ui/
|                   \-- BibliotecaUI.java
+-- docs/
|   +-- DOCUMENTACION.md
|   +-- BibliotecaDiagramaClases.drawio
|   +-- BibliotecaDiagramaClases.pdf
|   \-- UML_Biblioteca.md
+-- README.md
+-- pom.xml
\-- .gitignore
```

## 4. Funcionamiento del programa

### 4.1 Flujo principal

Al ejecutar `Main`:

1. se crea una instancia de `BibliotecaService`
2. se cargan libros y estudiantes iniciales
3. se ejecutan los casos de prueba del trabajo practico
4. se crea un segundo servicio para la interfaz interactiva
5. se inicia el menu por consola

### 4.2 Responsabilidad de cada clase

#### `Libro`

Representa un libro del catalogo con ISBN, titulo, autor, anio y estado de disponibilidad.

#### `Estudiante`

Representa un estudiante registrado con legajo, nombre, carrera y email.

#### `Prestamo`

Representa la relacion entre un libro y un estudiante, junto con la fecha de prestamo y devolucion.

#### `BibliotecaService`

Contiene la logica de negocio:

- alta de libros
- alta de estudiantes
- registro de prestamos
- registro de devoluciones
- busqueda de libros
- listado de prestamos
- calculo de multa recursivo

#### `BibliotecaUI`

Maneja la interaccion por consola:

- muestra menu
- solicita datos al usuario
- presenta resultados en formato legible
- muestra errores capturados

#### `Main`

Ejecuta la carga inicial y los casos de prueba del TP.

## 5. Datos cargados en el sistema

Estos son los datos base disponibles en el menu interactivo.

### 5.1 Libros

| ISBN  | Titulo                                               | Autor                | Anio |
|-------|------------------------------------------------------|----------------------|------|
| 13110 | El Lenguaje de Programacion C                        | Kernighan & Ritchie  | 1978 |
| 20133 | Clean Code                                           | Robert C. Martin     | 2008 |
| 13235 | Java: The Complete Reference. McGraw-Hill            | Schildt, H           | 2018 |
| 59651 | Code Conventions for the Java Programming Language.  | Oracle               | 2005 |
| 13468 | The Pragmatic Programmer                             | Hunt & Thomas        | 2019 |

### 5.2 Estudiantes

| Legajo   | Nombre         | Carrera                  | Email               |
|----------|----------------|--------------------------|---------------------|
| EISI676  | Ana Perez      | Ingenieria en Sistemas   | ana@unlar.edu.ar    |
| EISI1234 | Carlos Lopez   | Ingenieria en Sistemas   | carlos@unlar.edu.ar |
| LSI4321  | Maria Garcia   | Ingenieria Civil         | maria@unlar.edu.ar  |

## 6. Como probar el sistema

### 6.1 Ejecutar desde Maven

```bash
mvn compile
mvn exec:java
```

### 6.2 Probar el menu interactivo

#### Buscar libros

Ejemplos de busqueda:

- `java`
- `programacion`
- `code`

#### Registrar un prestamo

Ejemplos validos:

- ISBN: `13110`
- Legajo: `LSI4321`

Si intentas prestar un libro ya prestado, el sistema mostrara una excepcion controlada.

#### Registrar una devolucion

Para devolver, usa el ISBN y legajo del prestamo previamente realizado.

#### Ver prestamos de un estudiante

Ejemplos:

- `EISI676`
- `EISI1234`
- `LSI4321`

## 7. Casos de prueba incluidos en Main

La clase `Main` demuestra:

1. carga de datos inicial
2. busqueda de libros
3. prestamos exitosos
4. `LibroNoDisponibleException`
5. `EstudianteNoEncontradoException`
6. `LimitePrestamosExcedidoException`
7. devolucion con multa de 15 dias
8. analisis de recursion para 30 iteraciones

## 8. Collections usadas en el sistema

### `ArrayList<Libro>`

Se usa para mantener el catalogo de libros y poder recorrerlo facilmente en las busquedas y listados.

### `HashMap<String, Estudiante>`

Se usa para acceder rapido a cada estudiante a partir de su legajo.

### `HashSet<Prestamo>`

Se usa para evitar prestamos duplicados de la misma combinacion libro-estudiante, apoyandose en `equals()` y `hashCode()`.

## 9. Excepciones del sistema

### `LibroNoDisponibleException`

Se lanza cuando se intenta prestar un libro inexistente o no disponible.

### `EstudianteNoEncontradoException`

Se lanza cuando se usa un legajo que no existe en el sistema.

### `LimitePrestamosExcedidoException`

Se lanza cuando un estudiante intenta superar el maximo de 3 prestamos activos.

## 10. Observaciones para GitHub

- la carpeta `target` no se debe subir
- se agrego `.gitignore` para ignorar `target/` y archivos `.class`
- el proyecto puede reconstruirse localmente con Maven

