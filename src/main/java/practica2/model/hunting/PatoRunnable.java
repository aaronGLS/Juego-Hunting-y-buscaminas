package practica2.model.hunting;

/**
 * Runnable que controla el ciclo de aparición y desaparición del pato.
 * Reemplaza la funcionalidad basada en Timer por una implementación con hilos.
 */
public class PatoRunnable implements Runnable {
    
    /**
     * Referencia al juego que controla este runnable.
     */
    private JuegoHunting juego;
    
    /**
     * Tiempo que el pato permanece visible en milisegundos.
     */
    private int tiempoVisibilidad;
    
    /**
     * Tiempo entre apariciones del pato en milisegundos.
     */
    private int tiempoEntreApariciones;
    
    /**
     * Constructor que inicializa el runnable con referencia al juego.
     * 
     * @param juego Juego al que pertenece este runnable
     */
    public PatoRunnable(JuegoHunting juego) {
        this.juego = juego;
        actualizarTiempos();
    }
    
    /**
     * Actualiza los tiempos de visibilidad y espera según la configuración actual del juego.
     */
    public synchronized void actualizarTiempos() {
        this.tiempoVisibilidad = juego.getTiempoVisibilidadActual();
        this.tiempoEntreApariciones = 1000; // 1 segundo entre apariciones
    }
    
    /**
     * Método principal que se ejecuta en el hilo.
     * Controla el ciclo de mostrar y ocultar el pato.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Mostrar pato
                juego.mostrarPato();
                
                // Esperar el tiempo de visibilidad
                Thread.sleep(tiempoVisibilidad);
                
                // Ocultar pato si sigue visible
                juego.ocultarPato();
                
                // Esperar antes de la siguiente aparición
                Thread.sleep(tiempoEntreApariciones);
                
                // Actualizar tiempos por si han cambiado
                actualizarTiempos();
            }
        } catch (InterruptedException e) {
            // Interrupción normal, solo terminar
            Thread.currentThread().interrupt();
        }
    }
}