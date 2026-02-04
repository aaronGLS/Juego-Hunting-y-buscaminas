package practica2.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import practica2.controller.comando.*;
import practica2.model.buscaminas.*;
import practica2.model.core.EstadoPartida;
import practica2.model.core.*;
import practica2.model.excepciones.ExcepcionJuego;
import practica2.model.persistencia.ExcepcionPersistencia;
import practica2.model.persistencia.ReporteBuscaminasRegistro;
import practica2.model.persistencia.RepositorioReportes;
import practica2.model.validacion.Validador;
import practica2.model.validacion.ValidadorConfiguracionBuscaminas;

/**
 * Controlador para el juego Buscaminas. Coordina la interacción entre la vista
 * y el modelo, siguiendo el patrón MVC. Implementa TemporizadorObservador para
 * recibir
 * notificaciones del temporizador.
 */
public class ControladorBuscaminas implements TemporizadorObservador {

    /**
     * Referencia al juego actual de Buscaminas.
     */
    private JuegoBuscaminas juegoActual;

    /**
     * Observadores tipados que serán notificados de cambios en el juego.
     */
    private final List<BuscaminasObservador> observadores;

    /**
     * Instancia de persistencia para guardar y cargar reportes.
     */
    private RepositorioReportes persistencia;

    private final Validador<ConfiguracionBuscaminas> validadorConfiguracion;

    private String ultimoErrorReportes;

    /**
     * Constructor que inicializa el controlador.
     * 
     * @param persistencia Sistema para guardar reportes
     */
    public ControladorBuscaminas(RepositorioReportes persistencia) {
        this.observadores = new CopyOnWriteArrayList<>();
        this.persistencia = persistencia;
        this.validadorConfiguracion = new ValidadorConfiguracionBuscaminas();
        this.ultimoErrorReportes = null;
    }

    public String getUltimoErrorReportes() {
        return ultimoErrorReportes;
    }

    public void limpiarUltimoErrorReportes() {
        ultimoErrorReportes = null;
    }

