package practica2.model.hunting;

import practica2.model.core.Jugador;
import practica2.model.core.Partida;
import practica2.model.core.Reporte;
import practica2.model.core.EstadoPartida;
import practica2.model.core.ResultadoPartida;
import practica2.model.core.TemporizadorPartida;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase principal del juego Hunting que extiende Partida.
 * Implementa la lógica del pato, disparos, puntuación y temporizadores.
 * Utiliza hilos para controlar el ciclo de vida del pato.
 */
public class JuegoHunting extends Partida {
    /**
     * Pato que aparece y desaparece en la pantalla.
     */
    private Pato pato;

    /**
     * Configuración del juego establecida por el jugador.
     */
    private ConfiguracionHunting configuracion;

    /**
     * Estado del juego (true = en curso, false = finalizado).
     */
    private boolean juegoEnCurso;

    /**
     * Indica si la partida fue interrumpida por el usuario sin terminarla
     * normalmente.
     */
    private boolean partidaInterrumpida;

    /**
     * Tiempo actual de visibilidad del pato en milisegundos.
     */
    private int tiempoVisibilidadActual;

    /**
     * Contador de aciertos.
     */
    private int aciertos;

    /**
     * Contador de fallos consecutivos.
     */
    private int fallosConsecutivos;

    /**
     * Total de fallos durante la partida.
     */
    private int fallosTotales;

    /**
     * ExecutorService para gestionar el ciclo de vida del pato en un hilo separado.
     */
    private ExecutorService patoExecutor;

    /**
     * Almacena si el último disparo fue un acierto o no.
     */
    private volatile boolean ultimoDisparoFueAcierto;

    /**
     * Puntos ganados por cada acierto.
     */
    private final int PUNTOS_POR_ACIERTO = 10;

    /**
     * Observadores tipados para notificar cambios del modelo.
     */
    private final List<HuntingJuegoObservador> observadores;

    /**
     * Constructor que inicializa el juego con la configuración.
     * 
     * @param jugador       Jugador de la partida
     * @param configuracion Configuración del juego que incluye nombre del jugador
     * @param anchoJuego    Ancho del área de juego en píxeles
     * @param altoJuego     Alto del área de juego en píxeles
     */
    public JuegoHunting(Jugador jugador, ConfiguracionHunting configuracion,
            int anchoJuego, int altoJuego) {
        super(jugador, new TemporizadorPartida());
        this.configuracion = configuracion;
        this.tiempoVisibilidadActual = configuracion.getTiempoVisibilidadInicial();
        this.aciertos = 0;
        this.fallosConsecutivos = 0;
        this.fallosTotales = 0;
        this.ultimoDisparoFueAcierto = false;
        this.juegoEnCurso = false;
        this.partidaInterrumpida = false;

        // Creamos el pato con dimensiones típicas y los límites del área de juego
        this.pato = new Pato(120, 120, anchoJuego, altoJuego);

        this.observadores = new CopyOnWriteArrayList<>();
    }

    /**
     * Inicia la partida activando temporizadores y mostrando el primer pato.
     */
    @Override
    public void iniciarPartida() {
        // Establecer estado inicial
        juegoEnCurso = true;
        partidaInterrumpida = false;

        // Establecer estado y iniciar temporizador
        setEstado(EstadoPartida.EN_CURSO);
        iniciarTemporizador();

        // Iniciar el hilo del pato
        iniciarCicloPato();
    }

    /**
     * Finaliza la partida deteniendo hilos.
     */
    @Override
    public void finalizarPartida() {
        if (juegoEnCurso) {
            juegoEnCurso = false;
            partidaInterrumpida = false;
            detenerTemporizador();
            setEstado(EstadoPartida.FINALIZADA);

            // Detener el hilo del pato
            detenerCicloPato();

            // Ocultar el pato
            pato.ocultar();

            // Notificar a los observadores
            notificarObservadores();
        }
    }

    /**
     * Interrumpe la partida sin generar reporte.
     * Este método se usa cuando el jugador decide terminar la partida
     * voluntariamente antes de completarla.
     */
    public void interrumpirPartida() {
        if (juegoEnCurso) {
            juegoEnCurso = false;
            partidaInterrumpida = true;
            detenerTemporizador();
            setEstado(EstadoPartida.INTERRUMPIDA);

            // Detener el hilo del pato
            detenerCicloPato();

            // Ocultar el pato
            pato.ocultar();

            // Notificar a los observadores
            notificarObservadores();
        }
    }

    /**
     * Inicializa y arranca el ciclo de vida del pato usando un ExecutorService.
     */
    private void iniciarCicloPato() {
        // Asegurarse de que cualquier ejecutor anterior esté detenido.
        if (patoExecutor != null && !patoExecutor.isShutdown()) {
            patoExecutor.shutdownNow();
        }
        // Crear un nuevo ejecutor para la partida y enviar la tarea del pato.
        patoExecutor = Executors.newSingleThreadExecutor();
        patoExecutor.submit(new PatoRunnable(this));
    }

    /**
     * Detiene el ciclo de vida del pato de forma segura.
     */
    private void detenerCicloPato() {
        if (patoExecutor != null) {
            patoExecutor.shutdownNow(); // Método seguro para detener las tareas del ejecutor.
        }
    }

