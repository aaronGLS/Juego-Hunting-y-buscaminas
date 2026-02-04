/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practica2.view.buscaminas;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import practica2.controller.ControladorBuscaminas;
import practica2.model.buscaminas.ConfiguracionBuscaminas;
import practica2.view.comun.MarcoPrincipal;
import practica2.view.comun.UiStyles;
import practica2.view.comun.UiTheme;

/**
 *
 * @author aaron
 */
public class PanelConfigBuscaminas extends javax.swing.JPanel {

	        // Constantes de colores - Estilo Gamer
	        private static final Color COLOR_NEGRO = UiTheme.NEGRO;
	        private static final Color COLOR_MORADO_OSCURO = UiTheme.MORADO_OSCURO;
	        private static final Color COLOR_BLANCO = UiTheme.BLANCO;

	        // Colores neón
	        private static final Color COLOR_AZUL_NEON = UiTheme.AZUL_NEON;
	        private static final Color COLOR_VERDE_NEON = UiTheme.VERDE_NEON;
	        private static final Color COLOR_ROSA_NEON = UiTheme.ROSA_NEON;
	        private static final Color COLOR_ROJO_NEON = UiTheme.ROJO_NEON;

        private MarcoPrincipal marcoPrincipal;
        private ControladorBuscaminas controlador;

        /**
         * Constructor que recibe una referencia al marco principal y al controlador
         * 
         * @param marcoPrincipal El marco principal de la aplicación
         * @param controlador    El controlador del juego Buscaminas
         */
        public PanelConfigBuscaminas(MarcoPrincipal marcoPrincipal, ControladorBuscaminas controlador) {
                this.marcoPrincipal = marcoPrincipal;
                this.controlador = controlador;
                initComponents();
                aplicarEstilo();
        }

        /**
         * Aplica el estilo visual consistente con el resto de la aplicación
         */
        private void aplicarEstilo() {
                // Estilizar los botones
                configurarBoton(volverMenu, COLOR_NEGRO, COLOR_ROJO_NEON);
                configurarBoton(iniciarBuscaMinas, COLOR_NEGRO, COLOR_VERDE_NEON);

                // Estilizar el título
                titulo.setForeground(COLOR_AZUL_NEON);
                titulo.setBackground(COLOR_NEGRO);
                titulo.setOpaque(true);

                // Añadir borde al título
                Border bordeExterior = BorderFactory.createLineBorder(COLOR_ROSA_NEON, 3, true);
                Border bordeInterior = BorderFactory.createEmptyBorder(15, 15, 15, 15);
                titulo.setBorder(BorderFactory.createCompoundBorder(bordeExterior, bordeInterior));

                // Hacer los paneles transparentes para que se vea el degradado
                panelDatos.setOpaque(false);
                panelTitulo.setOpaque(false);
                panelBotones.setOpaque(false);

                // Estilizar los textos y campos
                Color colorEtiqueta = COLOR_AZUL_NEON;
                nombreJugadorName.setForeground(colorEtiqueta);
                tamañoTableroName.setForeground(colorEtiqueta);
                filaName.setForeground(colorEtiqueta);
                columnaName.setForeground(colorEtiqueta);
                cantidadMinasName.setForeground(colorEtiqueta);

                // Configurar campos de entrada
                estilizarCampoTexto(nombreJugador);
                estilizarCampoTexto(fila);
                estilizarCampoTexto(columna);
                estilizarCampoTexto(cantidadMinas);
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

	                // Configurar propiedades adicionales para mantener coherencia con PanelMenu
	                boton.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // Misma fuente que PanelMenu
	                boton.setContentAreaFilled(true);
	        }

