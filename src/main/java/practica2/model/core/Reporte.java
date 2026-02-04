package practica2.model.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase base para la generación de reportes de partidas.
 * Define los atributos y métodos comunes para registrar datos como el tiempo de partida,
 * el resultado y la información del jugador.
 */
public class Reporte {
    /**
     * Jugador que participó en la partida.
     */
    protected Jugador jugador;
    
    /**
     * Duración de la partida en milisegundos.
     */
    protected long duracionPartida;
    
    /**
     * Resultado de la partida (Ganada/Perdida).
     */
    protected ResultadoPartida resultado;
    
    /**
     * Fecha y hora en que se realizó la partida.
     */
    protected Date fechaPartida;
    
    /**
     * Constructor base para inicializar un reporte con datos esenciales.
     * 
     * @param jugador El jugador de la partida
     * @param duracionPartida La duración de la partida en milisegundos
     * @param resultado El resultado tipado de la partida
     */
    public Reporte(Jugador jugador, long duracionPartida, ResultadoPartida resultado) {
        this.jugador = jugador;
        this.duracionPartida = duracionPartida;
        this.resultado = resultado;
        this.fechaPartida = new Date(); // Se establece la fecha actual
    }
    
    /**
     * Constructor con fecha específica para cargar reportes desde almacenamiento.
     * 
     * @param jugador El jugador de la partida
     * @param duracionPartida La duración de la partida en milisegundos
     * @param resultado El resultado tipado de la partida
     * @param fechaPartida La fecha en que se realizó la partida
     */
    public Reporte(Jugador jugador, long duracionPartida, ResultadoPartida resultado, Date fechaPartida) {
        this.jugador = jugador;
        this.duracionPartida = duracionPartida;
        this.resultado = resultado;
        this.fechaPartida = fechaPartida;
    }
    
    /**
     * Obtiene el jugador asociado al reporte.
     * 
     * @return El jugador de la partida
     */
    public Jugador getJugador() {
        return jugador;
    }
    
    /**
     * Establece el jugador asociado al reporte.
     * 
     * @param jugador El jugador a establecer
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    
    /**
     * Obtiene la duración de la partida.
     * 
     * @return La duración en milisegundos
     */
    public long getDuracionPartida() {
        return duracionPartida;
    }
    
    /**
     * Establece la duración de la partida.
     * 
     * @param duracionPartida La duración en milisegundos
     */
    public void setDuracionPartida(long duracionPartida) {
        this.duracionPartida = duracionPartida;
    }
    
    /**
     * Obtiene el resultado de la partida.
     * 
     * @return El resultado tipado
     */
    public ResultadoPartida getResultado() {
        return resultado;
    }

    public String getResultadoTexto() {
        return resultado != null ? resultado.aTexto() : "";
    }
    
    /**
     * Establece el resultado de la partida.
     * 
     * @param resultado El resultado tipado a establecer
     */
    public void setResultado(ResultadoPartida resultado) {
        this.resultado = resultado;
    }
    
    /**
     * Obtiene la fecha de la partida.
     * 
     * @return La fecha de la partida
     */
    public Date getFechaPartida() {
        return fechaPartida;
    }
    
    /**
     * Establece la fecha de la partida.
     * 
     * @param fechaPartida La fecha a establecer
     */
    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }
    
    /**
     * Genera un resumen del reporte en formato de texto.
     * 
     * @return Cadena con el resumen de la partida
     */
    public String generarResumen() {
        SimpleDateFormat sdf = new SimpleDateFormat(ConfiguracionGeneral.FORMATO_FECHA);
        StringBuilder resumen = new StringBuilder();
        
        resumen.append("Reporte de Partida\n");
        resumen.append("------------------\n");
        resumen.append("Jugador: ").append(jugador.getNombre()).append("\n");
        resumen.append("Fecha: ").append(sdf.format(fechaPartida)).append("\n");
        
        // Formatear la duración en un formato más legible (minutos:segundos)
        long minutos = duracionPartida / 60000;
        long segundos = (duracionPartida % 60000) / 1000;
        resumen.append("Duración: ").append(minutos).append(" min, ").append(segundos).append(" seg\n");
        
        resumen.append("Resultado: ").append(getResultadoTexto()).append("\n");
        
        return resumen.toString();
    }
}
