package practica2.model.buscaminas;

import practica2.model.core.Jugador;
import practica2.model.core.Partida;
import practica2.model.core.Reporte;
import practica2.model.core.EstadoPartida;
import practica2.model.core.TemporizadorPartida;
import practica2.model.core.ResultadoPartida;
import practica2.model.excepciones.ExcepcionJuego;

/**
 * Contiene la lógica central del juego Buscaminas.
 * Gestiona la inicialización del tablero, la distribución de minas,
 * el efecto dominó y la validación de cada jugada.
 */
public class JuegoBuscaminas extends Partida {

    /**
     * Tablero asociado a la partida actual.
     */
    private Tablero tablero;

    /**
     * Configuración de la partida actual.
     */
    private ConfiguracionBuscaminas configuracion;

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
     * Contador de casillas descubiertas sin mina.
     */
    private int casillasDescubiertas;

    /**
     * Número total de casillas seguras (sin mina) en el tablero.
     */
    private int casillasSinMina;

    /**
     * Resultado de la partida (true = ganada, false = perdida, null = en curso).
     */
    private Boolean partidaGanada;

    /**
     * Bandera para controlar el primer movimiento y asegurar que no sea una mina.
     */
    private boolean esPrimerMovimiento;

    /**
     * Constructor que inicializa una partida con la configuración especificada.
     * 
     * @param configuracion Configuración de la partida
     */
    public JuegoBuscaminas(ConfiguracionBuscaminas configuracion) {
        super(new Jugador(configuracion.getNombreJugador()), new TemporizadorPartida());
        this.configuracion = configuracion;
        this.juegoEnCurso = false;
        this.partidaInterrumpida = false;
        this.casillasDescubiertas = 0;
        this.partidaGanada = null;
        this.esPrimerMovimiento = true;
    }

    /**
     * Inicializa la partida: crea el tablero, distribuye las minas y establece el
     * estado inicial.
     */
    @Override
    public void iniciarPartida() {
        // Crear y preparar el tablero según la configuración
        if (configuracion.esTableroCuadrado()) {
            tablero = new Tablero(configuracion.getTamañoTablero(), configuracion.getTamañoTablero());
        } else {
            tablero = new Tablero(configuracion.getFilas(), configuracion.getColumnas());
        }

        // Inicializar el tablero con las minas
        tablero.inicializarTablero(configuracion.getCantidadMinas());

        // Calcular el número total de casillas sin mina
        casillasSinMina = (tablero.getFilas() * tablero.getColumnas()) - tablero.contarMinas();

        // Establecer estado inicial
        juegoEnCurso = true;
        partidaInterrumpida = false;
        casillasDescubiertas = 0;
        partidaGanada = null;
        this.esPrimerMovimiento = true;

        // Iniciar el temporizador
        iniciarTemporizador();

        setEstado(EstadoPartida.EN_CURSO);
    }

    /**
     * Descubre una casilla en la posición especificada.
     * Si la casilla no tiene minas adyacentes, activa el efecto dominó.
     * 
     * @param fila    Fila de la casilla a descubrir
     * @param columna Columna de la casilla a descubrir
     * @return true si la jugada es válida, false si la casilla ya estaba
     *         descubierta o marcada
     * @throws ExcepcionJuego Si se descubre una mina (partida perdida) o si el
     *                        juego no está en curso
     */
    public boolean descubrirCasilla(int fila, int columna) throws ExcepcionJuego {
        // Verificar que el juego esté en curso
        if (!juegoEnCurso) {
            throw new ExcepcionJuego("El juego no está en curso", ExcepcionJuego.ERROR_JUEGO_FINALIZADO);
        }

        // Validar las coordenadas
        if (!tablero.esCoordenadaValida(fila, columna)) {
            throw new ExcepcionJuego("Coordenadas fuera de los límites del tablero",
                    ExcepcionJuego.ERROR_MOVIMIENTO_INVALIDO);
        }

        // Garantizar que el primer movimiento no sea una mina
        if (esPrimerMovimiento) {
            if (tablero.getCasilla(fila, columna).contieneMina()) {
                tablero.recolocarMina(fila, columna);
            }
            esPrimerMovimiento = false;
        }

        Casilla casilla = tablero.getCasilla(fila, columna);

        // Verificar si la casilla ya está descubierta o marcada
        if (!casilla.estaCubierta() || casilla.estaMarcada()) {
            return false;
        }

        // Descubrir la casilla
        boolean hayMina = casilla.descubrir();

        // Si hay mina, el juego termina
        if (hayMina) {
            finalizarPartida();
            partidaGanada = false;
            throw new ExcepcionJuego("¡Has descubierto una mina! Juego terminado.", ExcepcionJuego.ERROR_GENERAL);
        }

        // Actualizar contador de casillas descubiertas
        casillasDescubiertas++;

        // Si la casilla no tiene minas adyacentes, aplicar efecto dominó
        if (casilla.getNumeroMinasAdyacentes() == 0) {
            casillasDescubiertas += tablero.aplicarEfectoDomino(fila, columna);
        }

        // Verificar si se han descubierto todas las casillas seguras
        if (casillasDescubiertas >= casillasSinMina) {
            finalizarPartida();
            partidaGanada = true;
        }

        return true;
    }

