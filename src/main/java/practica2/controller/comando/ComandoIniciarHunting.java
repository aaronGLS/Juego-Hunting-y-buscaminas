package practica2.controller.comando;

import practica2.controller.ControladorHunting;
import practica2.model.excepciones.ExcepcionJuego;
import practica2.model.hunting.ConfiguracionHunting;

/**
 * Comando para iniciar una nueva partida del juego Hunting.
 * Encapsula los parámetros necesarios para iniciar el juego.
 */
public class ComandoIniciarHunting implements Comando {

    /**
     * Controlador que gestionará el juego.
     */
    private ControladorHunting controlador;

    /**
     * Configuración con la que se iniciará el juego.
     */
    private ConfiguracionHunting configuracion;

    /**
     * Constructor que inicializa el comando con los parámetros necesarios.
     * 
     * @param controlador   Controlador que gestionará el juego
     * @param configuracion Configuración para el nuevo juego
     */
    public ComandoIniciarHunting(ConfiguracionHunting configuracion,
            ControladorHunting controlador) {
        this.controlador = controlador;
        this.configuracion = configuracion;
    }

    /**
     * Ejecuta el comando, iniciando una nueva partida con la configuración
     * especificada.
     * 
     * @throws ExcepcionJuego Si hay un error al iniciar el juego
     */
    @Override
    public void ejecutar() throws ExcepcionJuego {
        if (controlador == null) {
            throw new ExcepcionJuego("El controlador no está disponible",
                    ExcepcionJuego.ERROR_INICIALIZACION);
        }

        if (configuracion == null) {
            throw new ExcepcionJuego("La configuración no puede ser nula",
                    ExcepcionJuego.ERROR_GENERAL);
        }

        // La validación detallada ahora será responsabilidad del controlador
        // Todo está correcto, iniciar el juego
        controlador.iniciarJuego(configuracion);
    }
}
