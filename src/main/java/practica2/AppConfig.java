package practica2;

/**
 * Configuración centralizada de la aplicación.
 * Evita números mágicos repartidos por el código (p.ej. tamaños de ventana/áreas).
 */
public final class AppConfig {

    private AppConfig() {
    }

    public static final int APP_ANCHO = 1080;
    public static final int APP_ALTO = 700;

    // Tamaño del área de juego Hunting (coherente con el UI actual)
    public static final int HUNTING_AREA_ANCHO = 1080;
    public static final int HUNTING_AREA_ALTO = 400;
}

