package practica2.model.validacion;

/**
 * Contrato genérico para validación.
 * Retorna null si es válido o un mensaje de error si no.
 */
public interface Validador<T> {
    String validar(T input);
}