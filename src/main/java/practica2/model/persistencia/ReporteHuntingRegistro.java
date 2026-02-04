package practica2.model.persistencia;

/**
 * DTO de persistencia: representa una fila leída/escrita en archivos de reportes
 * de Hunting.
 *
 * Nota: esta clase no conoce nada de Swing ni de tablas; solo transporta datos.
 */
public record ReporteHuntingRegistro(
        String jugador,
        String configuracion,
        int aciertos,
        int fallos) {
}

