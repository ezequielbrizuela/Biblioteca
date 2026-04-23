package unlar.edu.ar.model;

import java.time.LocalDate;

/**
 * Representa un prestamo de un libro a un estudiante.
 */
public class Prestamo {

    private Libro libro;
    private Estudiante estudiante;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    /** Constructor por defecto. */
    public Prestamo() {
    }

    /** Constructor para registrar un prestamo nuevo. */
    public Prestamo(Libro libro, Estudiante estudiante) {
        this.libro = libro;
        this.estudiante = estudiante;
        this.fechaPrestamo = LocalDate.now();
    }

    /** Constructor completo, util para pruebas con fechas especificas. */
    public Prestamo(Libro libro, Estudiante estudiante,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.libro = libro;
        this.estudiante = estudiante;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Libro getLibro() { return libro; }

    public void setLibro(Libro libro) { this.libro = libro; }

    public Estudiante getEstudiante() { return estudiante; }

    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }

    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    /** Un prestamo es unico por la combinacion libro + estudiante. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Prestamo)) return false;
        Prestamo otro = (Prestamo) obj;
        return this.libro.equals(otro.libro)
                && this.estudiante.equals(otro.estudiante);
    }

    @Override
    public int hashCode() {
        int result = libro != null ? libro.hashCode() : 0;
        result = 31 * result + (estudiante != null ? estudiante.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Prestamo{libro='" + (libro != null ? libro.getTitulo() : "null")
                + "', estudiante='" + (estudiante != null ? estudiante.getNombre() : "null")
                + "', fechaPrestamo=" + fechaPrestamo
                + ", fechaDevolucion="
                + (fechaDevolucion != null ? fechaDevolucion : "pendiente") + "}";
    }
}
