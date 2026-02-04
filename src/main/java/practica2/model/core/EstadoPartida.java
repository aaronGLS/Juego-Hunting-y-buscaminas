package practica2.model.core;

/**
 * Estados posibles de una partida.
 * Sustituye el uso de Strings para evitar errores por "strings mágicos".
 */
public enum EstadoPartida {
    NO_INICIADA,
    EN_CURSO,
    PAUSADA,
    FINALIZADA,
    INTERRUMPIDA
}

