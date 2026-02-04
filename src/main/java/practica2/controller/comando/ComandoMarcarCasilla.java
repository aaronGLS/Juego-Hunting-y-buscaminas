package practica2.controller.comando;

import practica2.model.buscaminas.JuegoBuscaminas;
import practica2.model.excepciones.ExcepcionJuego;

/**
 * Comando para marcar o desmarcar una casilla en el tablero del Buscaminas.
 * Implementa la interfaz Comando siguiendo el patrón Command.
 */
public class ComandoMarcarCasilla implements Comando {
    
    /**
     * Referencia al juego sobre el que se ejecutará el comando.
     */
    private JuegoBuscaminas juego;
    
    /**
     * Fila de la casilla a marcar.
     */
    private int fila;
    
    /**
     * Columna de la casilla a marcar.
     */
    private int columna;
    
    /**
     * Constructor que inicializa el comando con los parámetros necesarios.
     * 
     * @param juego Juego sobre el que se ejecutará el comando
     * @param fila Fila de la casilla a marcar
     * @param columna Columna de la casilla a marcar
     */
    public ComandoMarcarCasilla(JuegoBuscaminas juego, int fila, int columna) {
        this.juego = juego;
        this.fila = fila;
        this.columna = columna;
    }
    
    /**
     * Ejecuta la acción de marcar o desmarcar una casilla en el tablero.
     * 
     * @throws ExcepcionJuego Si se produce algún error al ejecutar la acción
     */
    @Override
    public void ejecutar() throws ExcepcionJuego {
        if (juego == null) {
            throw new ExcepcionJuego("No hay un juego en curso", ExcepcionJuego.ERROR_GENERAL);
        }
        
        // Delegar la acción al modelo
        boolean resultado = juego.marcarCasilla(fila, columna);
        
        // Si la casilla no se pudo marcar (ya estaba descubierta)
        if (!resultado) {
            // No lanzamos excepción, simplemente no hacemos nada
        }
    }
}
