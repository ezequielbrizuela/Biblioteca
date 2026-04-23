package unlar.edu.ar.exception;

/**
 * Se lanza cuando se intenta pedir prestado un libro que ya esta prestado.
 *
 * Analogia: es como intentar sacar un numero de un dispensador vacio —
 * el sistema te avisa que no hay stock disponible.
 */
public class LibroNoDisponibleException extends Exception {

    public LibroNoDisponibleException() {
        super("El libro no esta disponible para prestamo.");
    }

    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public LibroNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
