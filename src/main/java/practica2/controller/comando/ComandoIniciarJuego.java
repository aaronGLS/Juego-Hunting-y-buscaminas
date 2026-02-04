package practica2.controller.comando;

import practica2.model.buscaminas.ConfiguracionBuscaminas;
import practica2.model.buscaminas.JuegoBuscaminas;
import practica2.model.excepciones.ExcepcionJuego;

/**
 * Comando para iniciar una nueva partida de Buscaminas.
 * Implementa la interfaz Comando siguiendo el patrón Command.
 */
public class ComandoIniciarJuego implements Comando {
    
    /**
     * Juego a inicializar.
     */
    private JuegoBuscaminas juego;
    
    /**
     * Configuración con la que se iniciará el juego.
     */
    private ConfiguracionBuscaminas configuracion;
    
    /**
     * Constructor que inicializa el comando con los parámetros necesarios.
     * 
     * @param configuracion Configuración para el nuevo juego
     */
    public ComandoIniciarJuego(ConfiguracionBuscaminas configuracion) {
        this.configuracion = configuracion;
    }
    
    /**
     * Ejecuta la acción de iniciar una nueva partida.
     * 
     * @throws ExcepcionJuego Si se produce un error al inicializar el juego
     */
    @Override
    public void ejecutar() throws ExcepcionJuego {
        if (configuracion == null) {
            throw new ExcepcionJuego("La configuración no puede ser nula", ExcepcionJuego.ERROR_GENERAL);
        }
        
        // Crear una nueva instancia del juego con la configuración proporcionada
        juego = new JuegoBuscaminas(configuracion);
        
        // Iniciar la partida
        juego.iniciarPartida();
    }
    
    /**
     * Obtiene el juego inicializado.
     * 
     * @return Instancia del juego inicializado
     */
    public JuegoBuscaminas getJuego() {
        return juego;
    }
}