    /**
     * Inicia una nueva partida de Buscaminas con la configuración especificada.
     * 
     * @param configuracion Configuración inicial del juego
     * @return true si el juego se inicializó correctamente
     */
    public boolean iniciarJuego(ConfiguracionBuscaminas configuracion) {
        try {
            // Limpiar explícitamente cualquier juego anterior
            if (juegoActual != null) {
                // Desvincular el controlador como observador del temporizador del juego
                // anterior
                juegoActual.eliminarObservadorTemporizador(this);
                // Eliminar la referencia
                juegoActual = null;
            }

            // Validar la configuración antes de iniciar el juego
            String mensajeError = validarConfiguracion(configuracion);
            if (mensajeError != null) {
                System.err.println("Error en la configuración: " + mensajeError);
                return false;
            }

            // Usar el comando existente para crear el juego
            ComandoIniciarJuego comando = new ComandoIniciarJuego(configuracion);
            comando.ejecutar();
            juegoActual = comando.getJuego();

            // Registrar el controlador como observador del temporizador
            juegoActual.registrarObservadorTemporizador(this);

            // Notificar a todos los observadores de la vista
            notificarObservadores();

            return true;
        } catch (Exception e) {
            System.err.println("Error al iniciar el juego: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida la configuración e inicia el juego si es válida.
     * Centraliza la validación y el feedback al usuario.
     * 
     * @param configuracion La configuración a validar
     * @return true si el juego se inició con éxito
     */
    public boolean iniciarJuegoValidado(ConfiguracionBuscaminas configuracion) {
        String mensajeError = validarConfiguracion(configuracion);
        if (mensajeError != null) {
            return false;
        }

        return iniciarJuego(configuracion);
    }

    /**
     * Inicia el juego y retorna null si fue exitoso, o un mensaje de error si no.
     * Preferir este método en la UI para decidir cómo mostrar el error.
     */
    public String iniciarJuegoConValidacion(ConfiguracionBuscaminas configuracion) {
        String mensajeError = validarConfiguracion(configuracion);
        if (mensajeError != null) {
            return mensajeError;
        }

        return iniciarJuego(configuracion) ? null : "No se pudo iniciar el juego";
    }

    /**
     * Valida la configuración del juego.
     * Comprueba que los parámetros sean válidos según las reglas del juego.
     * 
     * @param configuracion Configuración a validar
     * @return Mensaje de error si hay problemas, null si todo está correcto
     */
    public String validarConfiguracion(ConfiguracionBuscaminas configuracion) {
        return validadorConfiguracion.validar(configuracion);
    }

    /**
     * Ejecuta el comando para descubrir una casilla en las coordenadas
     * especificadas.
     * 
     * @param fila    Fila de la casilla a descubrir
     * @param columna Columna de la casilla a descubrir
     * @return true si la operación fue exitosa, false si hubo error o la casilla ya
     *         estaba descubierta
     */
    public boolean descubrirCasilla(int fila, int columna) {
        if (juegoActual == null) {
            return false;
        }

        // Bloquear acciones si el juego no está en curso (incluye "Pausada")
        if (juegoActual.getEstadoPartida() != EstadoPartida.EN_CURSO || juegoActual.esJuegoFinalizado()) {
            return false;
        }

        ComandoDescubrirCasilla comando = new ComandoDescubrirCasilla(juegoActual, fila, columna);
        try {
            comando.ejecutar();
            notificarObservadores();
            return true;
        } catch (ExcepcionJuego e) {
            // Si hay una excepción al descubrir una mina, notificar a observadores
            // para que puedan mostrar que el juego terminó
            juegoActual.descubrirTodasLasMinas();
            notificarObservadores();
            return false;
        }
    }

    /**
     * Ejecuta el comando para marcar o desmarcar una casilla.
     * 
     * @param fila    Fila de la casilla a marcar
     * @param columna Columna de la casilla a marcar
     * @return true si la operación fue exitosa
     */
    public boolean marcarCasilla(int fila, int columna) {
        if (juegoActual == null) {
            return false;
        }

        // Bloquear acciones si el juego no está en curso (incluye "Pausada")
        if (juegoActual.getEstadoPartida() != EstadoPartida.EN_CURSO || juegoActual.esJuegoFinalizado()) {
            return false;
        }

        ComandoMarcarCasilla comando = new ComandoMarcarCasilla(juegoActual, fila, columna);
        try {
            comando.ejecutar();
            notificarObservadores();
            return true;
        } catch (ExcepcionJuego e) {
            return false;
        }
    }

    /**
     * Finaliza la partida actual generando un reporte.
     * 
     * @return El reporte generado, o null si no hay juego en curso o fue
     *         interrumpido
     */
    public ReporteBuscaminas finalizarPartida() {
        if (juegoActual == null) {
            return null;
        }

        // Finalizar la partida
        juegoActual.finalizarPartida();

        // Descubrir todas las minas para mostrarlas en la vista
        juegoActual.descubrirTodasLasMinas();

        // Generar reporte
        Reporte reporte = juegoActual.generarReporte();
        notificarObservadores();

        // Si hay reporte, guardarlo en la persistencia
        if (reporte != null) {
            ComandoActualizarReporte comandoReporte = new ComandoActualizarReporte(persistencia, reporte);
            try {
                ultimoErrorReportes = null;
                comandoReporte.ejecutar();
            } catch (ExcepcionJuego e) {
                ultimoErrorReportes = e.getMensajeCompleto();
            }
            return (ReporteBuscaminas) reporte;
        }

        return null;
    }

    /**
     * Interrumpe la partida actual sin generar reporte.
     */
    public void interrumpirPartida() {
        if (juegoActual != null) {
            juegoActual.interrumpirPartida();
            notificarObservadores();
        }
    }

    /**
     * Pausa el temporizador del juego actual
     */
    public void pausarTemporizador() {
        if (juegoActual != null) {
            juegoActual.pausarPartida();
        }
    }

    /**
     * Reanuda el temporizador del juego actual
     */
    public void reanudarTemporizador() {
        if (juegoActual != null) {
            juegoActual.reanudarPartida();
        }
    }

    /**
     * Obtiene el tablero actual del juego.
     * 
     * @return Tablero del juego en curso, o null si no hay juego
     */
    public Tablero getTablero() {
        return juegoActual != null ? juegoActual.getTablero() : null;
    }

    /**
     * Obtiene la configuración del juego actual.
     * 
     * @return Configuración del juego en curso, o null si no hay juego
     */
    public ConfiguracionBuscaminas getConfiguracion() {
        return juegoActual != null ? juegoActual.getConfiguracion() : null;
    }

    /**
     * Verifica si el juego ha terminado.
     * 
     * @return true si el juego ha terminado, false si sigue en curso
     */
    public boolean esJuegoFinalizado() {
        return juegoActual != null && juegoActual.esJuegoFinalizado();
    }

    /**
     * Verifica si la partida fue ganada.
     * 
     * @return true si la partida fue ganada, false si fue perdida, null si está en
     *         curso
     */
    public Boolean esPartidaGanada() {
        return juegoActual != null ? juegoActual.esPartidaGanada() : null;
    }

    /**
     * Obtiene el tiempo transcurrido de la partida en milisegundos.
     * 
     * @return Tiempo transcurrido en milisegundos
     */
    public long getTiempoTranscurrido() {
        return juegoActual != null ? juegoActual.calcularDuracion() : 0;
    }

    /**
     * Obtiene el tiempo transcurrido formateado como texto (minutos:segundos).
     * 
     * @return Cadena con el tiempo formateado
     */
    public String getTiempoFormateado() {
        long milisegundos = getTiempoTranscurrido();
        long segundos = milisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;

        return String.format("%02d:%02d", minutos, segundos);
    }

    /**
     * Obtiene el número de casillas descubiertas.
     * 
     * @return Cantidad de casillas descubiertas
     */
    public int getCasillasDescubiertas() {
        return juegoActual != null ? juegoActual.getCasillasDescubiertas() : 0;
    }

    /**
     * Obtiene el número de minas en el tablero.
     * 
     * @return Cantidad de minas
     */
    public int getCantidadMinas() {
        return juegoActual != null && juegoActual.getTablero() != null ? juegoActual.getTablero().contarMinas() : 0;
    }

    /**
     * Obtiene el número de casillas marcadas.
     * 
     * @return Cantidad de casillas marcadas
     */
    public int getCantidadMarcadas() {
        return juegoActual != null && juegoActual.getTablero() != null
                ? juegoActual.getTablero().contarCasillasMarcadas()
                : 0;
    }

    /**
     * Obtiene el número de minas restantes por marcar.
     * 
     * @return Número de minas menos número de casillas marcadas
     */
    public int getMinasRestantes() {
        return getCantidadMinas() - getCantidadMarcadas();
    }

    /**
     * Añade un observador que será notificado de cambios en el juego.
     * 
     * @param observador Observador a añadir
     */
    public void addObservador(BuscaminasObservador observador) {
        if (observador == null) {
            return;
        }
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador de la lista.
     * 
     * @param observador Observador a eliminar
     */
    public void removeObservador(BuscaminasObservador observador) {
        if (observador == null) {
            return;
        }
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores de que ha habido un cambio en el juego.
     */
    private void notificarObservadores() {
        Runnable notificacion = () -> {
            for (BuscaminasObservador observador : observadores) {
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
     * Implementación del método de la interfaz TemporizadorObservador.
     * Se ejecuta cuando el temporizador notifica un cambio en el tiempo.
     * 
     * @param segundos El tiempo transcurrido en segundos
     */
    @Override
    public void tiempoActualizado(int segundos) {
        // Cuando el temporizador envía una actualización, notificar a los observadores
        notificarObservadores();
    }

    /**
     * Guarda el reporte de una partida finalizada de Buscaminas.
     * 
     * @param reporte El reporte de la partida
     */
    public void guardarReporte(ReporteBuscaminas reporte) {
        try {
            persistencia.guardarReporteBuscaminas(reporte);
        } catch (ExcepcionPersistencia e) {
            ultimoErrorReportes = e.getMessage();
        }
    }

    /**
     * Obtiene reportes de Buscaminas ganados desde el repositorio.
     *
     * Nota: el formato para tablas (Object[][]) pertenece a la vista.
     */
    public List<ReporteBuscaminasRegistro> obtenerReportesGanados() {
        try {
            return persistencia.obtenerReportesBuscaminasGanadas();
        } catch (ExcepcionPersistencia e) {
            ultimoErrorReportes = e.getMessage();
            return List.of();
        }
    }

    /**
     * Obtiene reportes de Buscaminas perdidos desde el repositorio.
     *
     * Nota: el formato para tablas (Object[][]) pertenece a la vista.
     */
    public List<ReporteBuscaminasRegistro> obtenerReportesPerdidos() {
        try {
            return persistencia.obtenerReportesBuscaminasPerdidas();
        } catch (ExcepcionPersistencia e) {
            ultimoErrorReportes = e.getMessage();
            return List.of();
        }
    }

    public JuegoBuscaminas getJuegoActual() {

        return juegoActual;
    }
}
