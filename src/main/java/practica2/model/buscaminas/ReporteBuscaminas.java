package practica2.model.buscaminas;

import java.util.Date;
import practica2.model.core.Jugador;
import practica2.model.core.Reporte;
import practica2.model.core.ResultadoPartida;

/**
 * Extiende la clase base Reporte para registrar datos específicos de una
 * partida de Buscaminas.
 * Almacena información como la cantidad de casillas descubiertas, las minas
 * marcadas
 * y la duración de la partida.
 */
public class ReporteBuscaminas extends Reporte {

    /**
     * Número de casillas descubiertas durante la partida.
     */
    private int casillasDescubiertas;

    /**
     * Número de casillas marcadas con banderas.
     */
    private int minasMarcadas;

    /**
     * Constructor para crear un reporte de partida recién finalizada.
     * 
     * @param jugador              El jugador de la partida
     * @param duracionPartida      La duración de la partida en milisegundos
     * @param resultado            El resultado (Ganada/Perdida)
     * @param casillasDescubiertas Número de casillas descubiertas
     * @param minasMarcadas        Número de minas marcadas
     */
    public ReporteBuscaminas(Jugador jugador, long duracionPartida, String resultado,
            int casillasDescubiertas, int minasMarcadas) {
        this(jugador, duracionPartida, ResultadoPartida.desdeTexto(resultado), casillasDescubiertas, minasMarcadas);
    }

    public ReporteBuscaminas(Jugador jugador, long duracionPartida, ResultadoPartida resultado,
            int casillasDescubiertas, int minasMarcadas) {
        super(jugador, duracionPartida, resultado);
        this.casillasDescubiertas = casillasDescubiertas;
        this.minasMarcadas = minasMarcadas;
    }

    /**
     * Constructor para cargar un reporte desde almacenamiento persistente.
     * 
     * @param jugador              El jugador de la partida
     * @param duracionPartida      La duración de la partida en milisegundos
     * @param resultado            El resultado (Ganada/Perdida)
     * @param fechaPartida         La fecha en que se realizó la partida
     * @param casillasDescubiertas Número de casillas descubiertas
     * @param minasMarcadas        Número de minas marcadas
     */
    public ReporteBuscaminas(Jugador jugador, long duracionPartida, String resultado,
            Date fechaPartida, int casillasDescubiertas, int minasMarcadas) {
        this(jugador, duracionPartida, ResultadoPartida.desdeTexto(resultado), fechaPartida, casillasDescubiertas, minasMarcadas);
    }

    public ReporteBuscaminas(Jugador jugador, long duracionPartida, ResultadoPartida resultado,
            Date fechaPartida, int casillasDescubiertas, int minasMarcadas) {
        super(jugador, duracionPartida, resultado, fechaPartida);
        this.casillasDescubiertas = casillasDescubiertas;
        this.minasMarcadas = minasMarcadas;
    }

    /**
     * Obtiene el número de casillas descubiertas.
     * 
     * @return Casillas descubiertas
     */
    public int getCasillasDescubiertas() {
        return casillasDescubiertas;
    }

    /**
     * Establece el número de casillas descubiertas.
     * 
     * @param casillasDescubiertas Nuevo valor de casillas descubiertas
     */
    public void setCasillasDescubiertas(int casillasDescubiertas) {
        this.casillasDescubiertas = casillasDescubiertas;
    }

    /**
     * Obtiene el número de minas marcadas.
     * 
     * @return Minas marcadas
     */
    public int getMinasMarcadas() {
        return minasMarcadas;
    }

    /**
     * Establece el número de minas marcadas.
     * 
     * @param minasMarcadas Nuevo valor de minas marcadas
     */
    public void setMinasMarcadas(int minasMarcadas) {
        this.minasMarcadas = minasMarcadas;
    }

    /**
     * Genera un resumen detallado de la partida de Buscaminas.
     * Incluye información específica como casillas descubiertas y minas marcadas.
     * 
     * @return Cadena con el resumen detallado de la partida
     */
    @Override
    public String generarResumen() {
        // Obtenemos el resumen básico de la clase padre
        StringBuilder resumen = new StringBuilder(super.generarResumen());

        // Añadimos información específica del Buscaminas
        resumen.append("\nDetalles específicos de Buscaminas:\n");
        resumen.append("----------------------------------\n");
        resumen.append("Casillas descubiertas: ").append(casillasDescubiertas).append("\n");
        resumen.append("Minas marcadas: ").append(minasMarcadas).append("\n");

        // Calcular estadísticas adicionales si se tienen los datos necesarios
        if (resultado == ResultadoPartida.GANADA) {
            resumen.append("¡Felicidades! Has completado el tablero sin activar ninguna mina.\n");
        } else if (resultado == ResultadoPartida.PERDIDA) {
            resumen.append("Has activado una mina. ¡Mejor suerte la próxima vez!\n");
        }

        return resumen.toString();
    }

    /**
     * Calcula un puntaje basado en la duración y el resultado de la partida.
     * Este método podría utilizarse para rankings o comparativas.
     * 
     * @return Puntaje calculado
     */
    public int calcularPuntaje() {
        // Base de puntos según resultado
        int puntajeBase = resultado == ResultadoPartida.GANADA ? 1000 : 100;

        // Bonificación por casillas descubiertas
        int bonificacionCasillas = casillasDescubiertas * 10;

        // Penalización por duración (menos tiempo = más puntos)
        // Limitamos a un máximo de 5 minutos para el cálculo
        long duracionMinutos = Math.min(duracionPartida / 60000, 5);
        int penalizacionTiempo = (int) (duracionMinutos * 50);

        return puntajeBase + bonificacionCasillas - penalizacionTiempo;
    }
}
