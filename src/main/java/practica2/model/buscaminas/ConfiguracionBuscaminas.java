package practica2.model.buscaminas;

/**
 * Almacena y gestiona los parámetros necesarios para configurar
 * una partida de Buscaminas. Permite establecer el tamaño del tablero,
 * la cantidad de minas y el nombre del jugador.
 */
public class ConfiguracionBuscaminas {

    /**
     * Tamaño del tablero (número de filas y columnas en tablero cuadrado).
     */
    private int tamañoTablero;

    /**
     * Número de filas del tablero (para tableros no cuadrados).
     */
    private int filas;

    /**
     * Número de columnas del tablero (para tableros no cuadrados).
     */
    private int columnas;

    /**
     * Cantidad de minas que se colocarán en el tablero.
     */
    private int cantidadMinas;

    /**
     * Nombre del jugador que participa en la partida.
     */
    private String nombreJugador;

    /**
     * Indica si el tablero debe ser cuadrado (mismo número de filas y columnas).
     */
    private boolean tableroCuadrado;


    /**
     * Constructor para configuración personalizada con tablero no cuadrado.
     * 
     * @param filas         Número de filas del tablero
     * @param columnas      Número de columnas del tablero
     * @param cantidadMinas Cantidad de minas a colocar
     * @param nombreJugador Nombre del jugador
     */
    public ConfiguracionBuscaminas(int filas, int columnas, int cantidadMinas, String nombreJugador) {
        this.filas = filas;
        this.columnas = columnas;
        this.tamañoTablero = Math.max(filas, columnas); // Para compatibilidad
        this.cantidadMinas = cantidadMinas;
        this.nombreJugador = nombreJugador;
        this.tableroCuadrado = (filas == columnas);

    }

    /**
     * Obtiene el tamaño del tablero (para tableros cuadrados).
     * 
     * @return Tamaño del tablero
     */
    public int getTamañoTablero() {
        return tamañoTablero;
    }

    /**
     * Establece el tamaño del tablero (para tableros cuadrados).
     * 
     * @param tamañoTablero Nuevo tamaño del tablero
     */
    public void setTamañoTablero(int tamañoTablero) {
        this.tamañoTablero = tamañoTablero;
        if (tableroCuadrado) {
            this.filas = tamañoTablero;
            this.columnas = tamañoTablero;
        }
    }

    /**
     * Obtiene el número de filas del tablero.
     * 
     * @return Número de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Establece el número de filas del tablero.
     * 
     * @param filas Nuevo número de filas
     */
    public void setFilas(int filas) {
        this.filas = filas;
        this.tableroCuadrado = (filas == columnas);
        if (tableroCuadrado) {
            this.tamañoTablero = filas;
        }
    }

    /**
     * Obtiene el número de columnas del tablero.
     * 
     * @return Número de columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Establece el número de columnas del tablero.
     * 
     * @param columnas Nuevo número de columnas
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
        this.tableroCuadrado = (filas == columnas);
        if (tableroCuadrado) {
            this.tamañoTablero = columnas;
        }
    }

    /**
     * Obtiene la cantidad de minas a colocar en el tablero.
     * 
     * @return Cantidad de minas
     */
    public int getCantidadMinas() {
        return cantidadMinas;
    }

    /**
     * Establece la cantidad de minas a colocar en el tablero.
     * 
     * @param cantidadMinas Nueva cantidad de minas
     */
    public void setCantidadMinas(int cantidadMinas) {
        this.cantidadMinas = cantidadMinas;
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
     * Verifica si el tablero es cuadrado.
     * 
     * @return true si el tablero es cuadrado, false en caso contrario
     */
    public boolean esTableroCuadrado() {
        return tableroCuadrado;
    }

}
