package practica2.view.comun;

import javax.swing.SwingUtilities;

public class MainPrueba {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear instancia del MarcoPrincipal
            MarcoPrincipal marcoPrincipal = new MarcoPrincipal();
            
            // Mostrar la ventana principal
            marcoPrincipal.setVisible(true);
        });
    }
}
