package practica2.model.persistencia;

/**
 * DTO de persistencia: representa una fila leída/escrita en archivos de reportes
 * de Buscaminas.
 *
 * Nota: esta clase no conoce nada de Swing ni de tablas; solo transporta datos.
 */
public record ReporteBuscaminasRegistro(
        String jugador,
        long duracionMs,
        String resultado,
        String fecha,
        int casillasDescubiertas,
        int minasMarcadas) {
}

