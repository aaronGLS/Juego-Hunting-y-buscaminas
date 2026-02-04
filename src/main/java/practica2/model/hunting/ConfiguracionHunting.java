package practica2.model.hunting;

/**
 * Configuración específica para el juego Hunting.
 * Contiene los parámetros que definen la dificultad y comportamiento del juego.
 */
public class ConfiguracionHunting {
    /**
     * Nombre del jugador que participa en la partida.
     */
    private String nombreJugador;

    /**
     * Tiempo inicial en milisegundos que se muestra el pato en pantalla.
     */
    private int tiempoVisibilidadInicial;

    /**
     * Cantidad de aciertos necesarios para reducir el tiempo de visibilidad.
     */
    private int umbralAciertos;

    /**
     * Cantidad de milisegundos que se reduce el tiempo de visibilidad
     * después de alcanzar el umbral de aciertos.
     */
    private int reduccionTiempo;

    /**
     * Cantidad máxima de fallos consecutivos permitidos.
     * Por requisito siempre es 5.
     */
    public static final int FALLOS_MAXIMOS = 5;

    /**
     * Constructor de la configuración con los parámetros necesarios.
     * 
     * @param nombreJugador            Nombre del jugador
     * @param tiempoVisibilidadInicial Tiempo inicial en milisegundos que se muestra
     *                                 el pato
     * @param umbralAciertos           Cantidad de aciertos para reducir tiempo
     * @param reduccionTiempo          Cantidad de milisegundos a reducir
     */
    public ConfiguracionHunting(String nombreJugador, int tiempoVisibilidadInicial, int umbralAciertos,
            int reduccionTiempo) {
        this.nombreJugador = nombreJugador;
        this.tiempoVisibilidadInicial = tiempoVisibilidadInicial;
        this.umbralAciertos = umbralAciertos;
        this.reduccionTiempo = reduccionTiempo;
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return Nombre del jugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * Establece el nombre del jugador.
     * 
     * @param nombreJugador Nuevo nombre del jugador
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    /**
     * Obtiene el tiempo inicial de visibilidad.
     * 
     * @return Tiempo en milisegundos
     */
    public int getTiempoVisibilidadInicial() {
        return tiempoVisibilidadInicial;
    }

    /**
     * Establece el tiempo inicial de visibilidad.
     * 
     * @param tiempoVisibilidadInicial Tiempo en milisegundos
     */
    public void setTiempoVisibilidadInicial(int tiempoVisibilidadInicial) {
        this.tiempoVisibilidadInicial = tiempoVisibilidadInicial;
    }

    /**
     * Obtiene el umbral de aciertos para reducir tiempo.
     * 
     * @return Cantidad de aciertos
     */
    public int getUmbralAciertos() {
        return umbralAciertos;
    }

    /**
     * Establece el umbral de aciertos para reducir tiempo.
     * 
     * @param umbralAciertos Cantidad de aciertos
     */
    public void setUmbralAciertos(int umbralAciertos) {
        this.umbralAciertos = umbralAciertos;
    }

    /**
     * Obtiene la reducción de tiempo tras alcanzar umbral.
     * 
     * @return Tiempo a reducir en milisegundos
     */
    public int getReduccionTiempo() {
        return reduccionTiempo;
    }

    /**
     * Establece la reducción de tiempo tras alcanzar umbral.
     * 
     * @param reduccionTiempo Tiempo a reducir en milisegundos
     */
    public void setReduccionTiempo(int reduccionTiempo) {
        this.reduccionTiempo = reduccionTiempo;
    }

    @Override
    public String toString() {
        return String.format(
            "Tiempo: %d ms, Umbral: %d, Reducción: %d ms",
            tiempoVisibilidadInicial,
            umbralAciertos,
            reduccionTiempo
        );
    }

}
