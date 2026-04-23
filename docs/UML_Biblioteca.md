# Diagrama de clases UML textual

## Paquetes

- `unlar.edu.ar.model`
- `unlar.edu.ar.service`
- `unlar.edu.ar.ui`
- `unlar.edu.ar.exception`

## Clases

### `Libro`

- Atributos:
- `isbn: String`
- `titulo: String`
- `autor: String`
- `anio: int`
- `disponible: boolean`
- Metodos:
- constructores por defecto y parametrizado
- getters y setters
- `equals(Object)`
- `hashCode()`
- `toString()`

### `Estudiante`

- Atributos:
- `legajo: String`
- `nombre: String`
- `carrera: String`
- `email: String`
- Metodos:
- constructores por defecto y parametrizado
- getters y setters
- `equals(Object)`
- `hashCode()`
- `toString()`

### `Prestamo`

- Atributos:
- `libro: Libro`
- `estudiante: Estudiante`
- `fechaPrestamo: LocalDate`
- `fechaDevolucion: LocalDate`
- Metodos:
- constructores por defecto y parametrizados
- getters y setters
- `equals(Object)`
- `hashCode()`
- `toString()`

## Excepciones

### `LibroNoDisponibleException`

- Hereda de `Exception`
- Se lanza cuando se intenta prestar un libro no disponible

### `EstudianteNoEncontradoException`

- Hereda de `Exception`
- Se lanza cuando el legajo no existe

### `LimitePrestamosExcedidoException`

- Hereda de `Exception`
- Se lanza cuando un estudiante supera el maximo de 3 prestamos

## Servicio

### `BibliotecaService`

- Atributos:
- `catalogo: ArrayList<Libro>`
- `estudiantes: HashMap<String, Estudiante>`
- `prestamosActivos: HashSet<Prestamo>`
- Responsabilidades:
- agregar libros al catalogo
- registrar estudiantes
- registrar prestamos
- registrar devoluciones
- buscar libros por titulo
- listar prestamos por estudiante
- calcular multa recursivamente

## Interfaz de usuario

### `BibliotecaUI`

- Atributos:
- `servicio: BibliotecaService`
- `scanner: Scanner`
- Responsabilidades:
- mostrar menu de consola
- pedir datos al usuario
- invocar operaciones del servicio
- capturar y mostrar errores

## Clase principal

### `Main`

- Responsabilidades:
- cargar datos iniciales
- ejecutar casos de prueba del TP
- iniciar el menu interactivo

## Relaciones

- `Prestamo` se asocia con `Libro`
- `Prestamo` se asocia con `Estudiante`
- `BibliotecaService` administra colecciones de `Libro`, `Estudiante` y `Prestamo`
- `BibliotecaUI` depende de `BibliotecaService`
- `Main` depende de `BibliotecaService`, `BibliotecaUI` y las clases del modelo
- `BibliotecaService` usa las tres excepciones personalizadas

