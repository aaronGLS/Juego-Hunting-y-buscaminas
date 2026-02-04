package practica2;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Test;

import practica2.model.persistencia.PersistenciaReportes;
import practica2.model.persistencia.ReporteBuscaminasRegistro;
import practica2.model.persistencia.ReporteHuntingRegistro;

public class PersistenciaReportesRobustezTest {

    @Test
    public void obtenerReportesHunting_omiteLineasInvalidas_yNoCrashea() throws Exception {
        PersistenciaReportes persistencia = new PersistenciaReportes();

        File archivo = new File("reportes" + File.separator + "reportes_hunting");
        try (PrintWriter out = new PrintWriter(archivo)) {
            out.println("Ana|cfg|3|1");      // válida
            out.println("Bad|cfg|x|2");      // inválida (aciertos)
            out.println("Bob|cfg|5|0");      // válida
        }

        List<ReporteHuntingRegistro> datos = persistencia.obtenerReportesHunting();
        assertEquals(2, datos.size());
        assertEquals("Ana", datos.get(0).jugador());
        assertEquals(3, datos.get(0).aciertos());
        assertEquals(1, datos.get(0).fallos());
        assertEquals("cfg", datos.get(0).configuracion());
        assertEquals("Bob", datos.get(1).jugador());
        assertEquals(5, datos.get(1).aciertos());
        assertEquals(0, datos.get(1).fallos());
        assertEquals("cfg", datos.get(1).configuracion());

        // Limpieza
        archivo.delete();
    }

    @Test
    public void obtenerReportesBuscaminas_omiteLineasInvalidas_yNoCrashea() throws Exception {
        PersistenciaReportes persistencia = new PersistenciaReportes();

        File archivo = new File("reportes" + File.separator + "reportes_buscaminas_ganadas");
        try (PrintWriter out = new PrintWriter(archivo)) {
            out.println("Ana|1000|Ganada|2020-01-01 00:00:00|10|2"); // válida
            out.println("Bad|ms|Ganada|2020-01-01 00:00:00|x|2");    // inválida (duración/casillas)
            out.println("Bob|2000|Ganada|2020-01-01 00:00:00|7|1");  // válida
        }

        List<ReporteBuscaminasRegistro> datos = persistencia.obtenerReportesBuscaminasGanadas();
        assertEquals(2, datos.size());
        assertEquals("Ana", datos.get(0).jugador());
        assertEquals(1000L, datos.get(0).duracionMs());
        assertEquals("Ganada", datos.get(0).resultado());
        assertEquals("2020-01-01 00:00:00", datos.get(0).fecha());
        assertEquals(10, datos.get(0).casillasDescubiertas());
        assertEquals(2, datos.get(0).minasMarcadas());
        assertEquals("Bob", datos.get(1).jugador());
        assertEquals(2000L, datos.get(1).duracionMs());
        assertEquals("Ganada", datos.get(1).resultado());
        assertEquals("2020-01-01 00:00:00", datos.get(1).fecha());
        assertEquals(7, datos.get(1).casillasDescubiertas());
        assertEquals(1, datos.get(1).minasMarcadas());

        // Limpieza
        archivo.delete();
    }
}
