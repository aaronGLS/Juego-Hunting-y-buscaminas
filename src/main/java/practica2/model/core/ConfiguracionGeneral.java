package practica2.model.core;

/**
 * Clase que proporciona parámetros, constantes o utilidades generales
 * compartidas entre los diferentes juegos del sistema.
 */
public class ConfiguracionGeneral {
    
    /**
     * Formato estándar para fechas en todo el sistema.
     */
    public static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss";
    
    /**
     * Niveles de dificultad disponibles para los juegos.
     */
    public static final String[] NIVELES_DIFICULTAD = {"Fácil", "Medio", "Difícil"};
    
    /**
     * Puntos base para cálculos de puntaje en juegos que lo requieran.
     */
    public static final int PUNTOS_BASE = 100;
    
    /**
     * Tiempo máximo de inactividad (en milisegundos) antes de pausar un juego.
     */
    public static final long TIEMPO_INACTIVIDAD_MAX = 60000; // 1 minuto
    
    /**
     * Cantidad máxima de jugadores que pueden guardar sus puntajes.
     */
    public static final int MAX_JUGADORES_RANKING = 10;
}
