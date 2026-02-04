package practica2.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import practica2.model.core.EstadoPartida;
import practica2.model.core.Jugador;
import practica2.model.core.Reporte;
import practica2.model.core.TemporizadorObservador;
import practica2.model.excepciones.ExcepcionJuego;
import practica2.model.hunting.ConfiguracionHunting;
import practica2.model.hunting.HuntingJuegoObservador;
import practica2.model.hunting.JuegoHunting;
import practica2.model.hunting.ReporteHunting;
import practica2.model.persistencia.ExcepcionPersistencia;
import practica2.model.persistencia.ReporteHuntingRegistro;
import practica2.model.persistencia.RepositorioReportes;
import practica2.model.validacion.Validador;
import practica2.model.validacion.ValidadorConfiguracionHunting;
import practica2.controller.comando.ComandoActualizarReporte;
import practica2.controller.comando.ComandoDisparar;
import practica2.controller.comando.ComandoIniciarHunting;

/**
 * Controlador para el juego Hunting.
 * Gestiona la lógica de negocio entre la vista y el modelo.
 */
public class ControladorHunting implements HuntingJuegoObservador, TemporizadorObservador {

    private static final Logger LOG = Logger.getLogger(ControladorHunting.class.getName());

    private String ultimoErrorReportes;

    /**
     * Juego actual en ejecución.
     */
    private JuegoHunting juegoActual;

    /**
     * Sistema de persistencia para guardar reportes.
     */
    private RepositorioReportes persistencia;

    private final Validador<ConfiguracionHunting> validadorConfiguracion;

    /**
     * Observadores que serán notificados cuando haya cambios.
     */
    private final List<HuntingObservador> observadores;

    /**
     * Ancho predeterminado del área de juego.
     */
    private int anchoJuego;

    /**
     * Alto predeterminado del área de juego.
     */
    private int altoJuego;

    /**
     * Constructor del controlador.
     * 
     * @param persistencia Sistema para guardar reportes
     * @param anchoJuego   Ancho del área de juego
     * @param altoJuego    Alto del área de juego
     */
    public ControladorHunting(RepositorioReportes persistencia, int anchoJuego, int altoJuego) {
        this.persistencia = persistencia;
        this.anchoJuego = anchoJuego;
        this.altoJuego = altoJuego;
        this.validadorConfiguracion = new ValidadorConfiguracionHunting();

        this.observadores = new CopyOnWriteArrayList<>();
        this.ultimoErrorReportes = null;
    }

    public String getUltimoErrorReportes() {
        return ultimoErrorReportes;
    }

    public void limpiarUltimoErrorReportes() {
        ultimoErrorReportes = null;
    }

    /**
     * Valida la configuración del juego.
     * Comprueba que los parámetros sean válidos según las reglas del juego.
     * 
     * @param configuracion Configuración a validar
     * @return Mensaje de error si hay problemas, null si todo está correcto
     */
    public String validarConfiguracion(ConfiguracionHunting configuracion) {
        return validadorConfiguracion.validar(configuracion);
    }

    /**
     * Valida la configuración e inicia el juego si es válida.
     * Centraliza la validación y el feedback al usuario.
     * 
     * @param configuracion La configuración a validar
     * @return true si el juego se inició con éxito
     */
    public boolean iniciarJuegoValidado(ConfiguracionHunting configuracion) {
        String mensajeError = validarConfiguracion(configuracion);
        if (mensajeError != null) {
            return false;
        }

        return iniciarJuegoConComando(configuracion);
    }

    /**
     * Inicia el juego y retorna null si fue exitoso, o un mensaje de error si no.
     * Preferir este método en la UI para decidir cómo mostrar el error.
     */
    public String iniciarJuegoConValidacion(ConfiguracionHunting configuracion) {
        String mensajeError = validarConfiguracion(configuracion);
        if (mensajeError != null) {
            return mensajeError;
        }

        return iniciarJuegoConComando(configuracion) ? null : "No se pudo iniciar el juego";
    }

    /**
     * Inicia un nuevo juego usando el patrón Command.
     * 
     * @param configuracion Configuración para el nuevo juego
     * @return true si el juego se inicializó correctamente
     */
    private boolean iniciarJuegoConComando(ConfiguracionHunting configuracion) {
        try {
            // Usar el comando para crear e iniciar el juego
            ComandoIniciarHunting comando = new ComandoIniciarHunting(configuracion, this);
            comando.ejecutar();
            return true;
        } catch (ExcepcionJuego e) {
            System.err.println("Error al iniciar el juego: " + e.getMensajeCompleto());
            return false;
        }
    }

    /**
     * Inicia un nuevo juego con la configuración proporcionada.
     * Este método es llamado por ComandoIniciarHunting.
     * 
     * @param configuracion Configuración del juego
     * @return El juego creado
     */
    public JuegoHunting iniciarJuego(ConfiguracionHunting configuracion) {
        // Crear jugador
        Jugador jugador = new Jugador(configuracion.getNombreJugador());

        // Crear e iniciar juego
        juegoActual = new JuegoHunting(jugador, configuracion, anchoJuego, altoJuego);
        juegoActual.registrarObservador(this);

        // Registrar el controlador como observador del temporizador para recibir
        // actualizaciones de tiempo
        juegoActual.registrarObservadorTemporizador(this);

        juegoActual.iniciarPartida();

        return juegoActual;
    }

