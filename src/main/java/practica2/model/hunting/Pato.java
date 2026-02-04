package practica2.model.hunting;

import java.util.Random;

/**
 * Clase que representa al pato en el juego Hunting.
 * Gestiona su posición, visibilidad y detección de clics.
 */
public class Pato {
    /**
     * Posición X actual del pato.
     */
    private int posicionX;
    
    /**
     * Posición Y actual del pato.
     */
    private int posicionY;
    
    /**
     * Ancho del pato (para detección de clics).
     */
    private int ancho;
    
    /**
     * Alto del pato (para detección de clics).
     */
    private int alto;
    
    /**
     * Indica si el pato está visible actualmente.
     */
    private boolean visible;
    
    /**
     * Límite de ancho para el área de juego.
     */
    private int limiteAncho;
    
    /**
     * Límite de alto para el área de juego.
     */
    private int limiteAlto;
    
    /**
     * Generador de números aleatorios para posicionar el pato.
     */
    private Random random;
    
    /**
     * Constructor que inicializa el pato con dimensiones y área de juego.
     * 
     * @param ancho Ancho del pato en píxeles
     * @param alto Alto del pato en píxeles
     * @param limiteAncho Ancho del área de juego
     * @param limiteAlto Alto del área de juego
     */
    public Pato(int ancho, int alto, int limiteAncho, int limiteAlto) {
        this.ancho = ancho;
        this.alto = alto;
        this.limiteAncho = limiteAncho;
        this.limiteAlto = limiteAlto;
        this.visible = false;
        this.random = new Random();
        
        // Posición inicial fuera de pantalla
        this.posicionX = -ancho;
        this.posicionY = -alto;
    }
    
    /**
     * Hace visible al pato.
     */
    public void mostrar() {
        this.visible = true;
    }
    
    /**
     * Oculta al pato.
     */
    public void ocultar() {
        this.visible = false;
    }
    
    /**
     * Mueve el pato a una posición aleatoria dentro del área de juego.
     */
    public void moverAleatorio() {
        // Calcula nueva posición asegurando que el pato quede dentro de los límites.
        // Evita Random.nextInt(n) con n <= 0 (crash cuando el área es más pequeña que el pato).
        int maxX = limiteAncho - ancho;
        int maxY = limiteAlto - alto;

        this.posicionX = (maxX <= 0) ? 0 : random.nextInt(maxX + 1);
        this.posicionY = (maxY <= 0) ? 0 : random.nextInt(maxY + 1);
    }
    
    /**
     * Verifica si las coordenadas de un clic están dentro del pato.
     * 
     * @param x Coordenada X del clic
     * @param y Coordenada Y del clic
     * @return true si el clic está sobre el pato, false en caso contrario
     */
    public boolean contieneClick(int x, int y) {
        if (!visible) {
            return false; // Si no está visible, no puede ser clickeado
        }
        
        return (x >= posicionX && x <= posicionX + ancho && 
                y >= posicionY && y <= posicionY + alto);
    }
    
    /**
     * Obtiene la posición X actual.
     * 
     * @return Coordenada X
     */
    public int getPosicionX() {
        return posicionX;
    }
    
    /**
     * Obtiene la posición Y actual.
     * 
     * @return Coordenada Y
     */
    public int getPosicionY() {
        return posicionY;
    }
    
    /**
     * Obtiene el ancho del pato.
     * 
     * @return Ancho en píxeles
     */
    public int getAncho() {
        return ancho;
    }
    
    /**
     * Obtiene el alto del pato.
     * 
     * @return Alto en píxeles
     */
    public int getAlto() {
        return alto;
    }
    
    /**
     * Indica si el pato está visible actualmente.
     * 
     * @return true si está visible, false en caso contrario
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Intenta registrar un acierto en el pato de forma atómica.
     * Si el pato está visible, lo oculta y devuelve true.
     * Si no, devuelve false.
     * 
     * @return true si el acierto fue exitoso, false en caso contrario
     */
    public synchronized boolean intentarAcierto() {
        if (this.visible) {
            this.visible = false; // Operación atómica: comprobar y establecer
            return true; // Acierto exitoso
        }
        return false; // Fallo, el pato ya no estaba visible
    }
}
