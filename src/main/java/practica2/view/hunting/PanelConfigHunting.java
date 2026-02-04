/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practica2.view.hunting;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JOptionPane;
import practica2.controller.ControladorHunting;
import practica2.model.hunting.ConfiguracionHunting;
import practica2.view.comun.MarcoPrincipal;
import practica2.view.comun.UiStyles;
import practica2.view.comun.UiTheme;

/**
 *
 * @author aaron
 */
public class PanelConfigHunting extends javax.swing.JPanel {

    // Constantes de colores - Estilo Gamer
    private static final Color COLOR_NEGRO = UiTheme.NEGRO;
    private static final Color COLOR_MORADO_OSCURO = UiTheme.MORADO_OSCURO;
    private static final Color COLOR_BLANCO = UiTheme.BLANCO;

    // Colores neón
    private static final Color COLOR_AZUL_NEON = UiTheme.AZUL_NEON;
    private static final Color COLOR_VERDE_NEON = UiTheme.VERDE_NEON;
    private static final Color COLOR_ROSA_NEON = UiTheme.ROSA_NEON;
    private static final Color COLOR_ROJO_NEON = UiTheme.ROJO_NEON;

    private MarcoPrincipal marco;
    private ControladorHunting hunting;

    /**
     * Creates new form PanelConfigHunting
     */
    public PanelConfigHunting() {
        initComponents();
        aplicarEstilo();
    }

    public PanelConfigHunting(MarcoPrincipal marco, ControladorHunting hunting) {
        initComponents();
        this.marco = marco;
        this.hunting = hunting;
        aplicarEstilo();
        configurarValoresPredeterminados();
    }
    
    /**
     * Aplica el estilo visual consistente con el resto de la aplicación
     */
    private void aplicarEstilo() {
        // Estilizar los botones
        configurarBoton(btnVolverMenu, COLOR_NEGRO, COLOR_ROJO_NEON);
        configurarBoton(btnJugar, COLOR_NEGRO, COLOR_VERDE_NEON);

        // Estilizar el título
        titulo.setForeground(COLOR_AZUL_NEON);
        titulo.setBackground(COLOR_NEGRO);
        titulo.setOpaque(true);

        // Añadir borde al título
        Border bordeExterior = BorderFactory.createLineBorder(COLOR_ROSA_NEON, 3, true);
        Border bordeInterior = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        titulo.setBorder(BorderFactory.createCompoundBorder(bordeExterior, bordeInterior));
        
        // Agregar icono al título si lo hay
        try {
            javax.swing.ImageIcon icono = new javax.swing.ImageIcon(
                    getClass().getResource("/practica2/resources/pato.png"));
            java.awt.Image img = icono.getImage();
            java.awt.Image imgEscalada = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            icono = new javax.swing.ImageIcon(imgEscalada);
            titulo.setIcon(icono);
            titulo.setIconTextGap(15);
            titulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            titulo.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono del título: " + e.getMessage());
        }

        // Hacer los paneles transparentes para que se vea el degradado
        panelTitulo.setOpaque(false);
        panelDatos.setOpaque(false);
        panelDatosInterno.setOpaque(false);
        panelBotones.setOpaque(false);

        // Estilizar los textos y campos
        Color colorEtiqueta = COLOR_AZUL_NEON;
        lblNombreJugador.setForeground(colorEtiqueta);
        lblVelocidadInicial.setForeground(colorEtiqueta);
        lblAciertosReducen.setForeground(colorEtiqueta);
        lblReduccionTiempo.setForeground(colorEtiqueta);

        // Configurar campos de entrada
        estilizarCampoTexto(txtNombreJugador);
        estilizarCampoTexto(txtVelocidadInicial);
        estilizarCampoTexto(txtAciertosReducen);
        estilizarCampoTexto(txtReduccionTiempo);
    }