    /**
     * Procesa un disparo en las coordenadas especificadas usando el comando
     * correspondiente.
     * 
     * @param x Coordenada X del disparo
     * @param y Coordenada Y del disparo
     * @return true si fue acierto, false si fue fallo
     */
    public boolean procesarDisparo(int x, int y) {
        if (juegoActual != null && juegoActual.getEstadoPartida() == EstadoPartida.EN_CURSO) {
            try {
                // Crear y ejecutar el comando de disparo siguiendo el patrón Command
                ComandoDisparar comando = new ComandoDisparar(this, x, y);
                comando.ejecutar();
                notificarObservadores(); // Notificar a los observadores del disparo

                // Consultar el resultado almacenado en el modelo
                return juegoActual.fueUltimoDisparoAcierto();
            } catch (ExcepcionJuego e) {
                // Manejar la excepción 
                LOG.log(Level.WARNING, "Error al procesar disparo: " + e.getMensajeCompleto(), e);
            }
        }
        return false; // Fallo o error
    }

    /**
     * Finaliza el juego actual y guarda el reporte.
     */
    public ReporteHunting finalizarJuego() {
        if (juegoActual == null) {
            LOG.fine("finalizarJuego(): no hay juego actual");
            return null;
        }

        juegoActual.finalizarPartida();

        Reporte reporte = juegoActual.generarReporte();
        LOG.fine("finalizarJuego(): reporte generado: " + (reporte != null));

        // Si hay reporte, guardarlo en la persistencia
        if (reporte != null) {
            ComandoActualizarReporte comandoReporte = new ComandoActualizarReporte(persistencia, reporte);
            try {
                ultimoErrorReportes = null;
                comandoReporte.ejecutar();
            } catch (ExcepcionJuego e) {
                ultimoErrorReportes = e.getMensajeCompleto();
                LOG.log(Level.WARNING, "Error al actualizar reporte: " + e.getMessage(), e);
            }
            return (ReporteHunting) reporte;
        }
        return null; // No se generó reporte o el juego no estaba en curso
    }

    /**
     * Cancela el juego sin guardar reporte.
     */
    public void cancelarJuego() {
        if (juegoActual != null && juegoActual.getEstadoPartida() == EstadoPartida.EN_CURSO) {
            // Eliminar el controlador como observador del temporizador antes de cancelar
            juegoActual.eliminarObservadorTemporizador(this);

            juegoActual.interrumpirPartida();

        }
    }

    /**
     * Registra un observador para ser notificado de cambios.
     * Método sincronizado para evitar condiciones de carrera.
     * 
     * @param observador El observador a registrar
     */
    public void registrarObservador(HuntingObservador observador) {
        if (observador == null) {
            return;
        }
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void eliminarObservador(HuntingObservador observador) {
        if (observador == null) {
            return;
        }
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores registrados sobre cambios en el
     * controlador. Método sincronizado para evitar condiciones de carrera.
     * Utiliza SwingUtilities.invokeLater para asegurar que las actualizaciones
     * de la UI se realicen en el Event Dispatch Thread (EDT).
     */
    private void notificarObservadores() {
        Runnable notificacion = () -> {
            for (HuntingObservador observador : observadores) {
                if (observador == null) {
                    continue;
                }
                try {
                    observador.actualizar(this);
                } catch (Exception e) {
                    System.err.println("Error al notificar observador: " + e.getMessage());
                }
            }
        };

        UiNotifier.runOnEdt(notificacion);
    }

    /**
     * Implementación del método de la interfaz HuntingJuegoObservador.
     * Se llama cuando el juego notifica cambios.
     * 
     * @param juego El juego que ha cambiado
     */
    @Override
    public void actualizar(JuegoHunting juego) {
        // Notificar a los observadores del controlador
        notificarObservadores();
    }

    /**
     * Implementación del método de la interfaz TemporizadorObservador.
     * Se llama cuando el temporizador notifica un cambio en el tiempo.
     * 
     * @param segundos El tiempo transcurrido en segundos
     */
    @Override
    public void tiempoActualizado(int segundos) {
        // Notificar a los observadores del controlador para actualizar la UI del tiempo
        notificarObservadores();
    }

    /**
     * Obtiene reportes de Hunting desde el repositorio.
     *
     * Nota: el formato para tablas (Object[][]) pertenece a la vista.
     */
    public List<ReporteHuntingRegistro> obtenerReportesHunting() {
        if (persistencia == null) {
            return List.of();
        }
        try {
            return persistencia.obtenerReportesHunting();
        } catch (ExcepcionPersistencia e) {
            ultimoErrorReportes = e.getMessage();
            return List.of();
        }
    }

    /**
     * Obtiene el juego actual.
     * 
     * @return El juego actual
     */
    public JuegoHunting getJuegoActual() {
        return juegoActual;
    }

    public boolean isJuegoEnCurso() {
        return juegoActual != null && juegoActual.getEstadoPartida() == EstadoPartida.EN_CURSO;
    }

    public boolean isPatoVisible() {
        return juegoActual != null && juegoActual.getPato().isVisible();
    }

    public int[] getPosicionPato() {
        if (juegoActual != null) {
            return new int[] {
                    juegoActual.getPato().getPosicionX(),
                    juegoActual.getPato().getPosicionY()
            };
        }
        return new int[] { 0, 0 };
    }

    public String getNombreJugador() {
        return juegoActual != null ? juegoActual.getJugador().getNombre() : null;
    }

    public int getAciertos() {
        return juegoActual != null ? juegoActual.getAciertos() : 0;
    }

    public int getFallosConsecutivos() {
        return juegoActual != null ? juegoActual.getFallosConsecutivos() : 0;
    }

    public int getTiempoTranscurrido() {
        return juegoActual != null ? juegoActual.getTiempoTranscurrido() : 0;
    }
}
