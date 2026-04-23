package unlar.edu.ar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import unlar.edu.ar.exception.EstudianteNoEncontradoException;
import unlar.edu.ar.exception.LibroNoDisponibleException;
import unlar.edu.ar.exception.LimitePrestamosExcedidoException;
import unlar.edu.ar.model.Estudiante;
import unlar.edu.ar.model.Libro;
import unlar.edu.ar.model.Prestamo;

/**
 * Contiene toda la logica de negocio de la biblioteca.
 */
public class BibliotecaService {

    private static final int MAX_PRESTAMOS_POR_ESTUDIANTE = 3;
    private static final double PORCENTAJE_MULTA_DIARIO = 0.01;
    private static final int MAX_DIAS_MULTA = 30;

    private final ArrayList<Libro> catalogo;
    private final HashMap<String, Estudiante> estudiantes;
    private final HashSet<Prestamo> prestamosActivos;

    public BibliotecaService() {
        catalogo = new ArrayList<>();
        estudiantes = new HashMap<>();
        prestamosActivos = new HashSet<>();
    }

    /** Agrega un libro al catalogo. */
    public void agregarLibro(Libro libro) {
        catalogo.add(libro);
    }

    /** Registra un estudiante en el sistema. */
    public void registrarEstudiante(Estudiante estudiante) {
        estudiantes.put(estudiante.getLegajo(), estudiante);
    }

    /**
     * Registra un prestamo nuevo.
     *
     * Valida:
     *   1. Que el libro exista y este disponible.
     *   2. Que el estudiante exista.
     *   3. Que el estudiante no supere el limite de 3 libros.
     *
     * @return el prestamo creado
     * @throws LibroNoDisponibleException si el libro no esta disponible
     * @throws EstudianteNoEncontradoException si el legajo no existe
     * @throws LimitePrestamosExcedidoException si el estudiante ya tiene 3 libros
     */
    public Prestamo registrarPrestamo(String isbn, String legajo)
            throws LibroNoDisponibleException,
            EstudianteNoEncontradoException,
            LimitePrestamosExcedidoException {
        return registrarPrestamo(isbn, legajo, LocalDate.now());
    }

    /**
     * Registra un prestamo con una fecha especifica.
     *
     * Se usa para pruebas y simulaciones de atrasos.
     */
    public Prestamo registrarPrestamo(String isbn, String legajo, LocalDate fechaPrestamo)
            throws LibroNoDisponibleException,
            EstudianteNoEncontradoException,
            LimitePrestamosExcedidoException {

        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null || !libro.isDisponible()) {
            throw new LibroNoDisponibleException(
                    "El libro con ISBN '" + isbn + "' no esta disponible.");
        }

        Estudiante estudiante = buscarEstudiantePorLegajo(legajo);
        if (estudiante == null) {
            throw new EstudianteNoEncontradoException(
                    "No existe el estudiante con legajo '" + legajo + "'.");
        }

        int prestamosDelEstudiante = contarPrestamosDeEstudiante(legajo);
        if (prestamosDelEstudiante >= MAX_PRESTAMOS_POR_ESTUDIANTE) {
            throw new LimitePrestamosExcedidoException(
                    "El estudiante '" + estudiante.getNombre()
                            + "' ya tiene " + MAX_PRESTAMOS_POR_ESTUDIANTE + " libros prestados.");
        }

