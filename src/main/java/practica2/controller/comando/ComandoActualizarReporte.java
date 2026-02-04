package practica2.controller.comando;

import practica2.model.buscaminas.ReporteBuscaminas;
import practica2.model.core.Reporte;
import practica2.model.persistencia.ExcepcionPersistencia;
import practica2.model.persistencia.RepositorioReportes;
import practica2.model.excepciones.ExcepcionJuego;
import practica2.model.hunting.ReporteHunting;


/**
 * Comando para guardar y actualizar reportes de partidas finalizadas.
 * Implementa la interfaz Comando siguiendo el patrón Command.
 */
public class ComandoActualizarReporte implements Comando {
    
    /**
     * Sistema de persistencia donde se guardarán los reportes.
     */
    private RepositorioReportes persistencia;
    
    /**
     * Reporte a guardar o actualizar.
     */
    private Reporte reporte;
    
    /**
     * Constructor que inicializa el comando con los parámetros necesarios.
     * 
     * @param persistencia Sistema de persistencia
     * @param reporte Reporte a guardar
     */
    public ComandoActualizarReporte(RepositorioReportes persistencia, Reporte reporte) {
        this.persistencia = persistencia;
        this.reporte = reporte;
    }
    
    /**
     * Ejecuta la acción de guardar un reporte en el sistema de persistencia.
     * 
     * @throws ExcepcionJuego Si se produce un error durante la persistencia
     */
    @Override
    public void ejecutar() throws ExcepcionJuego {
        if (persistencia == null) {
            throw new ExcepcionJuego("El sistema de persistencia no está disponible", ExcepcionJuego.ERROR_GENERAL);
        }
        
        if (reporte == null) {
            throw new ExcepcionJuego("No hay reporte para guardar", ExcepcionJuego.ERROR_GENERAL);
        }
        
        try {
            // Guardar el reporte según su tipo
            if (reporte instanceof ReporteBuscaminas) {
                persistencia.guardarReporteBuscaminas((ReporteBuscaminas) reporte);
            } else if (reporte instanceof ReporteHunting) {
                persistencia.guardarReporteHunting((ReporteHunting) reporte);
            } else {
                throw new ExcepcionJuego("Tipo de reporte no soportado", ExcepcionJuego.ERROR_GENERAL);
            }
        } catch (ExcepcionPersistencia e) {
            throw new ExcepcionJuego(
                    "No se pudo guardar el reporte. Revisa permisos y el directorio 'reportes/'.",
                    ExcepcionJuego.ERROR_GENERAL);
        } catch (Exception e) {
            throw new ExcepcionJuego("Error al guardar el reporte: " + e.getMessage(), ExcepcionJuego.ERROR_GENERAL);
        }
    }
}
