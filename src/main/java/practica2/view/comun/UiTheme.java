package practica2.view.comun;

import java.awt.Color;

/**
 * Paleta y constantes visuales compartidas.
 * Evita duplicación de colores y valores en paneles.
 */
public final class UiTheme {

    private UiTheme() {
    }

    // Base
    public static final Color NEGRO = new Color(0, 0, 0);
    public static final Color MORADO_OSCURO = new Color(48, 0, 96);
    public static final Color BLANCO = new Color(255, 255, 255);

    // Neón
    public static final Color AZUL_NEON = new Color(0, 255, 255);
    public static final Color VERDE_NEON = new Color(0, 255, 0);
    public static final Color ROSA_NEON = new Color(255, 0, 255);
    public static final Color ROJO_NEON = new Color(255, 0, 0);

    // Tamaños comunes
    public static final int ICONO_BOTON_W = 64;
    public static final int ICONO_BOTON_H = 64;
    public static final int ICONO_BOTON_SMALL_W = 54;
    public static final int ICONO_BOTON_SMALL_H = 54;
    public static final int ICONO_TITULO_W = 100;
    public static final int ICONO_TITULO_H = 100;
}
