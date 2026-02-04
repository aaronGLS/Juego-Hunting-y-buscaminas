/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practica2.view.comun;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 *
 * @author aaron
 */
public class PanelMenu extends javax.swing.JPanel {

    // Constantes de colores - Estilo Gamer
    private static final Color COLOR_NEGRO = UiTheme.NEGRO;
    private static final Color COLOR_MORADO_OSCURO = UiTheme.MORADO_OSCURO;
    private static final Color COLOR_BLANCO = UiTheme.BLANCO;

    // Colores neón
    private static final Color COLOR_AZUL_NEON = UiTheme.AZUL_NEON;
    private static final Color COLOR_VERDE_NEON = UiTheme.VERDE_NEON;
    private static final Color COLOR_ROSA_NEON = UiTheme.ROSA_NEON;
    private static final Color COLOR_ROJO_NEON = UiTheme.ROJO_NEON;

    // Colores para botones
    private static final Color COLOR_BUSCAMINAS = COLOR_NEGRO;
    private static final Color COLOR_BUSCAMINAS_HOVER = COLOR_AZUL_NEON;
    private static final Color COLOR_HUNTING = COLOR_NEGRO;
    private static final Color COLOR_HUNTING_HOVER = COLOR_VERDE_NEON;
    private static final Color COLOR_REPORTES = COLOR_NEGRO;
    private static final Color COLOR_REPORTES_HOVER = COLOR_ROJO_NEON;

    // Constantes de dimensiones
    private static final int ESPACIO_TITULO_BOTONES = 70;
    private static final int ESPACIO_ENTRE_COMPONENTES = 20;

    private MarcoPrincipal marcoPrincipal;

    /**
     * Creates new form PanelMenu
     */
    public PanelMenu() {
        initComponents();
        configurarPanelPrincipal();
        mejorarTitulo();
        configurarBotones();
        centrarComponentes();
    }

    public PanelMenu(MarcoPrincipal marcoPrincipal) {
        this.marcoPrincipal = marcoPrincipal;
        initComponents();
        configurarPanelPrincipal();
        mejorarTitulo();
        configurarBotones();
        centrarComponentes();
    }

    /**
     * Configura el panel principal con sus propiedades básicas
     */
    private void configurarPanelPrincipal() {
        setBorder(new javax.swing.border.LineBorder(COLOR_BLANCO, 2, true));
    }

    /**
     * Configura los botones con sus colores y efectos respectivos
     */
    private void configurarBotones() {
        configurarBoton(iniciarBuscaMinas, COLOR_BUSCAMINAS, COLOR_BUSCAMINAS_HOVER);
        configurarBoton(iniciarHunting, COLOR_HUNTING, COLOR_HUNTING_HOVER);
        configurarBoton(reportes, COLOR_REPORTES, COLOR_REPORTES_HOVER);

        // Agregar iconos a los botones
        configurarIconoBoton(iniciarBuscaMinas, "/practica2/resources/minas.png");
        configurarIconoBoton(iniciarHunting, "/practica2/resources/pato.png");
        configurarIconoBoton(reportes, "/practica2/resources/reportes.png");

        // Configurar propiedades comunes a todos los botones
        JButton[] botones = { iniciarBuscaMinas, iniciarHunting, reportes };
        for (JButton boton : botones) {
            boton.setFocusPainted(false);
            boton.setContentAreaFilled(true);
            boton.setPreferredSize(new Dimension(180, 60));
        }
    }

    /**
     * Aplica configuración específica a un botón
     * 
     * @param boton         El botón a configurar
     * @param colorOriginal Color normal del botón
     * @param colorHover    Color cuando el ratón está sobre el botón
     */
    private void configurarBoton(JButton boton, Color colorOriginal, Color colorHover) {
        UiStyles.configurarBotonNeon(boton, colorOriginal, colorHover, COLOR_BLANCO);
    }

    /**
     * Configura un icono para un botón específico
     * 
     * @param boton     El botón al que se añadirá el icono
     * @param rutaIcono Ruta relativa al icono dentro de los recursos
     */
    private void configurarIconoBoton(JButton boton, String rutaIcono) {
        javax.swing.ImageIcon icono = UiStyles.cargarIconoEscalado(getClass(), rutaIcono, UiTheme.ICONO_BOTON_W, UiTheme.ICONO_BOTON_H);
        if (icono != null) {
            boton.setIcon(icono);
            boton.setIconTextGap(10); // Espacio entre el icono y el texto
            boton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            boton.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        }
    }

    /**
     * Centra todos los componentes en el panel
     */
    private void centrarComponentes() {
        Component[] componentes = {
                iniciarBuscaMinas, iniciarHunting, reportes, titulo,
                filler1, filler2, filler3, filler4
        };

        for (Component componente : componentes) {
            if (componente instanceof javax.swing.JComponent) {
                ((javax.swing.JComponent) componente).setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
            }
        }
    }

