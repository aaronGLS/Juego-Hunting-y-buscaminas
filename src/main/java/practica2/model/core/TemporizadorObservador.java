package practica2.model.core;

/**
 * Interfaz para observar las actualizaciones del temporizador de una partida.
 * Los controladores u otros componentes pueden implementar esta interfaz
 * para recibir notificaciones cuando el tiempo se actualiza.
 */
public interface TemporizadorObservador {
    /**
     * Método llamado cuando se actualiza el tiempo en el temporizador.
     * 
     * @param segundos El tiempo transcurrido en segundos
     */
    void tiempoActualizado(int segundos);
}