    /**
     * Estiliza un campo de texto
     * 
     * @param campo El campo de texto a estilizar
     */
    private void estilizarCampoTexto(JTextField campo) {
        campo.setBackground(COLOR_NEGRO);
        campo.setForeground(COLOR_BLANCO);
        campo.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_NEON, 2));
        campo.setCaretColor(COLOR_BLANCO);
        campo.setText(""); // Limpiar el texto predeterminado "jTextField1"
    }

    /**
     * Configura valores predeterminados en los campos
     */
    private void configurarValoresPredeterminados() {
        txtNombreJugador.setText("");
        txtVelocidadInicial.setText("1000");
        txtAciertosReducen.setText("5");
        txtReduccionTiempo.setText("50");
    }

    /**
     * Configura el estilo visual de un botón
     * 
     * @param boton       El botón a estilizar
     * @param colorNormal El color normal del botón
     * @param colorHover  El color cuando el ratón está sobre el botón
     */
    private void configurarBoton(JButton boton, Color colorNormal, Color colorHover) {
        UiStyles.configurarBotonNeon(boton, colorNormal, colorHover, COLOR_BLANCO);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Asegurarse de que el gradiente cubra todo el panel
        int w = getWidth();
        int h = getHeight();

        // Crear un gradiente desde negro (arriba) hasta morado oscuro (abajo)
        GradientPaint gp = new GradientPaint(
                0, 0, COLOR_NEGRO,
                0, h, COLOR_MORADO_OSCURO);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitulo = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        panelDatosInterno = new javax.swing.JPanel();
        lblNombreJugador = new javax.swing.JLabel();
        txtNombreJugador = new javax.swing.JTextField();
        lblVelocidadInicial = new javax.swing.JLabel();
        txtVelocidadInicial = new javax.swing.JTextField();
        lblAciertosReducen = new javax.swing.JLabel();
        txtAciertosReducen = new javax.swing.JTextField();
        lblReduccionTiempo = new javax.swing.JLabel();
        txtReduccionTiempo = new javax.swing.JTextField();
        panelBotones = new javax.swing.JPanel();
        btnVolverMenu = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(45, 15), new java.awt.Dimension(200, 15), new java.awt.Dimension(45, 15));
        btnJugar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1080, 700));
        setMinimumSize(new java.awt.Dimension(1080, 700));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        panelTitulo.setOpaque(false);

        titulo.setFont(new java.awt.Font("Georgia", 1, 36)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Configuración de Hunting");
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titulo.setMaximumSize(new java.awt.Dimension(900, 900));
        titulo.setMinimumSize(new java.awt.Dimension(0, 0));
        titulo.setPreferredSize(new java.awt.Dimension(800, 150));

        javax.swing.GroupLayout panelTituloLayout = new javax.swing.GroupLayout(panelTitulo);
        panelTitulo.setLayout(panelTituloLayout);
        panelTituloLayout.setHorizontalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelTituloLayout.setVerticalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(panelTitulo);

        panelDatos.setOpaque(false);
        panelDatos.setLayout(new java.awt.GridBagLayout());

        panelDatosInterno.setOpaque(false);
        panelDatosInterno.setPreferredSize(new java.awt.Dimension(700, 400));

        lblNombreJugador.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNombreJugador.setText("Nombre del Jugador");

        txtNombreJugador.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtNombreJugador.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombreJugador.setText("jTextField1");
        txtNombreJugador.setPreferredSize(new java.awt.Dimension(200, 50));

        lblVelocidadInicial.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblVelocidadInicial.setText("Velocidad inicial (ms)");

        txtVelocidadInicial.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtVelocidadInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVelocidadInicial.setText("jTextField1");
        txtVelocidadInicial.setPreferredSize(new java.awt.Dimension(90, 50));

        lblAciertosReducen.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAciertosReducen.setText("Aciertos para reducir");

        txtAciertosReducen.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAciertosReducen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAciertosReducen.setText("jTextField1");
        txtAciertosReducen.setPreferredSize(new java.awt.Dimension(90, 50));

        lblReduccionTiempo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblReduccionTiempo.setText("Reducción por acierto (ms)");

        txtReduccionTiempo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtReduccionTiempo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtReduccionTiempo.setText("jTextField1");
        txtReduccionTiempo.setPreferredSize(new java.awt.Dimension(90, 50));

        javax.swing.GroupLayout panelDatosInternoLayout = new javax.swing.GroupLayout(panelDatosInterno);
        panelDatosInterno.setLayout(panelDatosInternoLayout);
        panelDatosInternoLayout.setHorizontalGroup(
            panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosInternoLayout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosInternoLayout.createSequentialGroup()
                        .addComponent(lblReduccionTiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                        .addComponent(txtReduccionTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosInternoLayout.createSequentialGroup()
                        .addComponent(lblAciertosReducen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAciertosReducen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosInternoLayout.createSequentialGroup()
                        .addComponent(lblVelocidadInicial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtVelocidadInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosInternoLayout.createSequentialGroup()
                        .addComponent(lblNombreJugador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        panelDatosInternoLayout.setVerticalGroup(
            panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosInternoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreJugador)
                    .addComponent(txtNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVelocidadInicial)
                    .addComponent(txtVelocidadInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAciertosReducen)
                    .addComponent(txtAciertosReducen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(panelDatosInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblReduccionTiempo)
                    .addComponent(txtReduccionTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDatos.add(panelDatosInterno, new java.awt.GridBagConstraints());

        add(panelDatos);

        panelBotones.setOpaque(false);

        btnVolverMenu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnVolverMenu.setText("Volver al menú");
        btnVolverMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        btnVolverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenuActionPerformed(evt);
            }
        });
        panelBotones.add(btnVolverMenu);
        panelBotones.add(filler1);

        btnJugar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnJugar.setText("Jugar");
        btnJugar.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        btnJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJugarActionPerformed(evt);
            }
        });
        panelBotones.add(btnJugar);

        add(panelBotones);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenuActionPerformed
        marco.volverAlMenu();
    }//GEN-LAST:event_btnVolverMenuActionPerformed

    private void btnJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugarActionPerformed
        try {
            String nombre = txtNombreJugador.getText().trim();
            int velocidad = Integer.parseInt(txtVelocidadInicial.getText().trim());
            int umbral = Integer.parseInt(txtAciertosReducen.getText().trim());
            int reduccion = Integer.parseInt(txtReduccionTiempo.getText().trim());

            // Crear objeto de configuración con el nuevo constructor que incluye nombre
            ConfiguracionHunting config = new ConfiguracionHunting(nombre, velocidad, umbral, reduccion);

            // El controlador valida y retorna el error (si lo hay). La vista decide cómo mostrarlo.
            String error = hunting.iniciarJuegoConValidacion(config);
            if (error != null) {
                JOptionPane.showMessageDialog(this,
                    error,
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            marco.mostrarPanelHunting();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, llena todos los campos con valores válidos.",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnJugarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJugar;
    private javax.swing.JButton btnVolverMenu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel lblAciertosReducen;
    private javax.swing.JLabel lblNombreJugador;
    private javax.swing.JLabel lblReduccionTiempo;
    private javax.swing.JLabel lblVelocidadInicial;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelDatosInterno;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JLabel titulo;
    private javax.swing.JTextField txtAciertosReducen;
    private javax.swing.JTextField txtNombreJugador;
    private javax.swing.JTextField txtReduccionTiempo;
    private javax.swing.JTextField txtVelocidadInicial;
    // End of variables declaration//GEN-END:variables
}
