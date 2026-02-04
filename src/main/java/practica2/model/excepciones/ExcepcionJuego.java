package practica2.model.excepciones;

/**
 * Define una excepción personalizada para gestionar errores y condiciones 
 * excepcionales específicas en la lógica del juego.
 * Permite lanzar errores claros cuando se detectan configuraciones erróneas 
 * o acciones inválidas durante el desarrollo de una partida.
 */
public class ExcepcionJuego extends Exception {
    
    /**
     * Mensaje descriptivo del error ocurrido.
     */
    private String mensaje;
    
    /**
     * Código de error para identificar el tipo de problema.
     */
    private int codigoError;
    
    /**
     * Valores de códigos de error comunes.
     */
    public static final int ERROR_CONFIGURACION = 1;
    public static final int ERROR_INICIALIZACION = 2;
    public static final int ERROR_MOVIMIENTO_INVALIDO = 3;
    public static final int ERROR_JUEGO_FINALIZADO = 4;
    public static final int ERROR_GENERAL = 99;
    
    /**
     * Crea una excepción con un mensaje descriptivo y código de error general.
     * 
     * @param mensaje Descripción del error
     */
    public ExcepcionJuego(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
        this.codigoError = ERROR_GENERAL;
    }
    
    /**
     * Crea una excepción con mensaje y código de error específico.
     * 
     * @param mensaje Descripción del error
     * @param codigoError Código numérico que identifica el tipo de error
     */
    public ExcepcionJuego(String mensaje, int codigoError) {
        super(mensaje);
        this.mensaje = mensaje;
        this.codigoError = codigoError;
    }
    
    /**
     * Obtiene el mensaje descriptivo del error.
     * 
     * @return El mensaje de error
     */
    @Override
    public String getMessage() {
        return mensaje;
    }
    
    /**
     * Obtiene el código de error asociado a esta excepción.
     * 
     * @return El código de error
     */
    public int getCodigoError() {
        return codigoError;
    }
    
    /**
     * Obtiene una descripción textual del tipo de error basado en el código.
     * 
     * @return Descripción del tipo de error
     */
    public String getTipoError() {
        switch (codigoError) {
            case ERROR_CONFIGURACION:
                return "Error de configuración";
            case ERROR_INICIALIZACION:
                return "Error de inicialización";
            case ERROR_MOVIMIENTO_INVALIDO:
                return "Movimiento inválido";
            case ERROR_JUEGO_FINALIZADO:
                return "Juego ya finalizado";
            default:
                return "Error general";
        }
    }
    
    /**
     * Genera un mensaje detallado con el código y tipo de error.
     * 
     * @return Mensaje completo con detalles del error
     */
    public String getMensajeCompleto() {
        return "[" + getTipoError() + " - Código: " + codigoError + "] " + mensaje;
    }
}
