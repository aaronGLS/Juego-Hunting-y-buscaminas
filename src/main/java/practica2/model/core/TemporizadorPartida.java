package practica2.model.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementación concreta del temporizador para las partidas.
 * Se encarga de actualizar el contador de tiempo y notificar a los
 * observadores.
 */
public class TemporizadorPartida implements TemporizadorRunnable {

    /**
     * Tiempo transcurrido en segundos.
     */
    private volatile int tiempoTranscurrido;

    /**
     * Indica si el temporizador está activo.
     */
    private final AtomicBoolean activo;

    /**
     * Observadores tipados que serán notificados de los cambios en el tiempo.
     */
    private final List<TemporizadorObservador> observadores;

    /**
     * Crea un nuevo temporizador.
     */
    public TemporizadorPartida() {
        this.tiempoTranscurrido = 0;
        this.activo = new AtomicBoolean(false);
        this.observadores = new CopyOnWriteArrayList<>();
    }

    /**
     * Método principal que ejecuta el temporizador en un hilo.
     */
    @Override
    public void run() {
        while (activo.get()) {
            try {
                Thread.sleep(1000); // Actualizar cada segundo
                tiempoTranscurrido++;
                notificarObservadores();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Inicia el temporizador.
     */
    @Override
    public void iniciar() {
        this.activo.set(true);
        this.tiempoTranscurrido = 0;
    }

    /**
     * Detiene el temporizador.
     */
    @Override
    public void detener() {
        this.activo.set(false);
    }

    /**
     * Pausa el temporizador.
     */
    @Override
    public void pausar() {
        this.activo.set(false);
    }

    /**
     * Reanuda el temporizador.
     */
    @Override
    public void reanudar() {
        this.activo.set(true);
    }

    /**
     * Obtiene el tiempo transcurrido en segundos.
     * 
     * @return Tiempo en segundos
     */
    @Override
    public int getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    /**
     * Verifica si el observador ya está registrado.
     * 
     * @param observador El observador a verificar
     * @return true si ya está registrado, false en caso contrario
     */
    private boolean contieneObservador(TemporizadorObservador observador) {
        if (observador == null) {
            return false;
        }
        return observadores.contains(observador);
    }

    /**
     * Registra un observador para recibir notificaciones.
     * 
     * @param observador El observador a registrar
     */
    @Override
    public void registrarObservador(TemporizadorObservador observador) {
        if (observador != null && !contieneObservador(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador para que deje de recibir notificaciones.
     * 
     * @param observador El observador a eliminar
     */
    @Override
    public void eliminarObservador(TemporizadorObservador observador) {
        if (observador == null) {
            return;
        }
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores registrados sobre la actualización del
     * tiempo.
     */
    private void notificarObservadores() {
        for (TemporizadorObservador observador : observadores) {
            if (observador != null) {
                observador.tiempoActualizado(tiempoTranscurrido);
            }
        }
    }
}