        Prestamo prestamo = new Prestamo(libro, estudiante, fechaPrestamo, null);
        libro.setDisponible(false);
        prestamosActivos.add(prestamo);
        return prestamo;
    }

    /**
     * Registra la devolucion de un libro y calcula la multa si hay retraso.
     *
     * @param isbn ISBN del libro a devolver
     * @param legajo legajo del estudiante que devuelve
     * @param valorLibro precio del libro
     * @return monto de multa
     * @throws EstudianteNoEncontradoException si el legajo no existe
     */
    public double registrarDevolucion(String isbn, String legajo, double valorLibro)
            throws EstudianteNoEncontradoException {

        Estudiante estudiante = buscarEstudiantePorLegajo(legajo);
        if (estudiante == null) {
            throw new EstudianteNoEncontradoException(
                    "No existe el estudiante con legajo '" + legajo + "'.");
        }

        Prestamo prestamoEncontrado = null;
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getLibro().getIsbn().equals(isbn)
                    && prestamo.getEstudiante().getLegajo().equals(legajo)) {
                prestamoEncontrado = prestamo;
                break;
            }
        }

        if (prestamoEncontrado == null) {
            System.out.println("No se encontro un prestamo activo para ese libro y estudiante.");
            return 0;
        }

        LocalDate fechaLimite = prestamoEncontrado.getFechaPrestamo().plusDays(7);
        LocalDate hoy = LocalDate.now();
        int diasRetraso = 0;

        if (hoy.isAfter(fechaLimite)) {
            diasRetraso = (int) (hoy.toEpochDay() - fechaLimite.toEpochDay());
        }

        double multa = calcularMulta(diasRetraso, valorLibro);

        prestamoEncontrado.getLibro().setDisponible(true);
        prestamoEncontrado.setFechaDevolucion(hoy);
        prestamosActivos.remove(prestamoEncontrado);

        return multa;
    }

    /**
     * Calcula la multa de forma recursiva.
     *
     * Por cada dia de retraso, suma 1% del valor del libro.
     * Maximo 30 dias.
     *
     * @param diasRetraso dias de retraso
     * @param valorLibro valor del libro
     * @return multa total
     */
    public double calcularMulta(int diasRetraso, double valorLibro) {
        if (diasRetraso <= 0) {
            return 0;
        }

        if (diasRetraso > MAX_DIAS_MULTA) {
            diasRetraso = MAX_DIAS_MULTA;
        }

        return valorLibro * PORCENTAJE_MULTA_DIARIO
                + calcularMulta(diasRetraso - 1, valorLibro);
    }

    /**
     * Busca libros cuyo titulo contenga el fragmento dado sin importar mayusculas.
     *
     * @param fragmento parte del titulo a buscar
     * @return lista de libros que coinciden
     */
    public List<Libro> buscarLibrosPorTitulo(String fragmento) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : catalogo) {
            if (libro.getTitulo().toLowerCase().contains(fragmento.toLowerCase())) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    /**
     * Lista todos los prestamos activos de un estudiante.
     *
     * @param legajo identificador del estudiante
     * @return lista de prestamos activos
     * @throws EstudianteNoEncontradoException si el legajo no existe
     */
    public List<Prestamo> listarPrestamosPorEstudiante(String legajo)
            throws EstudianteNoEncontradoException {

        if (buscarEstudiantePorLegajo(legajo) == null) {
            throw new EstudianteNoEncontradoException(
                    "No existe el estudiante con legajo '" + legajo + "'.");
        }

        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getEstudiante().getLegajo().equals(legajo)) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }

    /** Busca un libro por ISBN. */
    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    /** Busca un estudiante por legajo. */
    public Estudiante buscarEstudiantePorLegajo(String legajo) {
        return estudiantes.get(legajo);
    }

    /** Cuenta cuantos prestamos activos tiene un estudiante. */
    private int contarPrestamosDeEstudiante(String legajo) {
        int contador = 0;
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getEstudiante().getLegajo().equals(legajo)) {
                contador++;
            }
        }
        return contador;
    }

    public List<Libro> getCatalogo() {
        return new ArrayList<>(catalogo);
    }

    public List<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes.values());
    }

    public HashSet<Prestamo> getPrestamosActivos() {
        return new HashSet<>(prestamosActivos);
    }

    @Override
    public String toString() {
        return "BibliotecaService{libros=" + catalogo.size()
                + ", estudiantes=" + estudiantes.size()
                + ", prestamosActivos=" + prestamosActivos.size() + "}";
    }
}
