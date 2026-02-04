package practica2.model.hunting;

import java.util.Date;
import practica2.model.core.Jugador;
import practica2.model.core.Reporte;
import practica2.model.core.ResultadoPartida;

/**
 * Extiende la clase base Reporte para registrar datos específicos de una partida de Hunting.
 * Almacena información como la configuración, aciertos y fallos.
 */
public class ReporteHunting extends Reporte {
    
    /**
     * Número de aciertos durante la partida.
     */
    private int aciertos;
    
    /**
     * Número de fallos durante la partida.
     */
    private int fallos;
    
    /**
     * Configuración de la partida.
     */
    private String configuracion;
    
    /**
     * Constructor para crear un reporte de partida recién finalizada.
     */
    public ReporteHunting(Jugador jugador, long duracionPartida, String resultado,
                        int aciertos, int fallos, String configuracion) {
        this(jugador, duracionPartida, ResultadoPartida.desdeTexto(resultado), aciertos, fallos, configuracion);
    }

    public ReporteHunting(Jugador jugador, long duracionPartida, ResultadoPartida resultado,
                        int aciertos, int fallos, String configuracion) {
        super(jugador, duracionPartida, resultado);
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.configuracion = configuracion;
    }

    
    /**
     * Constructor para cargar un reporte desde almacenamiento persistente.
     */
    public ReporteHunting(Jugador jugador, long duracionPartida, String resultado,
                        Date fechaPartida, int aciertos, int fallos, String configuracion) {
        this(jugador, duracionPartida, ResultadoPartida.desdeTexto(resultado), fechaPartida, aciertos, fallos, configuracion);
    }

    public ReporteHunting(Jugador jugador, long duracionPartida, ResultadoPartida resultado,
                        Date fechaPartida, int aciertos, int fallos, String configuracion) {
        super(jugador, duracionPartida, resultado, fechaPartida);
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.configuracion = configuracion;
    }
    
    // Getters y setters
    public int getAciertos() {
        return aciertos;
    }
    
    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }
    
    public int getFallos() {
        return fallos;
    }
    
    public void setFallos(int fallos) {
        this.fallos = fallos;
    }
    
    public String getConfiguracion() {
        return configuracion;
    }
    
    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }
    
    @Override
    public String generarResumen() {
        StringBuilder resumen = new StringBuilder(super.generarResumen());
        
        resumen.append("\nDetalles específicos de Hunting:\n");
        resumen.append("------------------------------\n");
        resumen.append("Aciertos: ").append(aciertos).append("\n");
        resumen.append("Fallos: ").append(fallos).append("\n");
        resumen.append("Configuración: ").append(configuracion).append("\n");
        
        return resumen.toString();
    }
}