        /**
         * Estiliza un campo de texto
         * 
         * @param campo El campo de texto a estilizar
         */
        private void estilizarCampoTexto(javax.swing.JTextField campo) {
                campo.setBackground(COLOR_NEGRO);
                campo.setForeground(COLOR_BLANCO);
                campo.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_NEON, 2));
                campo.setCaretColor(COLOR_BLANCO);
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
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                panelTitulo = new javax.swing.JPanel();
                titulo = new javax.swing.JLabel();
                panelDatos = new javax.swing.JPanel();
                nombreJugadorName = new javax.swing.JLabel();
                nombreJugador = new javax.swing.JTextField();
                tamañoTableroName = new javax.swing.JLabel();
                filaName = new javax.swing.JLabel();
                columnaName = new javax.swing.JLabel();
                cantidadMinasName = new javax.swing.JLabel();
                cantidadMinas = new javax.swing.JFormattedTextField();
                columna = new javax.swing.JFormattedTextField();
                fila = new javax.swing.JFormattedTextField();
                panelBotones = new javax.swing.JPanel();
                volverMenu = new javax.swing.JButton();
                iniciarBuscaMinas = new javax.swing.JButton();

                setMaximumSize(new java.awt.Dimension(1080, 700));
                setMinimumSize(new java.awt.Dimension(1080, 700));
                setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

                titulo.setFont(new java.awt.Font("Georgia", 1, 36)); // NOI18N
                titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                titulo.setText("Configuración del Buscaminas");
                titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
                titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                titulo.setMaximumSize(new java.awt.Dimension(800, 150));
                titulo.setMinimumSize(new java.awt.Dimension(800, 150));
                titulo.setPreferredSize(new java.awt.Dimension(800, 150));

                javax.swing.GroupLayout panelTituloLayout = new javax.swing.GroupLayout(panelTitulo);
                panelTitulo.setLayout(panelTituloLayout);
                panelTituloLayout.setHorizontalGroup(
                                panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTituloLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(titulo,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                panelTituloLayout.setVerticalGroup(
                                panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTituloLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(29, Short.MAX_VALUE)
                                                                .addComponent(titulo,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                139,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()));

                add(panelTitulo);

                panelDatos.setPreferredSize(new java.awt.Dimension(1080, 370));

                nombreJugadorName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                nombreJugadorName.setText("Nombre del Jugador");
                nombreJugadorName.setPreferredSize(new java.awt.Dimension(200, 50));

                nombreJugador.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                nombreJugador.setActionCommand("<Not Set>");
                nombreJugador.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
                nombreJugador.setPreferredSize(new java.awt.Dimension(200, 50));

                tamañoTableroName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                tamañoTableroName.setText("Tamaño del tablero");
                tamañoTableroName.setPreferredSize(new java.awt.Dimension(200, 50));

                filaName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                filaName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                filaName.setText("fila");
                filaName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                filaName.setPreferredSize(new java.awt.Dimension(200, 50));

                columnaName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                columnaName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                columnaName.setText("columna");
                columnaName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                columnaName.setPreferredSize(new java.awt.Dimension(200, 50));

                cantidadMinasName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                cantidadMinasName.setText("Cantidad de minas");
                cantidadMinasName.setPreferredSize(new java.awt.Dimension(200, 50));

                cantidadMinas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                cantidadMinas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                cantidadMinas.setPreferredSize(new java.awt.Dimension(90, 50));

                columna.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                columna.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                columna.setPreferredSize(new java.awt.Dimension(90, 50));

                fila.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                fila.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                fila.setPreferredSize(new java.awt.Dimension(90, 50));

                javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
                panelDatos.setLayout(panelDatosLayout);
                panelDatosLayout.setHorizontalGroup(
                                panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelDatosLayout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(
                                                                                panelDatosLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(tamañoTableroName,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(nombreJugadorName,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(cantidadMinasName,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(102, 102, 102)
                                                                .addGroup(panelDatosLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(nombreJugador,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(panelDatosLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                false)
                                                                                                .addGroup(panelDatosLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(filaName,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                90,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(61, 61, 61)
                                                                                                                .addComponent(columnaName,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                90,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGroup(panelDatosLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(fila,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(columna,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addComponent(cantidadMinas,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                panelDatosLayout.setVerticalGroup(
                                panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelDatosLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(
                                                                                panelDatosLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(nombreJugador,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(nombreJugadorName,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(panelDatosLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(panelDatosLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(39, 39, 39)
                                                                                                .addGroup(panelDatosLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(filaName,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(columnaName,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(panelDatosLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(fila,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(columna,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                61,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                panelDatosLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(tamañoTableroName,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(84, 84, 84)))
                                                                .addGroup(
                                                                                panelDatosLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(cantidadMinasName,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(cantidadMinas,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(57, 57, 57)));

                add(panelDatos);

                volverMenu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                volverMenu.setText("Volver al menú");
                volverMenu.setPreferredSize(new java.awt.Dimension(250, 60));
                volverMenu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                volverMenuActionPerformed(evt);
                        }
                });

                iniciarBuscaMinas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                iniciarBuscaMinas.setText("Jugar");
                iniciarBuscaMinas.setPreferredSize(new java.awt.Dimension(200, 60));
                iniciarBuscaMinas.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                iniciarBuscaMinasActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
                panelBotones.setLayout(panelBotonesLayout);
                panelBotonesLayout.setHorizontalGroup(
                                panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 1080, Short.MAX_VALUE)
                                                .addGroup(panelBotonesLayout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelBotonesLayout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(volverMenu,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(210, 210, 210)
                                                                                .addComponent(iniciarBuscaMinas,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE))));
                panelBotonesLayout.setVerticalGroup(
                                panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 152, Short.MAX_VALUE)
                                                .addGroup(panelBotonesLayout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(panelBotonesLayout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addGroup(panelBotonesLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(volverMenu,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(iniciarBuscaMinas,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(0, 0, Short.MAX_VALUE))));

                add(panelBotones);
        }// </editor-fold>//GEN-END:initComponents

        private void iniciarBuscaMinasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_iniciarBuscaMinasActionPerformed
                if (controlador == null) {
                        System.err.println("Error: controlador no está inicializado.");
                        return;
                }

                try {
                        // Recoger los datos directamente sin validación previa
                        String nombre = nombreJugador.getText();

                        // Intentar obtener valores numéricos
                        int filas, columnas, minas;

                        try {
                                // Obtener los valores de los campos formateados
                                filas = Integer.parseInt(fila.getText().trim());
                                columnas = Integer.parseInt(columna.getText().trim());
                                minas = Integer.parseInt(cantidadMinas.getText().trim());
                        } catch (NumberFormatException e) {
                                // Manejar errores de formato específicamente
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                "Por favor ingrese valores numéricos válidos en todos los campos.",
                                                "Error de formato",
                                                javax.swing.JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Crear configuración y usar el nuevo método del controlador que centraliza
                        // validaciones
                        ConfiguracionBuscaminas configuracion = new ConfiguracionBuscaminas(filas, columnas, minas,
                                        nombre);

                        // El controlador valida y retorna el error (si lo hay). La vista decide cómo mostrarlo.
                        String error = controlador.iniciarJuegoConValidacion(configuracion);
                        if (error != null) {
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                error,
                                                "Error de validación",
                                                javax.swing.JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        marcoPrincipal.mostrarPanelBuscaminas();
                } catch (Exception e) {
                        // Capturar cualquier otra excepción inesperada
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Error inesperado: " + e.getMessage(),
                                        "Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_iniciarBuscaMinasActionPerformed

        private void volverMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_volverMenuActionPerformed
                if (marcoPrincipal != null) {
                        marcoPrincipal.volverAlMenu();
                } else {
                        System.err.println("Error: marcoPrincipal no está inicializado.");
                }
        }// GEN-LAST:event_volverMenuActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JFormattedTextField cantidadMinas;
        private javax.swing.JLabel cantidadMinasName;
        private javax.swing.JFormattedTextField columna;
        private javax.swing.JLabel columnaName;
        private javax.swing.JFormattedTextField fila;
        private javax.swing.JLabel filaName;
        private javax.swing.JButton iniciarBuscaMinas;
        private javax.swing.JTextField nombreJugador;
        private javax.swing.JLabel nombreJugadorName;
        private javax.swing.JPanel panelBotones;
        private javax.swing.JPanel panelDatos;
        private javax.swing.JPanel panelTitulo;
        private javax.swing.JLabel tamañoTableroName;
        private javax.swing.JLabel titulo;
        private javax.swing.JButton volverMenu;
        // End of variables declaration//GEN-END:variables
}
