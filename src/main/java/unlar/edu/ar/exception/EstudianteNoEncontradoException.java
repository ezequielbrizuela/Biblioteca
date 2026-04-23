
package unlar.edu.ar.exception;

/**
 * Se lanza cuando se busca un estudiante por legajo y no existe en el sistema.
 *
 * Analogia: es como buscar un contacto en tu celular por un numero
 * que nunca agendaste — simplemente no esta.
 */
public class EstudianteNoEncontradoException extends Exception {

    public EstudianteNoEncontradoException() {
        super("El estudiante no fue encontrado en el sistema.");
    }

    public EstudianteNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public EstudianteNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
