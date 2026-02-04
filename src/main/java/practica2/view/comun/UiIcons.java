package practica2.view.comun;

import java.awt.Image;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

/**
 * Cache simple de iconos escalados para evitar reescalar repetidamente.
 */
public final class UiIcons {

    private static final ConcurrentHashMap<String, ImageIcon> CACHE = new ConcurrentHashMap<>();

    private UiIcons() {
    }

    public static ImageIcon get(Class<?> base, String resourcePath, int width, int height) {
        if (base == null || resourcePath == null) {
            return null;
        }
        String key = resourcePath + "@" + width + "x" + height;
        return CACHE.computeIfAbsent(key, k -> cargar(base, resourcePath, width, height));
    }

    private static ImageIcon cargar(Class<?> base, String resourcePath, int width, int height) {
        try {
            java.net.URL url = base.getResource(resourcePath);
            if (url == null) {
                return null;
            }
            ImageIcon icono = new ImageIcon(url);
            Image img = icono.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }
}

