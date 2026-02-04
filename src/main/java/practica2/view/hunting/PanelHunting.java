/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practica2.view.hunting;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import practica2.controller.ControladorHunting;
import practica2.controller.HuntingObservador;
import practica2.view.comun.MarcoPrincipal;
import practica2.view.comun.UiStyles;
import practica2.view.comun.UiTheme;

/**
 *
 * @author aaron
 */
public class PanelHunting extends javax.swing.JPanel implements HuntingObservador {
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
    
    // Imagen de fondo para el panel
    private javax.swing.ImageIcon imagenFondo;

    // Estado del juego
    private boolean juegoActivo = false;

    /**
     * Creates new form PanelHunting
     */
    public PanelHunting() {
        initComponents();
        aplicarEstilo();
    }

    public PanelHunting(MarcoPrincipal marco, ControladorHunting hunting) {
        initComponents();
        this.marco = marco;
        this.hunting = hunting;

        // Registrar este panel como observador del controlador
        if (hunting != null) {
            hunting.registrarObservador(this);
        }

        aplicarEstilo();
        inicializarComponentesJuego();
    }

    /**
     * Inicializa los componentes adicionales necesarios para el juego
     */
    private void inicializarComponentesJuego() {
        // Configurar el panel del pato para recibir clics (disparos)
        panelAreaPato.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (juegoActivo) {
                    procesarDisparo(e.getPoint());
                }
            }
        });

        // Iniciar el juego
        juegoActivo = true;

        // Actualizar interfaz inicial
        actualizarInterfaz();
    }

    /**
     * Procesa un disparo realizado por el usuario
     * 
     * @param punto Coordenadas del clic
     */
    private void procesarDisparo(Point punto) {
        if (hunting != null && hunting.isJuegoEnCurso()) {

            hunting.procesarDisparo(punto.x, punto.y);
        }
    }

    /**
     * Actualiza la interfaz con la información del modelo.
     * Este método es llamado cuando se reciben notificaciones del controlador.
     */
    private void actualizarInterfaz() {
        if (hunting == null) {
            return;
        }

        // Verificar estado del juego
        actualizarEstadoJuegoUI();

        // Si el juego está en curso, actualizar todos los componentes
        if (hunting.isJuegoEnCurso()) {
            actualizarPatoUI();
            actualizarPuntuacionUI();
            actualizarTiempoUI();
        }

        // Si el juego no está en curso, actualizar todos los componentes
        if (!hunting.isJuegoEnCurso()) {
            // Actualizar contadores y tiempo
            actualizarContadores(hunting.getAciertos(), hunting.getFallosConsecutivos(), hunting.getNombreJugador());
            actualizarTiempo(hunting.getTiempoTranscurrido());

        }
    }

    /**
     * Actualiza el estado general del juego en la UI
     */
    private void actualizarEstadoJuegoUI() {
        // Verificar si el juego ha terminado pero la UI aún lo considera activo
        if (!hunting.isJuegoEnCurso() && juegoActivo) {
            juegoActivo = false;
            mostrarResultadosFinales();
        }
    }

    /**
     * Actualiza la posición y visibilidad del pato en la UI
     */
    private void actualizarPatoUI() {
        if (hunting != null) {

            int[] posPato = hunting.getPosicionPato();
            boolean patoVisible = hunting.isPatoVisible();

            // Actualizar la posición del label del pato
            lblPato.setLocation(posPato[0], posPato[1]);
            lblPato.setVisible(patoVisible);
        }
    }

    /**
     * Actualiza los contadores de puntuación en la UI
     */
    private void actualizarPuntuacionUI() {
        actualizarContadores(hunting.getAciertos(), hunting.getFallosConsecutivos(), hunting.getNombreJugador());
    }

    /**
     * Actualiza el tiempo mostrado en la UI
     */
    private void actualizarTiempoUI() {
        actualizarTiempo(hunting.getTiempoTranscurrido());
    }

    /**
     * Muestra los resultados finales del juego
     */
    private void mostrarResultadosFinales() {
        // Mostrar resultados finales
        String mensaje = "¡Juego terminado!\n" +
                "Aciertos: " + hunting.getAciertos() + "\n" +
                "Tiempo total: " + hunting.getTiempoTranscurrido() + " segundos";

        // Mostrar diálogo de opciones
        String[] opciones = { "Volver al menú", "Jugar de nuevo" };
        int eleccion = JOptionPane.showOptionDialog(this,
                mensaje,
                "Fin del juego",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, opciones, opciones[0]);

        hunting.finalizarJuego();
        if (hunting.getUltimoErrorReportes() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    hunting.getUltimoErrorReportes(),
                    "Error al guardar reporte",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Procesar la elección del usuario
        if (eleccion == 0) {
            // Volver al menú
            if (marco != null) {
                marco.volverAlMenu();
            }
        } else {

            if (marco != null) {
                marco.iniciarConfigHunting();
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDatos = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblNombretxt = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(80, 15), new java.awt.Dimension(80, 15),
                new java.awt.Dimension(80, 15));
        jLabel1 = new javax.swing.JLabel();
        lblAciertostxt = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(80, 15), new java.awt.Dimension(80, 15),
                new java.awt.Dimension(80, 15));
        jLabel3 = new javax.swing.JLabel();
        lblFallostxt = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(80, 15), new java.awt.Dimension(80, 15),
                new java.awt.Dimension(80, 15));
        lblTiempo = new javax.swing.JLabel();
        lblTiempotxt = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        btnVolverMenu = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(80, 15), new java.awt.Dimension(200, 15),
                new java.awt.Dimension(80, 15));
        btnSalirJuego = new javax.swing.JButton();
        panelAreaPato = new javax.swing.JPanel();
        lblPato = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1080, 700));
        setMinimumSize(new java.awt.Dimension(1080, 700));
        setPreferredSize(new java.awt.Dimension(1080, 700));
        setLayout(new java.awt.BorderLayout());

        panelDatos.setOpaque(false);
        panelDatos.setPreferredSize(new java.awt.Dimension(1080, 110));
        panelDatos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        lblNombre.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblNombre.setText("Nombre: ");
        panelDatos.add(lblNombre);

        lblNombretxt.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblNombretxt.setText("name");
        panelDatos.add(lblNombretxt);
        panelDatos.add(filler4);

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel1.setText("Aciertos: ");
        panelDatos.add(jLabel1);

        lblAciertostxt.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblAciertostxt.setText("0");
        panelDatos.add(lblAciertostxt);
        panelDatos.add(filler1);

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel3.setText("Fallos: ");
        panelDatos.add(jLabel3);

        lblFallostxt.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblFallostxt.setText("0");
        panelDatos.add(lblFallostxt);
        panelDatos.add(filler3);

        lblTiempo.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblTiempo.setText("Tiempo: ");
        panelDatos.add(lblTiempo);

        lblTiempotxt.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        panelDatos.add(lblTiempotxt);

        add(panelDatos, java.awt.BorderLayout.PAGE_START);

        panelBotones.setMinimumSize(new java.awt.Dimension(439, 110));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new java.awt.Dimension(1080, 110));
        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 25));

        btnVolverMenu.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnVolverMenu.setText("Volver al menú");
        btnVolverMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        btnVolverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenuActionPerformed(evt);
            }
        });
        panelBotones.add(btnVolverMenu);
        panelBotones.add(filler2);

        btnSalirJuego.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnSalirJuego.setText("Salir del juego");
        btnSalirJuego.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        btnSalirJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirJuegoActionPerformed(evt);
            }
        });
        panelBotones.add(btnSalirJuego);

        add(panelBotones, java.awt.BorderLayout.PAGE_END);

        panelAreaPato.setOpaque(false);
        panelAreaPato.setLayout(null);
        panelAreaPato.add(lblPato);
        lblPato.setBounds(520, 159, 200, 120);

        add(panelAreaPato, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnVolverMenuActionPerformed
        if (hunting != null && hunting.isJuegoEnCurso()) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas volver al menú? Se perderá la partida actual.",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                // Cancelar el juego sin guardar reporte
                hunting.cancelarJuego();
                juegoActivo = false;

                // Volver al menú
                if (marco != null) {
                    marco.volverAlMenu();
                }
            }
        } else {
            // Si el juego ya terminó o no está activo, volver directamente
            if (marco != null) {
                marco.volverAlMenu();
            }
        }
    }// GEN-LAST:event_btnVolverMenuActionPerformed

    private void btnSalirJuegoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSalirJuegoActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas salir del juego?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Si hay un juego en curso, finalizarlo
            if (hunting != null && hunting.isJuegoEnCurso()) {
                hunting.cancelarJuego();
                juegoActivo = false;
            }

            // Salir de la aplicación
            System.exit(0);
        }
    }// GEN-LAST:event_btnSalirJuegoActionPerformed

    /**
     * Aplica el estilo visual consistente con el resto de la aplicación
     */
    private void aplicarEstilo() {
        // Estilizar los botones
        configurarBoton(btnVolverMenu, COLOR_NEGRO, COLOR_ROJO_NEON);
        configurarBoton(btnSalirJuego, COLOR_NEGRO, COLOR_ROJO_NEON);

        // Estilizar el panel de datos
        Color colorEtiqueta = COLOR_AZUL_NEON;
        jLabel1.setForeground(colorEtiqueta);
        jLabel3.setForeground(colorEtiqueta);
        lblNombre.setForeground(colorEtiqueta);
        lblAciertostxt.setForeground(COLOR_VERDE_NEON);
        lblFallostxt.setForeground(COLOR_ROSA_NEON);
        lblNombretxt.setForeground(COLOR_ROJO_NEON);

        // Estilizar el componente de tiempo
        lblTiempotxt.setForeground(COLOR_VERDE_NEON);
        lblTiempo.setForeground(colorEtiqueta);

        // Hacer los paneles transparentes para que se vea el degradado
        panelDatos.setOpaque(false);
        panelBotones.setOpaque(false);
        panelAreaPato.setOpaque(false);

        // Configurar el panelAreaPato para mostrar un cursor personalizado
        panelAreaPato.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));

        // Configurar icono de pato si no está ya configurado
        try {
            // Configuramos la imagen del pato
            javax.swing.ImageIcon iconoPato = new javax.swing.ImageIcon(
                    getClass().getResource("/practica2/resources/pato.png"));
            // La escalamos para que tenga un buen tamaño
            java.awt.Image img = iconoPato.getImage();
            java.awt.Image imgEscalada = img.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
            iconoPato = new javax.swing.ImageIcon(imgEscalada);
            lblPato.setIcon(iconoPato);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono del pato: " + e.getMessage());
        }

        // Cargar la imagen de fondo
        try {
            imagenFondo = new javax.swing.ImageIcon(getClass().getResource("/practica2/resources/fondoHunting.png"));
            if (imagenFondo.getImage() == null) {
                System.err.println("No se pudo cargar la imagen de fondo (null)");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }
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

        // Asegurarse de que el fondo cubra todo el panel
        int w = getWidth();
        int h = getHeight();

        // Dibujar la imagen de fondo si está disponible
        if (imagenFondo != null && imagenFondo.getImage() != null) {
            g2d.drawImage(imagenFondo.getImage(), 0, 0, w, h, this);
            
            // Aplicar una capa semitransparente con gradiente para mantener el estilo visual y para que se vea mas bonito
            g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.3f));
            GradientPaint gp = new GradientPaint(
                    0, 0, COLOR_NEGRO,
                    0, h, COLOR_MORADO_OSCURO);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
        } else {
            // Si la imagen no está disponible, usar solo el gradiente
            GradientPaint gp = new GradientPaint(
                    0, 0, COLOR_NEGRO,
                    0, h, COLOR_MORADO_OSCURO);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    /**
     * Actualiza los contadores de aciertos y fallos
     * 
     * @param aciertos Número de aciertos
     * @param fallos   Número de fallos
     */
    public void actualizarContadores(int aciertos, int fallos, String nombre) {
        lblAciertostxt.setText(String.valueOf(aciertos));
        lblFallostxt.setText(String.valueOf(fallos));
        lblNombretxt.setText(nombre);
    }

    /**
     * Actualiza la visualización del tiempo transcurrido
     * 
     * @param segundos Tiempo transcurrido en segundos
     */
    public void actualizarTiempo(int segundos) {
        lblTiempotxt.setText(String.valueOf(segundos));
    }

    /**
     * Implementación del método de la interfaz HuntingObservador.
     * Se llama cuando el controlador notifica un cambio.
     */
    @Override
    public void actualizar(ControladorHunting controlador) {
        // Asegurarse de que las actualizaciones de la UI se ejecuten en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Actualizar la interfaz con los datos más recientes del controlador
            actualizarInterfaz();

            // Si el juego está en curso, actualizar todos los componentes
            if (hunting.isJuegoEnCurso()) {
                // Actualizar posición y visibilidad del pato
                actualizarPatoUI();

                // Actualizar puntuación
                actualizarPuntuacionUI();

                // Actualizar tiempo
                actualizarTiempoUI();

            }
        });
    }

    /**
     * Limpia recursos al cerrar el panel
     */
    public void destruir() {
        // Desregistrar el panel como observador del controlador
        if (hunting != null) {
            hunting.eliminarObservador(this);
        }

        // Finalizar el juego si está en curso
        if (hunting != null && hunting.isJuegoEnCurso()) {
            hunting.cancelarJuego();
        }

        juegoActivo = false;
    }

    /**
     * Reinicia el panel para una nueva partida
     */
    public void reiniciarPanel() {
        // Restablecer el estado activo del juego
        juegoActivo = true;

        // Limpiar contadores visuales
        actualizarContadores(0, 0, "");
        actualizarTiempo(0);

        // Asegurar que el panel está conectado como observador
        conectarControlador();

        // Actualizar interfaz inicial
        actualizarInterfaz();
    }

    /**
     * Conecta este panel como observador del controlador actual
     */
    private void conectarControlador() {
        if (hunting != null) {
            hunting.registrarObservador(this);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalirJuego;
    private javax.swing.JButton btnVolverMenu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblAciertostxt;
    private javax.swing.JLabel lblFallostxt;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombretxt;
    private javax.swing.JLabel lblPato;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JLabel lblTiempotxt;
    private javax.swing.JPanel panelAreaPato;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelDatos;
    // End of variables declaration//GEN-END:variables
}