    /**
     * Muestra el pato en una posición aleatoria.
     * Este método es llamado desde PatoRunnable.
     */
    public synchronized void mostrarPato() {
        // Posicionar el pato aleatoriamente
        pato.moverAleatorio();

        // Hacerlo visible
        pato.mostrar();

        // Notificar cambio
        notificarObservadores();
    }

    /**
     * Oculta el pato y registra fallo si estaba visible.
     * Este método es llamado desde PatoRunnable.
     */
    public synchronized void ocultarPato() {
        if (pato.isVisible()) {
            pato.ocultar();
            notificarObservadores();
        }
    }

    /**
     * Procesa un disparo en las coordenadas especificadas.
     * Se sincroniza para evitar problemas con el hilo del pato.
     * 
     * @param x Coordenada X del disparo
     * @param y Coordenada Y del disparo
     * @return true si fue un acierto, false si fue un fallo
     */
    public synchronized boolean procesarDisparo(int x, int y) {
        if (super.getEstadoPartida() != EstadoPartida.EN_CURSO) {
            ultimoDisparoFueAcierto = false;
            return false;
        }

        // Primero, verificar si el clic está dentro de las coordenadas del pato
        if (pato.contieneClick(x, y)) {
            // Segundo, intentar el acierto de forma atómica
            if (pato.intentarAcierto()) {
                // Acierto exitoso
                registrarAcierto();
                notificarObservadores();
                ultimoDisparoFueAcierto = true;
                return true;
            }
        }

        // Si llegamos aquí, es un fallo (clic fuera o el pato ya no estaba visible)
        registrarFallo();
        notificarObservadores();
        ultimoDisparoFueAcierto = false;
        return false;
    }

    /**
     * Registra un acierto y actualiza contadores y puntuación.
     */
    private void registrarAcierto() {
        aciertos++;
        fallosConsecutivos = 0; // Se reinicia el contador de fallos consecutivos
        jugador.incrementarPuntaje(PUNTOS_POR_ACIERTO);

        // Verificar si se debe reducir el tiempo de visibilidad
        if (aciertos % configuracion.getUmbralAciertos() == 0) {
            reducirTiempoVisibilidad();
        }
    }

    /**
     * Registra un fallo y verifica si se debe finalizar la partida.
     */
    private void registrarFallo() {
        fallosConsecutivos++;
        fallosTotales++;

        // Verificar si se alcanzó el máximo de fallos consecutivos
        if (fallosConsecutivos >= ConfiguracionHunting.FALLOS_MAXIMOS) {
            finalizarPartida();
        }
    }

    /**
     * Reduce el tiempo de visibilidad del pato según la configuración.
     */
    private void reducirTiempoVisibilidad() {
        tiempoVisibilidadActual -= configuracion.getReduccionTiempo();

        // Establecer un mínimo de tiempo para que el juego siga siendo jugable
        if (tiempoVisibilidadActual < 200) {
            tiempoVisibilidadActual = 200; // Mínimo 200ms
        }

        // No es necesario reiniciar el hilo, ya que PatoRunnable
        // consulta el tiempo actualizado en cada ciclo
    }

    /**
     * Registra un observador para recibir notificaciones de cambios del juego.
     *
     * @param observador Observador que será notificado de los cambios
     */
    public void registrarObservador(HuntingJuegoObservador observador) {
        if (observador == null) {
            return;
        }
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador para que deje de recibir notificaciones.
     */
    public void eliminarObservador(HuntingJuegoObservador observador) {
        if (observador == null) {
            return;
        }
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores registrados sobre cambios en el juego.
     */
    private void notificarObservadores() {
        for (HuntingJuegoObservador observador : observadores) {
            if (observador == null) {
                continue;
            }
            try {
                observador.actualizar(this);
            } catch (Exception e) {
                System.err.println("Error al notificar observador: " + e.getMessage());
            }
        }
    }

    /**
     * Genera un reporte de la partida finalizada.
     * 
     * @return Reporte con datos de la partida o null si la partida fue interrumpida
     */
    @Override
    public Reporte generarReporte() {

        try {
            // Si el juego aún está en curso, finalizarlo para el reporte
            if (juegoEnCurso) {
                finalizarPartida();
            }

            // Si la partida fue interrumpida, no se genera reporte
            if (partidaInterrumpida) {
                return null;
            }

            ResultadoPartida resultado = (fallosConsecutivos >= ConfiguracionHunting.FALLOS_MAXIMOS)
                    ? ResultadoPartida.PERDIDA
                    : ResultadoPartida.GANADA;

            ReporteHunting reporte = new ReporteHunting(
                    jugador,
                    calcularDuracion(),
                    resultado,
                    aciertos,
                    fallosTotales,
                    configuracion.toString());

            return reporte;
        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene el resultado del último disparo procesado.
     * 
     * @return true si el último disparo fue un acierto, false en caso contrario
     */
    public synchronized boolean fueUltimoDisparoAcierto() {
        return ultimoDisparoFueAcierto;
    }

    public Pato getPato() {
        return pato;
    }

    public int getAciertos() {
        return aciertos;
    }

    public int getFallosConsecutivos() {
        return fallosConsecutivos;
    }

    public int getFallosTotales() {
        return fallosTotales;
    }

    public int getTiempoVisibilidadActual() {
        return tiempoVisibilidadActual;
    }

    public ConfiguracionHunting getConfiguracion() {
        return configuracion;
    }

}