    /**
     * Marca o desmarca una casilla como potencial ubicación de una mina.
     * 
     * @param fila    Fila de la casilla a marcar
     * @param columna Columna de la casilla a marcar
     * @return true si la operación fue exitosa, false si la casilla ya estaba
     *         descubierta
     * @throws ExcepcionJuego Si el juego no está en curso o las coordenadas son
     *                        inválidas
     */
    public boolean marcarCasilla(int fila, int columna) throws ExcepcionJuego {
        // Verificar que el juego esté en curso
        if (!juegoEnCurso) {
            throw new ExcepcionJuego("El juego no está en curso", ExcepcionJuego.ERROR_JUEGO_FINALIZADO);
        }

        // Validar las coordenadas
        if (!tablero.esCoordenadaValida(fila, columna)) {
            throw new ExcepcionJuego("Coordenadas fuera de los límites del tablero",
                    ExcepcionJuego.ERROR_MOVIMIENTO_INVALIDO);
        }

        // Marcar la casilla
        return tablero.getCasilla(fila, columna).marcar();
    }

    /**
     * Finaliza la partida normalmente y registra el tiempo.
     * Este método se usa cuando el juego termina por victoria o derrota.
     */
    @Override
    public void finalizarPartida() {
        // Permitir finalizar aunque esté pausada (juegoEnCurso=false pero la partida no ha terminado "de verdad")
        if (juegoEnCurso || getEstadoPartida() == EstadoPartida.PAUSADA) {
            juegoEnCurso = false;
            partidaInterrumpida = false;
            detenerTemporizador();
            setEstado(EstadoPartida.FINALIZADA);
        }
    }

    /**
     * Interrumpe la partida sin registrar resultado.
     * Este método se usa cuando el jugador decide terminar la partida
     * voluntariamente antes de completarla.
     */
    public void interrumpirPartida() {
        // Permitir interrumpir aunque esté pausada
        if (juegoEnCurso || getEstadoPartida() == EstadoPartida.PAUSADA) {
            juegoEnCurso = false;
            partidaInterrumpida = true;
            detenerTemporizador();
            setEstado(EstadoPartida.INTERRUMPIDA);
        }
    }

    public void pausarPartida() {
        if (juegoEnCurso) {
            juegoEnCurso = false;
            pausarTemporizador();
            setEstado(EstadoPartida.PAUSADA);
        }
    }

    /**
     * Reanuda la partida desde donde se pausó.
     */
    public void reanudarPartida() {
        if (!juegoEnCurso && getEstadoPartida() == EstadoPartida.PAUSADA) {
            juegoEnCurso = true;
            partidaInterrumpida = false;
            reanudarTemporizador();
            setEstado(EstadoPartida.EN_CURSO);
        }
    }

    /**
     * Verifica si el juego ha finalizado.
     * 
     * @return true si el juego ha finalizado, false si sigue en curso
     */
    public boolean esJuegoFinalizado() {
        // "Pausada" no se considera finalizada; solo Finalizada/Interrumpida.
        EstadoPartida estadoActual = getEstadoPartida();
        return estadoActual == EstadoPartida.FINALIZADA || estadoActual == EstadoPartida.INTERRUMPIDA;
    }

    /**
     * Verifica si la partida fue interrumpida sin completarse.
     * 
     * @return true si la partida fue interrumpida, false en caso contrario
     */
    public boolean esPartidaInterrumpida() {
        return partidaInterrumpida;
    }

    /**
     * Verifica si la partida fue ganada.
     * 
     * @return true si la partida fue ganada, false si fue perdida, null si aún está
     *         en curso o fue interrumpida
     */
    public Boolean esPartidaGanada() {
        return partidaGanada;
    }

    /**
     * Genera un reporte de la partida actual.
     * Si la partida fue interrumpida, retorna null para no registrar resultado.
     * 
     * @return Un reporte con la información de la partida, o null si fue
     *         interrumpida
     */
    @Override
    public Reporte generarReporte() {
        // Si el juego aún está en curso, finalizarlo para el reporte
        if (juegoEnCurso) {
            finalizarPartida();
        }

        // Si la partida fue interrumpida, no se genera reporte
        if (partidaInterrumpida) {
            return null;
        }

        ResultadoPartida resultado = (partidaGanada != null && partidaGanada)
                ? ResultadoPartida.GANADA
                : ResultadoPartida.PERDIDA;

        ReporteBuscaminas reporte = new ReporteBuscaminas(
                jugador,
                calcularDuracion(),
                resultado,
                casillasDescubiertas,
                tablero.contarCasillasMarcadasCorrectas());

        return reporte;
    }

    /**
     * Obtiene el tablero asociado a la partida.
     * 
     * @return El tablero actual
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Obtiene la configuración de la partida.
     * 
     * @return La configuración actual
     */
    public ConfiguracionBuscaminas getConfiguracion() {
        return configuracion;
    }

    /**
     * Obtiene el número de casillas descubiertas.
     * 
     * @return Cantidad de casillas descubiertas
     */
    public int getCasillasDescubiertas() {
        return casillasDescubiertas;
    }

    /**
     * Descubre todas las minas del tablero.
     * Útil para mostrar la solución al finalizar una partida.
     */
    public void descubrirTodasLasMinas() {
        Casilla[][] casillas = tablero.getMatrizCasillas();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (casillas[i][j].contieneMina() && casillas[i][j].estaCubierta()) {
                    casillas[i][j].descubrir();
                }
            }
        }
    }
}
