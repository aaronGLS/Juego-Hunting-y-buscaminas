package practica2.controller.comando;

import practica2.model.buscaminas.JuegoBuscaminas;
import practica2.model.excepciones.ExcepcionJuego;

/**
 * Comando para descubrir una casilla en el tablero del Buscaminas.
 * Implementa la interfaz Comando siguiendo el patrón Command.
 */
public class ComandoDescubrirCasilla implements Comando {
    
    /**
     * Referencia al juego sobre el que se ejecutará el comando.
     */
    private JuegoBuscaminas juego;
    
    /**
     * Fila de la casilla a descubrir.
     */
    private int fila;
    
    /**
     * Columna de la casilla a descubrir.
     */
    private int columna;
    
    /**
     * Constructor que inicializa el comando con los parámetros necesarios.
     * 
     * @param juego Juego sobre el que se ejecutará el comando
     * @param fila Fila de la casilla a descubrir
     * @param columna Columna de la casilla a descubrir
     */
    public ComandoDescubrirCasilla(JuegoBuscaminas juego, int fila, int columna) {
        this.juego = juego;
        this.fila = fila;
        this.columna = columna;
    }
    
    /**
     * Ejecuta la acción de descubrir una casilla en el tablero.
     * 
     * @throws ExcepcionJuego Si se produce algún error al ejecutar la acción
     */
    @Override
    public void ejecutar() throws ExcepcionJuego {
        if (juego == null) {
            throw new ExcepcionJuego("No hay un juego en curso", ExcepcionJuego.ERROR_GENERAL);
        }
        
        // Delegar la acción al modelo
        boolean resultado = juego.descubrirCasilla(fila, columna);
        
        // Si la casilla no se pudo descubrir (ya estaba descubierta o marcada)
        if (!resultado) {
            // No lanzamos excepción, simplemente no hacemos nada
        }
    }
}
