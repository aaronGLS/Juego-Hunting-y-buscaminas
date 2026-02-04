/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package practica2.view.comun;

import practica2.AppConfig;
import practica2.AppCompositionRoot;
import practica2.controller.ControladorBuscaminas;
import practica2.controller.ControladorHunting;
import practica2.view.buscaminas.PanelBuscaminas;
import practica2.view.buscaminas.PanelConfigBuscaminas;
import practica2.view.hunting.PanelConfigHunting;
import practica2.view.hunting.PanelHunting;

/**
 *
 * @author aaron
 */
public class MarcoPrincipal extends javax.swing.JFrame {

    /**
     * Controlador para las operaciones relacionadas con el juego Buscaminas
     */
    private ControladorBuscaminas controladorBuscaminas;

    /**
     * Controlador para las operaciones relacionadas con el juego Hunting
     */
    private ControladorHunting hunting;

    /**
     * Creates new form MarcoPrincipal
     */
    public MarcoPrincipal() {
        this(AppCompositionRoot.crearDependencias());
    }

    public MarcoPrincipal(AppCompositionRoot.Dependencias dependencias) {
        this(dependencias.controladorBuscaminas(), dependencias.controladorHunting());
    }

    public MarcoPrincipal(ControladorBuscaminas controladorBuscaminas, ControladorHunting hunting) {
        initComponents();
        configurarVentana();
        this.controladorBuscaminas = controladorBuscaminas;
        this.hunting = hunting;

        PanelMenu menu = new PanelMenu(this);
        contenedorPrincipal.add(menu, "menu");
        mostrarPanel("menu");
    }

    /**
     * Configura las características de la ventana principal
     */
    private void configurarVentana() {
        setTitle("Juegos Code n'Bugs");
        setSize(AppConfig.APP_ANCHO, AppConfig.APP_ALTO);
        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true); // Hacer visible la ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Muestra el panel especificado por su nombre.
     *
     * @param string El nombre del panel a mostrar
     */
    public void mostrarPanel(String string) {
        java.awt.CardLayout layout = (java.awt.CardLayout) contenedorPrincipal.getLayout();
        layout.show(contenedorPrincipal, string);
    }

    /**
     * Agrega un panel al contenedor principal con el nombre especificado.
     *
     * @param panel  El panel a agregar
     * @param nombre El nombre con el que se identificará el panel
     */
    public void agregarPanel(javax.swing.JPanel panel, String nombre) {
        contenedorPrincipal.add(panel, nombre);
        contenedorPrincipal.revalidate();
    }

    /**
     * Comprueba si existe un panel con el nombre especificado.
     *
     * @param nombre El nombre del panel a verificar
     * @return true si el panel existe, false en caso contrario
     */
    public boolean existePanel(String nombre) {
        for (java.awt.Component comp : contenedorPrincipal.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inicia la configuración del Buscaminas
     */
    public void iniciarBuscaminas() {
        if (existePanel("configBuscaminas")) {
            mostrarPanel("configBuscaminas");
        } else {
            PanelConfigBuscaminas panelConfig = new PanelConfigBuscaminas(this, controladorBuscaminas);
            panelConfig.setName("configBuscaminas");
            agregarPanel(panelConfig, "configBuscaminas");
            mostrarPanel("configBuscaminas");
        }
    }

    /**
     * Muestra el panel del juego Buscaminas
     */
    public void mostrarPanelBuscaminas() {
        if (existePanel("buscaminas")) {
            // Reiniciar el panel existente (p.ej. botón Pausar puede quedar deshabilitado
            // tras perder y volver al menú)
            for (java.awt.Component comp : contenedorPrincipal.getComponents()) {
                if (comp.getName() != null && comp.getName().equals("buscaminas") && comp instanceof PanelBuscaminas) {
                    ((PanelBuscaminas) comp).reiniciarPanelParaNuevaPartida();
                    break;
                }
            }
            mostrarPanel("buscaminas");
        } else {
            PanelBuscaminas panelBuscaminas = new PanelBuscaminas(this, controladorBuscaminas);
            panelBuscaminas.setName("buscaminas");
            agregarPanel(panelBuscaminas, "buscaminas");
            mostrarPanel("buscaminas");
        }
    }

    /**
     * Muestra el panel del juego Hunting
     */
    public void mostrarPanelHunting() {
        if (existePanel("hunting")) {
            for (java.awt.Component comp : contenedorPrincipal.getComponents()) {
                if (comp.getName() != null && comp.getName().equals("hunting")) {
                    // Actualizar los reportes en el panel existente
                    ((PanelHunting) comp).reiniciarPanel();
                    break;
                }
            }
            mostrarPanel("hunting");
        } else {
            PanelHunting panelHunting = new PanelHunting(this, hunting);
            panelHunting.setName("hunting");
            agregarPanel(panelHunting, "hunting");
            mostrarPanel("hunting");
        }
    }

    /**
     * Proporciona acceso al controlador del Buscaminas
     *
     * @return El controlador del juego Buscaminas
     */
    public ControladorBuscaminas getControladorBuscaminas() {
        return controladorBuscaminas;
    }

    /**
     * Inicia el juego de Hunting
     */
    public void iniciarConfigHunting() {
        if (existePanel("configHunting")) {
            mostrarPanel("configHunting");
        } else {
            PanelConfigHunting config = new PanelConfigHunting(this, hunting);
            config.setName("configHunting");
            agregarPanel(config, "configHunting");
            mostrarPanel("configHunting");
        }
    }

    /**
     * Muestra el panel de reportes
     */
    public void mostrarReportes() {
        if (existePanel("reportes")) {
            // Obtener el panel existente y actualizar sus datos antes de mostrarlo
            for (java.awt.Component comp : contenedorPrincipal.getComponents()) {
                if (comp.getName() != null && comp.getName().equals("reportes")) {
                    // Actualizar los reportes en el panel existente
                    ((PanelReportes) comp).actualizarReportes();
                    break;
                }
            }
            mostrarPanel("reportes");
        } else {
            PanelReportes panelReportes = new PanelReportes(this, controladorBuscaminas, hunting);
            panelReportes.setName("reportes");
            agregarPanel(panelReportes, "reportes");
            mostrarPanel("reportes");
        }
    }

    /**
     * Vuelve al menú principal
     */
    public void volverAlMenu() {
        mostrarPanel("menu");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedorPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Juegos Code n’Bugs");
        setPreferredSize(new java.awt.Dimension(800, 600));

        contenedorPrincipal.setMaximumSize(new java.awt.Dimension(1080, 700));
        contenedorPrincipal.setMinimumSize(new java.awt.Dimension(1080, 700));
        contenedorPrincipal.setPreferredSize(new java.awt.Dimension(1080, 700));
        contenedorPrincipal.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(contenedorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(contenedorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MarcoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarcoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarcoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarcoPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarcoPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedorPrincipal;
    // End of variables declaration//GEN-END:variables
}
