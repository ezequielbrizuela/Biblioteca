package unlar.edu.ar.exception;

/**
 * Se lanza cuando un estudiante ya tiene el maximo de libros prestados (3)
 * y quiere pedir uno mas.
 *
 * Analogia: es como intentar agregar un cuarto articulo a un carrito
 * que solo acepta 3 — el sistema te frena.
 */
public class LimitePrestamosExcedidoException extends Exception {

    public static final int LIMITE_MAXIMO = 3;

    public LimitePrestamosExcedidoException() {
        super("El estudiante ya alcanzo el limite maximo de " + LIMITE_MAXIMO + " prestamos.");
    }

    public LimitePrestamosExcedidoException(String mensaje) {
        super(mensaje);
    }

    public LimitePrestamosExcedidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
