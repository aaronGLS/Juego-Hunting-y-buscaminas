package practica2.model.persistencia;

/**
 * Excepción de persistencia para lectura/escritura de reportes.
 * Se usa para propagar fallos hacia la capa controller/view y permitir feedback
 * al usuario (en lugar de fallar silenciosamente con System.err).
 */
public class ExcepcionPersistencia extends RuntimeException {

    public ExcepcionPersistencia(String message) {
        super(message);
    }

    public ExcepcionPersistencia(String message, Throwable cause) {
        super(message, cause);
    }
}

