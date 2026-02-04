package practica2.model.core;

/**
 * Interfaz para implementar el comportamiento del temporizador.
 * Define el contrato que cualquier implementación de temporizador debe seguir.
 */
public interface TemporizadorRunnable extends Runnable {
    
    /**
     * Inicia el temporizador.
     */
    void iniciar();
    
    /**
     * Detiene el temporizador.
     */
    void detener();

    /**
     * Pausa el temporizador.
     */
    void pausar();

    
    /**
     * Reanuda el temporizador.
     */
    void reanudar();
    
    /**
     * Obtiene el tiempo transcurrido en segundos.
     * 
     * @return Tiempo en segundos
     */
    int getTiempoTranscurrido();
    
    /**
     * Registra un observador para recibir notificaciones de cambios de tiempo.
     * 
     * @param observador El observador a registrar
     */
    void registrarObservador(TemporizadorObservador observador);
    
    /**
     * Elimina un observador para que deje de recibir notificaciones.
     * 
     * @param observador El observador a eliminar
     */
    void eliminarObservador(TemporizadorObservador observador);
}