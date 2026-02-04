package practica2.model.persistencia;

import java.io.*;
import java.util.List;
import java.text.SimpleDateFormat;
import practica2.model.buscaminas.ReporteBuscaminas;
import practica2.model.core.ResultadoPartida;
import practica2.model.hunting.ReporteHunting;

/**
 * Clase simplificada que maneja la persistencia de reportes en archivos de
 * texto.
 */
public class PersistenciaReportes implements RepositorioReportes {

    // Directorio donde se guardarán los reportes
    private static final String DIRECTORIO_REPORTES = "reportes";

    // Nombres de los archivos de reportes (sin extensión)
    private static final String ARCHIVO_BUSCAMINAS_GANADAS = "reportes_buscaminas_ganadas";
    private static final String ARCHIVO_BUSCAMINAS_PERDIDAS = "reportes_buscaminas_perdidas";
    private static final String ARCHIVO_HUNTING = "reportes_hunting";

    // Patrón para fechas en archivos (no usar SimpleDateFormat estático: no es thread-safe)
    private static final String PATRON_FECHA_ARCHIVO = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constructor que crea el directorio de reportes si no existe.
     */
    public PersistenciaReportes() {
        File directorio = new File(DIRECTORIO_REPORTES);
        if (!directorio.exists()) {
            if (!directorio.mkdirs()) {
                throw new ExcepcionPersistencia("No se pudo crear el directorio de reportes: " + DIRECTORIO_REPORTES);
            }
        }
    }

    /**
     * Guarda un reporte de Buscaminas en el archivo correspondiente.
     */
    public void guardarReporteBuscaminas(ReporteBuscaminas reporte) {
        String nombreArchivo;
        if (reporte.getResultado() == ResultadoPartida.GANADA) {
            nombreArchivo = ARCHIVO_BUSCAMINAS_GANADAS;
        } else {
            nombreArchivo = ARCHIVO_BUSCAMINAS_PERDIDAS;
        }

        String nombreJugador = reporte.getJugador().getNombre();
        guardarLinea(nombreArchivo, nombreJugador, serializarBuscaminas(reporte));
    }

    /**
     * Guarda un reporte de Hunting en su archivo correspondiente.
     */
    public void guardarReporteHunting(ReporteHunting reporte) {
        String nombreJugador = reporte.getJugador().getNombre();
        guardarLinea(ARCHIVO_HUNTING, nombreJugador, serializarHunting(reporte));
    }

    private String serializarBuscaminas(ReporteBuscaminas rb) {
        return String.format("%s|%d|%s|%s|%d|%d",
                rb.getJugador().getNombre(),
                rb.getDuracionPartida(),
                rb.getResultado().aTexto(),
                new SimpleDateFormat(PATRON_FECHA_ARCHIVO).format(rb.getFechaPartida()),
                rb.getCasillasDescubiertas(),
                rb.getMinasMarcadas());
    }

    private String serializarHunting(ReporteHunting rh) {
        return String.format("%s|%s|%d|%d",
                rh.getJugador().getNombre(),
                rh.getConfiguracion(),
                rh.getAciertos(),
                rh.getFallos());
    }

    /**
     * Guarda una línea en el archivo indicado. Si ya existe un reporte para el mismo
     * jugador, lo reemplaza; si no, lo agrega.
     */
    private void guardarLinea(String nombreArchivo, String nombreJugador, String nuevaLinea) {
        String rutaCompleta = DIRECTORIO_REPORTES + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);

        try {
            if (!archivo.exists()) {
                try (FileWriter fw = new FileWriter(archivo, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println(nuevaLinea);
                }
                return;
            }

            java.util.ArrayList<String> lineas = new java.util.ArrayList<>();
            boolean jugadorEncontrado = false;

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] campos = linea.split("\\|", -1);
                    if (!jugadorEncontrado && campos.length > 0 && campos[0].equalsIgnoreCase(nombreJugador)) {
                        lineas.add(nuevaLinea);
                        jugadorEncontrado = true;
                    } else {
                        lineas.add(linea);
                    }
                }
            }

            if (!jugadorEncontrado) {
                lineas.add(nuevaLinea);
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, false))) {
                for (String l : lineas) {
                    pw.println(l);
                }
            }
        } catch (IOException e) {
            throw new ExcepcionPersistencia("No se pudo guardar el reporte en: " + rutaCompleta, e);
        }
    }

    /**
     * Obtiene los reportes de Buscaminas ganados.
     */
    @Override
    public List<ReporteBuscaminasRegistro> obtenerReportesBuscaminasGanadas() {
        return obtenerDatosReportesBuscaminas(ARCHIVO_BUSCAMINAS_GANADAS);
    }

    /**
     * Obtiene los reportes de Buscaminas perdidos.
     */
    @Override
    public List<ReporteBuscaminasRegistro> obtenerReportesBuscaminasPerdidas() {
        return obtenerDatosReportesBuscaminas(ARCHIVO_BUSCAMINAS_PERDIDAS);
    }

    /**
     * Obtiene los reportes de Hunting.
     */
    @Override
    public List<ReporteHuntingRegistro> obtenerReportesHunting() {
        String rutaCompleta = DIRECTORIO_REPORTES + File.separator + ARCHIVO_HUNTING;
        File archivo = new File(rutaCompleta);

        if (!archivo.exists()) {
            return List.of();
        }

        java.util.ArrayList<ReporteHuntingRegistro> filas = new java.util.ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split("\\|", -1);
                if (campos.length >= 4) { // Comprobamos que tiene al menos los 4 campos requeridos
                    try {
                        filas.add(new ReporteHuntingRegistro(
                                campos[0],
                                campos[1],
                                Integer.parseInt(campos[2]),
                                Integer.parseInt(campos[3])));
                    } catch (NumberFormatException e) {
                        // Línea corrupta: omitir para evitar crasheo del panel de reportes.
                    }
                }
            }
        } catch (IOException e) {
            throw new ExcepcionPersistencia("No se pudieron leer los reportes de Hunting desde: " + rutaCompleta, e);
        }

        return filas;
    }

    /**
     * Método común para obtener datos de reportes de Buscaminas.
     */
    private List<ReporteBuscaminasRegistro> obtenerDatosReportesBuscaminas(String nombreArchivo) {
        String rutaCompleta = DIRECTORIO_REPORTES + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);

        if (!archivo.exists()) {
            return List.of();
        }

        java.util.ArrayList<ReporteBuscaminasRegistro> filas = new java.util.ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split("\\|", -1);
                if (campos.length >= 6) {
                    try {
                        filas.add(new ReporteBuscaminasRegistro(
                                campos[0],
                                Long.parseLong(campos[1]),
                                campos[2],
                                campos[3],
                                Integer.parseInt(campos[4]),
                                Integer.parseInt(campos[5])));
                    } catch (NumberFormatException e) {
                        // Línea corrupta: omitir para evitar crasheo del panel de reportes.
                    }
                }
            }
        } catch (IOException e) {
            throw new ExcepcionPersistencia("No se pudieron leer los reportes de Buscaminas desde: " + rutaCompleta, e);
        }

        return filas;
    }
}
