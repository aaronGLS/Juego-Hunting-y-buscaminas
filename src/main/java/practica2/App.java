package practica2;

import javax.swing.SwingUtilities;

import practica2.view.comun.MarcoPrincipal;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarcoPrincipal::new);
    }
}