package practica2.controller.comando;

import practica2.controller.ControladorHunting;
import practica2.model.core.EstadoPartida;
import practica2.model.excepciones.ExcepcionJuego;
import practica2.model.hunting.JuegoHunting;

/**
 * Comando para gestionar los disparos en el juego Hunting.
 * Implementa el patrón Command para encapsular la acción de disparar.
 */
public class ComandoDisparar implements Comando {

    /**
     * Controlador que gestiona el juego.
     */
    private ControladorHunting controlador;

    /**
     * Coordenada X del disparo en pantalla.
     */
    private int coordenadaX;

    /**
     * Coordenada Y del disparo en pantalla.
     */
    private int coordenadaY;

    /**
     * Constructor que inicializa el comando con el controlador y las coordenadas.
     * 
     * @param controlador Controlador del juego
     * @param coordenadaX Coordenada X del disparo
     * @param coordenadaY Coordenada Y del disparo
     */
    public ComandoDisparar(ControladorHunting controlador, int coordenadaX, int coordenadaY) {
        this.controlador = controlador;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    /**
     * Ejecuta la acción de disparo, delegando la lógica al modelo.
     * Solo realiza validaciones básicas y deja que el modelo maneje la lógica de negocio.
     * 
     * @throws ExcepcionJuego Si hay un error al procesar el disparo
     */
    @Override
    public void ejecutar() throws ExcepcionJuego {
        if (controlador == null) {
            throw new ExcepcionJuego("El controlador no está disponible",
                    ExcepcionJuego.ERROR_GENERAL);
        }

        JuegoHunting juego = controlador.getJuegoActual();

        if (juego == null) {
            throw new ExcepcionJuego("No hay juego en curso",
                    ExcepcionJuego.ERROR_JUEGO_FINALIZADO);
        }

        if (juego.getEstadoPartida() != EstadoPartida.EN_CURSO) {
            throw new ExcepcionJuego("El juego no está en curso",
                    ExcepcionJuego.ERROR_JUEGO_FINALIZADO);
        }

        // Delegamos toda la lógica del disparo al modelo
        juego.procesarDisparo(coordenadaX, coordenadaY);
    }

    /**
     * Obtiene la coordenada X del disparo.
     * 
     * @return Coordenada X
     */
    public int getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * Obtiene la coordenada Y del disparo.
     * 
     * @return Coordenada Y
     */
    public int getCoordenadaY() {
        return coordenadaY;
    }
}
