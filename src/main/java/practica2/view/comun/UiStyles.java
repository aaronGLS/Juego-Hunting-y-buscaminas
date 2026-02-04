package practica2.view.comun;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * Utilidades para aplicar estilos consistentes en la UI (Swing).
 * Centraliza lógica repetida (bordes neón, hover, carga de iconos).
 */
public final class UiStyles {

    private UiStyles() {
    }

    public static void configurarBotonNeon(JButton boton, Color colorNormal, Color colorHover, Color colorTexto) {
        configurarBotonNeon(boton, colorNormal, colorHover, colorTexto, 3, 8, 15);
    }

    public static void configurarBotonNeon(
            JButton boton,
            Color colorNormal,
            Color colorHover,
            Color colorTexto,
            int grosorBorde,
            int paddingVertical,
            int paddingHorizontal) {
        if (boton == null) {
            return;
        }

        boton.setBackground(colorNormal);
        boton.setForeground(colorTexto);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);

        Border bordeNeon = BorderFactory.createLineBorder(colorHover, grosorBorde, true);
        Border bordeVacio = BorderFactory.createEmptyBorder(paddingVertical, paddingHorizontal, paddingVertical, paddingHorizontal);
        boton.setBorder(BorderFactory.createCompoundBorder(bordeNeon, bordeVacio));

        aplicarHoverBackground(boton, colorNormal, colorHover);
    }

    public static void aplicarHoverBackground(JButton boton, Color colorNormal, Color colorHover) {
        if (boton == null) {
            return;
        }

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorNormal);
            }
        });
    }

    public static ImageIcon cargarIconoEscalado(Class<?> base, String resourcePath, int width, int height) {
        return UiIcons.get(base, resourcePath, width, height);
    }
}
