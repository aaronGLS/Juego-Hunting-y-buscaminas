package practica2.model.core;

/**
 * Resultado tipado de una partida.
 * Evita strings mágicos como "Ganada"/"Perdida" en la lógica.
 */
public enum ResultadoPartida {
    GANADA,
    PERDIDA;

    public String aTexto() {
        return switch (this) {
            case GANADA -> "Ganada";
            case PERDIDA -> "Perdida";
        };
    }

    public static ResultadoPartida desdeTexto(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("El texto de resultado no puede ser null");
        }
        String normalizado = texto.trim().toLowerCase();
        return switch (normalizado) {
            case "ganada" -> GANADA;
            case "perdida" -> PERDIDA;
            default -> throw new IllegalArgumentException("ResultadoPartida inválido: " + texto);
        };
    }
}

