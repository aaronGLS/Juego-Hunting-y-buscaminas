package practica2.model.buscaminas;

/**
 * Modela una casilla individual en el tablero del Buscaminas.
 * Controla el estado de la casilla: si está cubierta, descubierta, marcada
 * o si contiene una mina, así como el número de minas adyacentes.
 */
public class Casilla {
    // Estado inicial de la casilla
    private boolean cubierta;      // Inicialmente true (casilla oculta)
    private boolean marcada;       // Inicialmente false (no está marcada)
    private boolean mina;          // Determinado al inicializar el tablero
    private int numeroMinasAdyacentes; // Cantidad de minas en casillas adyacentes
    
    /**
     * Constructor que inicializa una casilla con valores predeterminados.
     */
    public Casilla() {
        this.cubierta = true;
        this.marcada = false;
        this.mina = false;
        this.numeroMinasAdyacentes = 0;
    }
    
    /**
     * Constructor que inicializa una casilla especificando si contiene mina.
     * 
     * @param mina true si la casilla contiene una mina, false en caso contrario
     */
    public Casilla(boolean mina) {
        this.cubierta = true;
        this.marcada = false;
        this.mina = mina;
        this.numeroMinasAdyacentes = 0;
    }
    
    /**
     * Cambia el estado de la casilla a descubierta.
     * Este método debe ser llamado cuando el jugador selecciona una casilla.
     * 
     * @return true si la casilla contenía una mina (partida perdida), false en caso contrario
     */
    public boolean descubrir() {
        if (!marcada && cubierta) {
            cubierta = false;
            return mina; // Retorna true si hay mina (juego perdido)
        }
        return false;
    }
    
    /**
     * Alterna el estado de marcada de la casilla (permitiendo marcar y desmarcar).
     * Solo se puede marcar una casilla que esté cubierta.
     * 
     * @return true si la operación fue exitosa, false si la casilla está descubierta
     */
    public boolean marcar() {
        if (cubierta) {
            marcada = !marcada; // Alterna el estado de marcada
            return true;
        }
        return false;
    }
    
    /**
     * Verifica si la casilla está cubierta (no ha sido descubierta).
     * 
     * @return true si la casilla está cubierta, false si está descubierta
     */
    public boolean estaCubierta() {
        return cubierta;
    }
    
    /**
     * Establece el estado de cubierta de la casilla.
     * 
     * @param cubierta true para cubrir la casilla, false para descubrirla
     */
    public void setCubierta(boolean cubierta) {
        this.cubierta = cubierta;
    }
    
    /**
     * Verifica si la casilla está marcada con bandera.
     * 
     * @return true si la casilla está marcada, false en caso contrario
     */
    public boolean estaMarcada() {
        return marcada;
    }
    
    /**
     * Establece el estado de marcada de la casilla.
     * 
     * @param marcada true para marcar la casilla, false para desmarcarla
     */
    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }
    
    /**
     * Verifica si la casilla contiene una mina.
     * 
     * @return true si la casilla contiene una mina, false en caso contrario
     */
    public boolean contieneMina() {
        return mina;
    }
    
    /**
     * Establece si la casilla contiene una mina.
     * 
     * @param mina true para colocar una mina, false para quitarla
     */
    public void setMina(boolean mina) {
        this.mina = mina;
    }
    
    /**
     * Obtiene el número de minas adyacentes a esta casilla.
     * 
     * @return número de minas en las casillas vecinas
     */
    public int getNumeroMinasAdyacentes() {
        return numeroMinasAdyacentes;
    }
    
    /**
     * Establece el número de minas adyacentes a esta casilla.
     * 
     * @param numeroMinasAdyacentes número de minas en casillas vecinas
     */
    public void setNumeroMinasAdyacentes(int numeroMinasAdyacentes) {
        this.numeroMinasAdyacentes = numeroMinasAdyacentes;
    }
    
    /**
     * Incrementa el contador de minas adyacentes en uno.
     * Se utiliza durante la inicialización del tablero.
     */
    public void incrementarMinasAdyacentes() {
        this.numeroMinasAdyacentes++;
    }
    
    /**
     * Determina si la casilla está en un estado seguro para ser parte
     * del efecto dominó (no contiene mina y no tiene minas adyacentes).
     * 
     * @return true si es segura y sin minas adyacentes, false en caso contrario
     */
    public boolean esCasillaVacia() {
        return !mina && numeroMinasAdyacentes == 0;
    }
}
