package practica2.model.persistencia;

import java.util.List;

import practica2.model.buscaminas.ReporteBuscaminas;
import practica2.model.hunting.ReporteHunting;

/**
 * Contrato de persistencia de reportes (DIP).
 * Permite cambiar implementación (archivos/DB/etc.) sin tocar controladores.
 */
public interface RepositorioReportes {
    void guardarReporteBuscaminas(ReporteBuscaminas reporte);

    void guardarReporteHunting(ReporteHunting reporte);

    List<ReporteBuscaminasRegistro> obtenerReportesBuscaminasGanadas();

    List<ReporteBuscaminasRegistro> obtenerReportesBuscaminasPerdidas();

    List<ReporteHuntingRegistro> obtenerReportesHunting();
}
