package practica2.model.core;

/**
 * Clase que representa la información del jugador que participa en cualquier partida.
 * Encapsula el nombre, puntaje y otros datos personales o de juego necesarios
 * para los reportes y la lógica de partida.
 */
public class Jugador {
    // Atributos del jugador
    private String nombre;
    private int puntaje; // Para el juego Hunting (en Buscaminas puede omitirse o usar estadísticas)
    
    /**
     * Constructor que inicializa un jugador solo con nombre
     * @param nombre El nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0; // Puntaje inicial en cero
    }
    
    /**
     * Constructor que inicializa un jugador con nombre y puntaje
     * @param nombre El nombre del jugador
     * @param puntajeInicial El puntaje inicial del jugador
     */
    public Jugador(String nombre, int puntajeInicial) {
        this.nombre = nombre;
        this.puntaje = puntajeInicial;
    }
    
    /**
     * Obtiene el nombre del jugador
     * @return El nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del jugador
     * @param nombre El nuevo nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el puntaje actual del jugador
     * @return El puntaje actual
     */
    public int getPuntaje() {
        return puntaje;
    }
    
    /**
     * Establece el puntaje del jugador
     * @param puntaje El nuevo puntaje
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    /**
     * Incrementa el puntaje del jugador en la cantidad especificada
     * @param valor La cantidad a incrementar
     */
    public void incrementarPuntaje(int valor) {
        this.puntaje += valor;
    }
    
    /**
     * Decrementa el puntaje del jugador en la cantidad especificada
     * @param valor La cantidad a decrementar
     */
    public void decrementarPuntaje(int valor) {
        this.puntaje -= valor;
        // Evitar puntajes negativos si es necesario
        if (this.puntaje < 0) {
            this.puntaje = 0;
        }
    }
    
    /**
     * Reinicia el puntaje del jugador a cero
     */
    public void reiniciarPuntaje() {
        this.puntaje = 0;
    }
}