    /**
     * Mejora la apariencia del título
     */
    private void mejorarTitulo() {
        // Configurar apariencia del texto
        titulo.setForeground(COLOR_AZUL_NEON);

        // Configurar fondo del título
        titulo.setBackground(COLOR_NEGRO);
        titulo.setOpaque(true);

        // Añadir borde al título con efecto de neón
        Border bordeExterior = BorderFactory.createLineBorder(COLOR_ROSA_NEON, 3, true);
        Border bordeInterior = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        titulo.setBorder(BorderFactory.createCompoundBorder(bordeExterior, bordeInterior));

        // Agregar icono al título
        javax.swing.ImageIcon icono = UiStyles.cargarIconoEscalado(getClass(), "/practica2/resources/gamer.png", UiTheme.ICONO_TITULO_W, UiTheme.ICONO_TITULO_H);
        if (icono != null) {
            titulo.setIcon(icono);
            titulo.setIconTextGap(15); // Espacio entre el icono y el texto

            // Ajustar la posición del texto respecto al icono
            titulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            titulo.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        }

        // Configurar espaciado entre título y botones
        filler3.setMinimumSize(new Dimension(ESPACIO_ENTRE_COMPONENTES, ESPACIO_TITULO_BOTONES));
        filler3.setPreferredSize(new Dimension(ESPACIO_ENTRE_COMPONENTES, ESPACIO_TITULO_BOTONES));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20));
        titulo = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 80));
        iniciarBuscaMinas = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20));
        iniciarHunting = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20), new java.awt.Dimension(20, 20));
        reportes = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 204, 204));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        add(filler4);

        titulo.setFont(new java.awt.Font("Georgia", 1, 40)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText(" ¡Bienvenido a Juegos Code n’Bugs! ");
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titulo.setMaximumSize(new java.awt.Dimension(900, 200));
        titulo.setPreferredSize(new java.awt.Dimension(800, 200));
        add(titulo);
        add(filler3);

        iniciarBuscaMinas.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        iniciarBuscaMinas.setText("Iniciar Buscaminas");
        iniciarBuscaMinas.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        iniciarBuscaMinas.setMaximumSize(new java.awt.Dimension(350, 80));
        iniciarBuscaMinas.setMinimumSize(new java.awt.Dimension(150, 60));
        iniciarBuscaMinas.setPreferredSize(new java.awt.Dimension(150, 75));
        iniciarBuscaMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarBuscaMinasActionPerformed(evt);
            }
        });
        add(iniciarBuscaMinas);
        add(filler1);

        iniciarHunting.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        iniciarHunting.setText("Iniciar Hunting");
        iniciarHunting.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        iniciarHunting.setMaximumSize(new java.awt.Dimension(350, 80));
        iniciarHunting.setMinimumSize(new java.awt.Dimension(150, 60));
        iniciarHunting.setPreferredSize(new java.awt.Dimension(150, 75));
        iniciarHunting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarHuntingActionPerformed(evt);
            }
        });
        add(iniciarHunting);
        add(filler2);

        reportes.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        reportes.setText("Ver Reportes");
        reportes.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        reportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reportes.setMaximumSize(new java.awt.Dimension(350, 80));
        reportes.setMinimumSize(new java.awt.Dimension(150, 60));
        reportes.setPreferredSize(new java.awt.Dimension(150, 75));
        add(reportes);
        reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportesActionPerformed(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarBuscaMinasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_iniciarBuscaMinasActionPerformed
        if (marcoPrincipal != null) {
            marcoPrincipal.iniciarBuscaminas();
        } else {
            System.err.println("Error: marcoPrincipal no está inicializado.");
        }
    }// GEN-LAST:event_iniciarBuscaMinasActionPerformed

    private void iniciarHuntingActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_iniciarHuntingActionPerformed
        if (marcoPrincipal != null) {
            marcoPrincipal.iniciarConfigHunting();
        } else {
            System.err.println("Error: marcoPrincipal no está inicializado.");
        }
    }// GEN-LAST:event_iniciarHuntingActionPerformed

    private void reportesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_reportesActionPerformed
        if (marcoPrincipal != null) {
            marcoPrincipal.mostrarReportes();
        } else {
            System.err.println("Error: marcoPrincipal no está inicializado.");
        }
    }// GEN-LAST:event_reportesActionPerformed

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Usar las constantes de colores definidas al inicio de la clase
        GradientPaint gp = new GradientPaint(0, 0, COLOR_NEGRO, 0, getHeight(), COLOR_MORADO_OSCURO);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JButton iniciarBuscaMinas;
    private javax.swing.JButton iniciarHunting;
    private javax.swing.JButton reportes;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
