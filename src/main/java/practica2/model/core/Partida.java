package practica2.model.core;

/**
 * Clase abstracta que define la estructura básica y comportamiento común
 * para cualquier partida, ya sea de Buscaminas o Hunting.
 * Proporciona mecanismos para registrar tiempo y asociar un jugador.
 */
public abstract class Partida implements TemporizadorObservador {
    /**
     * Jugador que participa en la partida.
     */
    protected Jugador jugador;

    /**
     * Tiempo en milisegundos cuando se inició la partida.
     */
    protected long tiempoInicio;

    /**
     * Tiempo en milisegundos cuando finalizó la partida.
     */
    protected long tiempoFin;

    /**
     * Estado actual de la partida (EN_CURSO, FINALIZADA, etc.).
     */
    protected EstadoPartida estado;

    /**
     * Tiempo transcurrido en segundos desde el inicio de la partida.
     */
    protected int tiempoTranscurrido;

    /**
     * Hilo que actualiza el tiempo transcurrido.
     */
    protected Thread hiloTemporizador;

    /**
     * Objeto temporizador que implementa la interfaz TemporizadorRunnable.
     */
    protected TemporizadorRunnable temporizador;

    /**
     * Inicializa una partida con el jugador especificado.
     * 
     * @param jugador El jugador que participará en la partida
     * @param temporizador Temporizador a utilizar (inyección para DIP)
     */
    public Partida(Jugador jugador, TemporizadorRunnable temporizador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser null");
        }
        if (temporizador == null) {
            throw new IllegalArgumentException("El temporizador no puede ser null");
        }

        this.jugador = jugador;
        this.estado = EstadoPartida.NO_INICIADA;
        this.tiempoTranscurrido = 0;
        this.temporizador = temporizador;
        // La partida se registra como observador del temporizador
        this.temporizador.registrarObservador(this);
    }

    /**
     * Obtiene el jugador asociado a la partida.
     * 
     * @return El jugador de la partida
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Establece el jugador de la partida.
     * 
     * @param jugador El jugador a establecer
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * Obtiene el tiempo de inicio de la partida.
     * 
     * @return El tiempo de inicio en milisegundos
     */
    public long getTiempoInicio() {
        return tiempoInicio;
    }

    /**
     * Obtiene el tiempo de finalización de la partida.
     * 
     * @return El tiempo de finalización en milisegundos
     */
    public long getTiempoFin() {
        return tiempoFin;
    }

    /**
     * Obtiene el tiempo transcurrido en segundos desde que inició la partida.
     * 
     * @return El tiempo transcurrido en segundos
     */
    public int getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    /**
     * Obtiene el estado actual de la partida.
     * 
     * @return El estado de la partida
     */
    public String getEstado() {
        return estadoToTexto(estado);
    }

    /**
     * Obtiene el estado de la partida tipado (sin strings mágicos).
     */
    public EstadoPartida getEstadoPartida() {
        return estado;
    }

    /**
     * Establece el estado de la partida.
     * 
     * @param estado El nuevo estado
     */
    protected void setEstado(EstadoPartida estado) {
        if (estado == null) {
            this.estado = EstadoPartida.NO_INICIADA;
            return;
        }
        this.estado = estado;
    }

    private static String estadoToTexto(EstadoPartida estado) {
        if (estado == null) {
            return "No iniciada";
        }
        return switch (estado) {
            case EN_CURSO -> "En curso";
            case PAUSADA -> "Pausada";
            case FINALIZADA -> "Finalizada";
            case INTERRUMPIDA -> "Interrumpida";
            case NO_INICIADA -> "No iniciada";
        };
    }

    /**
     * Calcula la duración de la partida en milisegundos.
     * Utiliza el tiempo registrado por el hilo temporizador.
     * 
     * @return La duración en milisegundos
     */
    public long calcularDuracion() {
        // Usamos el contador actualizado por el hilo temporizador
        // convertido a milisegundos para consistencia
        return tiempoTranscurrido * 1000L;
    }

    /**
     * Inicia el hilo temporizador que cuenta el tiempo transcurrido.
     */
    protected void iniciarTemporizador() {
        if (hiloTemporizador != null && hiloTemporizador.isAlive()) {
            return; // El temporizador ya está activo
        }

        temporizador.iniciar();
        tiempoTranscurrido = 0;

        hiloTemporizador = new Thread(temporizador);
        hiloTemporizador.setDaemon(true); // Permite que el hilo se cierre al finalizar la aplicación
        hiloTemporizador.start();
    }

    /**
     * Detiene el hilo temporizador.
     */
    protected void detenerTemporizador() {
        temporizador.detener();
        if (hiloTemporizador != null) {
            hiloTemporizador.interrupt();
        }
    }

    /**
     * Pausa el temporizador.
     * Se puede usar para pausar la partida sin finalizarla.
     */
    protected void pausarTemporizador() {
        temporizador.pausar();
    }

    /**
     * Reanuda el temporizador después de una pausa.
     * Se puede usar para continuar la partida después de una pausa.
     */
    protected void reanudarTemporizador() {
        temporizador.reanudar();

        if (hiloTemporizador != null && !hiloTemporizador.isAlive()) {
            hiloTemporizador = new Thread(temporizador);
            hiloTemporizador.setDaemon(true); // Permite que el hilo se cierre al finalizar la aplicación
            hiloTemporizador.start();
        }
    }

    /**
     * Implementación del método de la interfaz TemporizadorObservador.
     * Se ejecuta cuando el temporizador notifica un cambio en el tiempo.
     * 
     * @param segundos El tiempo transcurrido en segundos
     */
    @Override
    public void tiempoActualizado(int segundos) {
        this.tiempoTranscurrido = segundos;
    }

    /**
     * Registra un observador adicional para el temporizador.
     * Útil para que controladores u otros componentes reciban actualizaciones de
     * tiempo.
     * 
     * @param observador El observador a registrar
     */
    public void registrarObservadorTemporizador(TemporizadorObservador observador) {
        if (temporizador != null) {
            temporizador.registrarObservador(observador);
        }
    }

    /**
     * Elimina un observador del temporizador para que deje de recibir
     * actualizaciones.
     * 
     * @param observador El observador a eliminar
     */
    public void eliminarObservadorTemporizador(TemporizadorObservador observador) {
        if (temporizador != null) {
            temporizador.eliminarObservador(observador);
        }
    }

    /**
     * Notifica a los observadores sobre la actualización del tiempo.
     * Las clases hijas pueden implementar este método si necesitan
     * alguna funcionalidad específica.
     */
    protected void notificarActualizacionTiempo() {
        this.tiempoTranscurrido = temporizador.getTiempoTranscurrido();
    }

    /**
     * Método abstracto para iniciar una partida.
     * Cada juego debe implementar su lógica específica de inicialización.
     */
    public abstract void iniciarPartida();

    /**
     * Método abstracto para finalizar una partida.
     * Cada juego debe implementar su lógica específica de finalización.
     */
    public abstract void finalizarPartida();

    /**
     * Genera un reporte de la partida actual.
     * Este método debe ser implementado por las clases hijas para
     * crear reportes específicos según el tipo de juego.
     * 
     * @return Un reporte con la información de la partida
     */
    public abstract Reporte generarReporte();
}
