/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practica2.view.buscaminas;

import practica2.controller.ControladorBuscaminas;
import practica2.controller.BuscaminasObservador;
import practica2.model.buscaminas.Casilla;
import practica2.model.buscaminas.Tablero;
import practica2.model.core.EstadoPartida;
import practica2.view.comun.MarcoPrincipal;
import practica2.view.comun.UiStyles;
import practica2.view.comun.UiTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.SwingUtilities;

/**
 * Panel que implementa el tablero visual del juego Buscaminas.
 * Implementa BuscaminasObservador para recibir notificaciones del controlador.
 *
 * @author aaron
 */
public class PanelBuscaminas extends javax.swing.JPanel implements BuscaminasObservador {
    // Constantes de colores - Estilo Gamer
    private static final Color COLOR_NEGRO = UiTheme.NEGRO;
    private static final Color COLOR_MORADO_OSCURO = UiTheme.MORADO_OSCURO;
    private static final Color COLOR_BLANCO = UiTheme.BLANCO;

    // Colores neón
    private static final Color COLOR_AZUL_NEON = UiTheme.AZUL_NEON;
    private static final Color COLOR_ROJO_NEON = UiTheme.ROJO_NEON;

    // Referencias principales
    private MarcoPrincipal marco;
    private ControladorBuscaminas controlador;

    // Componentes del tablero
    private JButton[][] botonesCasillas;
    private boolean tableroInicializado;
    private boolean juegoEnPausa;

    // Nueva variable para evitar mostrar el mensaje de fin de juego más de una vez
    private boolean mensajeFinJuegoMostrado = false;

    /**
     * Crea un nuevo PanelBuscaminas sin inicializar
     */
    public PanelBuscaminas() {
        initComponents();
    }

    /**
     * Constructor principal que recibe el marco y el controlador
     * 
     * @param marco       Marco principal de la aplicación
     * @param controlador Controlador del juego Buscaminas
     */
    public PanelBuscaminas(MarcoPrincipal marco, ControladorBuscaminas controlador) {
        this.marco = marco;
        this.controlador = controlador;
        this.tableroInicializado = false;
        this.juegoEnPausa = false;

        initComponents();

        // Configurar el layout del panel
        configurarLayoutTablero();

        // Aplicar estilos y configuraciones adicionales
        aplicarEstiloGamer();

        // Inicializar el tablero
        inicializarTablero();

        // Mostrar información inicial del jugador
        actualizarInformacionJugador();

        // Registrar este panel como observador del controlador
        if (controlador != null) {
            controlador.addObservador(this);
        }
    }

    /**
     * Configura el layout del panel tablero para hacerlo más cuadrado y centrado
     */
    private void configurarLayoutTablero() {
        // Eliminar el panel tablero actual del layout BorderLayout
        remove(panelTablero);

        // Crear un panel contenedor con FlowLayout centrado
        JPanel panelContenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelContenedor.setOpaque(false); // Para mantener el fondo visible

        // Configurar el panel tablero para que sea cuadrado
        panelTablero.setMaximumSize(new java.awt.Dimension(600, 600));
        panelTablero.setMinimumSize(new java.awt.Dimension(500, 500));
        panelTablero.setPreferredSize(new java.awt.Dimension(550, 550));

        // Agregar el tablero al contenedor centrado
        panelContenedor.add(panelTablero);

        // Agregar el contenedor al centro del BorderLayout
        add(panelContenedor, java.awt.BorderLayout.CENTER);

        // Actualizar la UI
        revalidate();
        repaint();
    }

    /**
     * Aplica el estilo visual consistente con el resto de la aplicación
     */
    private void aplicarEstiloGamer() {
        // Aplicar estilos a las etiquetas
        contadorMinasName.setForeground(COLOR_AZUL_NEON);
        contadorMinas.setForeground(COLOR_BLANCO);
        jLabel1.setForeground(COLOR_AZUL_NEON);
        nombreJugador.setForeground(COLOR_BLANCO);
        tiempoName.setForeground(COLOR_AZUL_NEON);
        tiempo.setForeground(COLOR_BLANCO);

        // Aplicar estilos a los botones
        configurarBoton(salirMenú, COLOR_NEGRO, COLOR_ROJO_NEON);
        configurarBoton(pausar, COLOR_NEGRO, COLOR_AZUL_NEON);
        configurarBoton(salirJuego, COLOR_NEGRO, COLOR_ROJO_NEON);

        // Paneles transparentes para ver el fondo
        panelSuperior.setOpaque(false);
        panelInferior.setOpaque(false);
        panelTablero.setOpaque(false);
    }

    /**
     * Configura un botón con estilos visuales consistentes
     * 
     * @param boton       El botón a configurar
     * @param colorNormal El color normal del botón
     * @param colorHover  El color cuando el ratón está sobre el botón
     */
    private void configurarBoton(JButton boton, Color colorNormal, Color colorHover) {
        UiStyles.configurarBotonNeon(boton, colorNormal, colorHover, COLOR_BLANCO);
    }

    /**
     * Inicializa el tablero de juego visual basado en el modelo
     */
    private void inicializarTablero() {
        if (controlador == null || controlador.getTablero() == null) {
            return;
        }

        Tablero tablero = controlador.getTablero();
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();

        // Configurar el panel del tablero
        panelTablero.removeAll();
        panelTablero.setLayout(new GridLayout(filas, columnas, 1, 1));

        // Crear botones para cada casilla
        botonesCasillas = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                final int fila = i;
                final int columna = j;

                JButton boton = new JButton();
                boton.setBackground(COLOR_NEGRO);
                boton.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_NEON, 1));
                boton.setFocusPainted(false);
                boton.setMargin(new Insets(0, 0, 0, 0));

                // Asignar eventos de ratón
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (juegoEnPausa) {
                            return; // No procesar clics si el juego está en pausa
                        }

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            // Clic izquierdo: descubrir casilla
                            controlador.descubrirCasilla(fila, columna);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            // Clic derecho: marcar casilla
                            controlador.marcarCasilla(fila, columna);
                        }
                    }
                });

                botonesCasillas[i][j] = boton;
                panelTablero.add(boton);
            }
        }

        tableroInicializado = true;
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    /**
     * Actualiza la vista del tablero según el estado actual del modelo
     */
    private void actualizarTablero() {
        if (controlador == null || controlador.getTablero() == null) {
            return;
        }

        Tablero tablero = controlador.getTablero();
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();

        // Verificar si el tablero visual necesita ser reinicializado:
        // - Si no está inicializado
        // - Si el tamaño ha cambiado
        // - Si los botones no tienen MouseListeners (juego terminado previamente)
        boolean reinicializarTablero = false;

        if (!tableroInicializado ||
                botonesCasillas == null ||
                botonesCasillas.length != filas ||
                (botonesCasillas.length > 0 && botonesCasillas[0].length != columnas)) {
            reinicializarTablero = true;
        } else if (botonesCasillas.length > 0 && botonesCasillas[0].length > 0) {
            // Verificar si los botones tienen listeners (si no tienen, se necesita
            // reinicializar)
            JButton unBoton = botonesCasillas[0][0];
            if (unBoton.getMouseListeners().length == 0) {
                reinicializarTablero = true;
            }
        }

        if (reinicializarTablero) {
            // El tamaño del tablero ha cambiado o los botones no tienen listeners,
            // reinicializar
            inicializarTablero();
            return;
        }

        // Actualizar la apariencia de cada casilla
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = tablero.getCasilla(i, j);
                JButton boton = botonesCasillas[i][j];

                if (casilla.estaCubierta()) {
                    if (casilla.estaMarcada()) {
                        // Casilla marcada con bandera
                        boton.setIcon(cargarIcono("/practica2/resources/bandera.png"));
                        boton.setText("");
                        boton.setBackground(COLOR_NEGRO);
                    } else {
                        // Casilla cubierta normal
                        boton.setIcon(null);
                        boton.setText("");
                        boton.setBackground(COLOR_NEGRO);
                    }
                } else {
                    // Casilla descubierta
                    boton.setIcon(null);
                    boton.setBackground(COLOR_BLANCO);

                    if (casilla.contieneMina()) {
                        // Es una mina
                        boton.setIcon(cargarIcono("/practica2/resources/mina.png"));
                        boton.setBackground(COLOR_ROJO_NEON);
                    } else {
                        // No es mina, mostrar número si hay minas adyacentes
                        int minasAdyacentes = casilla.getNumeroMinasAdyacentes();
                        if (minasAdyacentes > 0) {
                            boton.setText(String.valueOf(minasAdyacentes));
                            boton.setForeground(obtenerColorNumero(minasAdyacentes));
                        } else {
                            boton.setText("");
                        }
                    }
                }
            }
        }
    }

    /**
     * Actualiza la información del jugador y contadores
     */
    private void actualizarInformacionJugador() {
        if (controlador == null) {
            return;
        }

        // Actualizar nombre del jugador
        if (controlador.getConfiguracion() != null) {
            nombreJugador.setText(controlador.getConfiguracion().getNombreJugador());
        }

        // Actualizar contador de minas
        contadorMinas.setText(controlador.getCantidadMarcadas() + " / " + controlador.getCantidadMinas());

        // Actualizar tiempo
        tiempo.setText(controlador.getTiempoFormateado());
    }

    /**
     * Verifica si el juego ha terminado y muestra el resultado
     */
    private void verificarFinJuego() {
        if (controlador == null) {
            return;
        }

        if (controlador.esJuegoFinalizado()
                && !mensajeFinJuegoMostrado
                && controlador.getJuegoActual() != null
                && controlador.getJuegoActual().getEstadoPartida() != EstadoPartida.PAUSADA) {
            mensajeFinJuegoMostrado = true;
            Boolean ganado = controlador.esPartidaGanada();

            if (ganado != null) {
                // Desactivar interacción con el tablero
                desactivarTablero();

                // Desactivar botón de pausa
                pausar.setEnabled(false);

                // Mostrar mensaje diferente según si ganó o perdió
                String mensaje = ganado ? "¡Felicidades! Has completado el Buscaminas."
                        : "¡Has perdido! Cuidado con las minas la próxima vez.";

                String titulo = ganado ? "Victoria" : "Derrota";

                // Crear opciones personalizadas para el diálogo
                Object[] opciones = { "Volver al menú", "Volver a jugar" };

                // Crear un JOptionPane personalizado que no se pueda cerrar
                JDialog dialog = new JDialog();
                dialog.setModal(true);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // No permitir cerrar con X

                // Crear panel para el JOptionPane personalizado
                JOptionPane optionPane = new JOptionPane(
                        mensaje + "\n\nSeleccione una opción:",
                        ganado ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        opciones,
                        opciones[0]);

                // Configurar el diálogo
                dialog.setTitle(titulo);
                dialog.setContentPane(optionPane);

                // Agregar listener para capturar la selección de botones
                optionPane.addPropertyChangeListener(JOptionPane.VALUE_PROPERTY, evt -> {
                    if (JOptionPane.VALUE_PROPERTY.equals(evt.getPropertyName())
                            && optionPane.getValue() != JOptionPane.UNINITIALIZED_VALUE) {
                        dialog.dispose(); // Cerrar diálogo cuando se seleccione una opción
                    }
                });

                // Ajustar tamaño y centrar en pantalla
                dialog.pack();
                dialog.setLocationRelativeTo(this);

                // Finalizar la partida actual (generando reporte) antes de mostrar el diálogo
                // para que se registre correctamente
                controlador.finalizarPartida();
                if (controlador.getUltimoErrorReportes() != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            controlador.getUltimoErrorReportes(),
                            "Error al guardar reporte",
                            JOptionPane.ERROR_MESSAGE);
                }

                actualizarTablero();

                // Mostrar el diálogo (esto bloqueará hasta que el usuario seleccione una
                // opción)
                dialog.setVisible(true);

                // Obtener la opción seleccionada
                Object seleccion = optionPane.getValue();

                // Verificar que la selección sea válida (podría no serlo si se cerró de alguna
                // manera inesperada)
                if (seleccion != null && seleccion instanceof String) {
                    // Procesar la selección del usuario
                    if ("Volver al menú".equals(seleccion)) {
                        // Volver al menú principal
                        if (marco != null) {
                            marco.volverAlMenu();
                            mensajeFinJuegoMostrado = false; // Reiniciar el mensaje de fin de juego
                        }
                    } else if ("Volver a jugar".equals(seleccion)) {
                        // Reiniciar el juego con la misma configuración
                        reiniciarJuego();
                    }
                } else {
                    // Si de alguna manera se cerró el diálogo sin seleccionar opción,
                    // por defecto volver al menú
                    if (marco != null) {
                        marco.volverAlMenu();
                    }
                }
            }
        }
    }

    /**
     * Reinicia el juego con la misma configuración
     */
    private void reiniciarJuego() {
        // Obtener la configuración actual
        if (controlador != null && controlador.getConfiguracion() != null) {
            // Iniciar un nuevo juego con la misma configuración
            controlador.iniciarJuego(controlador.getConfiguracion());

            // Reiniciar el estado de la interfaz
            tableroInicializado = false;
            mensajeFinJuegoMostrado = false;
            juegoEnPausa = false;

            // Restablecer los botones
            pausar.setText("Pausar");
            pausar.setEnabled(true);

            // Limpiar los botones existentes para asegurar que se creen nuevos
            if (botonesCasillas != null) {
                for (JButton[] filaBotones : botonesCasillas) {
                    for (JButton boton : filaBotones) {
                        // Eliminar todos los MouseListeners para prevenir acumulación
                        for (MouseListener listener : boton.getMouseListeners()) {
                            boton.removeMouseListener(listener);
                        }
                    }
                }
            }
            panelTablero.removeAll();

            // Actualizar la interfaz
            inicializarTablero();
            actualizarInformacionJugador();
            actualizarTablero();

            // Refrescar la interfaz gráfica
            revalidate();
            repaint();
        }
    }

    /**
     * Desactiva la interacción con el tablero, útil al final del juego
     */
    private void desactivarTablero() {
        if (botonesCasillas == null) {
            return;
        }

        for (JButton[] filaBotones : botonesCasillas) {
            for (JButton boton : filaBotones) {
                // Eliminar todos los MouseListeners
                for (MouseListener listener : boton.getMouseListeners()) {
                    boton.removeMouseListener(listener);
                }
            }
        }
    }

    /**
     * Obtiene un color según el número de minas adyacentes
     * 
     * @param numero El número de minas adyacentes
     * @return Color correspondiente al número
     */
    private Color obtenerColorNumero(int numero) {
        switch (numero) {
            case 1:
                return Color.BLUE;
            case 2:
                return new Color(0, 128, 0); // Verde oscuro
            case 3:
                return Color.RED;
            case 4:
                return new Color(0, 0, 128); // Azul oscuro
            case 5:
                return new Color(128, 0, 0); // Marrón
            case 6:
                return Color.CYAN;
            case 7:
                return Color.BLACK;
            case 8:
                return Color.GRAY;
            default:
                return Color.BLACK;
        }
    }

    /**
     * Carga un icono desde los recursos de la aplicación
     * 
     * @param ruta Ruta al recurso
     * @return ImageIcon escalado o null si no se encuentra
     */
    private ImageIcon cargarIcono(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                System.err.println("No se pudo encontrar el recurso: " + ruta);
                return null;
            }

            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método de la interfaz BuscaminasObservador que actualiza la vista
     * cuando se produce un cambio en el modelo.
     * 
     * @param controlador El controlador que envía la notificación
     */
    @Override
    public void actualizar(ControladorBuscaminas controlador) {
        // Las notificaciones pueden venir de hilos de fondo (como el temporizador).
        // Asegurarse de que todas las actualizaciones de la UI se ejecuten en el EDT.
        SwingUtilities.invokeLater(() -> {
            // Actualizar toda la interfaz
            actualizarInformacionJugador();
            actualizarTablero();
            verificarFinJuego();
        });
    }

    /**
     * Pinta el fondo con un gradiente para mantener coherencia visual
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Crear un gradiente desde negro (arriba) hasta morado oscuro (abajo)
        GradientPaint gp = new GradientPaint(
                0, 0, COLOR_NEGRO,
                0, getHeight(), COLOR_MORADO_OSCURO);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Método para limpiar recursos al destruir el panel
     */
    public void destruir() {
        if (controlador != null) {
            controlador.removeObservador(this);
        }
    }

    /**
     * Reinicia el estado visual del panel para una nueva partida cuando el panel
     * es reutilizado (por ejemplo, al volver al menú y volver a entrar).
     * No inicia una partida nueva: eso es responsabilidad del flujo de configuración.
     */
    public void reiniciarPanelParaNuevaPartida() {
        // Asegurar que se ejecute en el EDT
        SwingUtilities.invokeLater(() -> {
            juegoEnPausa = false;
            mensajeFinJuegoMostrado = false;
            tableroInicializado = false;

            // Restablecer UI del botón de pausa (puede quedar deshabilitado tras finalizar)
            if (pausar != null) {
                pausar.setEnabled(true);
                pausar.setText("Pausar");
            }

            // Reconstruir tablero e info si ya existe un juego/tablero en el controlador
            if (controlador == null || controlador.getTablero() == null) {
                revalidate();
                repaint();
                return;
            }

            panelTablero.removeAll();
            inicializarTablero();
            actualizarInformacionJugador();
            actualizarTablero();
            revalidate();
            repaint();
        });
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

        panelTablero = new javax.swing.JPanel();
        panelSuperior = new javax.swing.JPanel();
        contadorMinasName = new javax.swing.JLabel();
        contadorMinas = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(25, 5), new java.awt.Dimension(95, 5),
                new java.awt.Dimension(25, 5));
        jLabel1 = new javax.swing.JLabel();
        nombreJugador = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(25, 5), new java.awt.Dimension(95, 5),
                new java.awt.Dimension(25, 5));
        tiempoName = new javax.swing.JLabel();
        tiempo = new javax.swing.JLabel();
        panelInferior = new javax.swing.JPanel();
        salirMenú = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(45, 15), new java.awt.Dimension(75, 15),
                new java.awt.Dimension(45, 15));
        pausar = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(45, 15), new java.awt.Dimension(75, 15),
                new java.awt.Dimension(45, 15));
        salirJuego = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1080, 700));
        setMinimumSize(new java.awt.Dimension(1080, 700));
        setPreferredSize(new java.awt.Dimension(1080, 700));
        setLayout(new java.awt.BorderLayout());

        panelTablero.setMaximumSize(new java.awt.Dimension(800, 32767));
        panelTablero.setMinimumSize(new java.awt.Dimension(800, 0));
        panelTablero.setOpaque(false);
        panelTablero.setPreferredSize(new java.awt.Dimension(800, 0));
        panelTablero.setLayout(new java.awt.GridLayout(1, 0));
        add(panelTablero, java.awt.BorderLayout.CENTER);

        panelSuperior.setMaximumSize(new java.awt.Dimension(1080, 32767));
        panelSuperior.setMinimumSize(new java.awt.Dimension(1080, 55));
        panelSuperior.setOpaque(false);
        panelSuperior.setPreferredSize(new java.awt.Dimension(1080, 55));

        contadorMinasName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contadorMinasName.setText("Minas marcadas: ");
        panelSuperior.add(contadorMinasName);

        contadorMinas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        contadorMinas.setText("0");
        panelSuperior.add(contadorMinas);
        panelSuperior.add(filler1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Nombre del jugador: ");
        panelSuperior.add(jLabel1);

        nombreJugador.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nombreJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreJugador.setText("11111");
        nombreJugador.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nombreJugador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelSuperior.add(nombreJugador);
        panelSuperior.add(filler2);

        tiempoName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tiempoName.setText("Tiempo: ");
        panelSuperior.add(tiempoName);

        tiempo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tiempo.setText("jLabel3");
        panelSuperior.add(tiempo);

        add(panelSuperior, java.awt.BorderLayout.PAGE_START);

        panelInferior.setOpaque(false);

        salirMenú.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        salirMenú.setText("Salir al menú");
        salirMenú.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenúActionPerformed(evt);
            }
        });
        panelInferior.add(salirMenú);
        panelInferior.add(filler3);

        pausar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pausar.setText("Pausar");
        pausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pausarActionPerformed(evt);
            }
        });
        panelInferior.add(pausar);
        panelInferior.add(filler4);

        salirJuego.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        salirJuego.setText("Salir del juego");
        salirJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirJuegoActionPerformed(evt);
            }
        });
        panelInferior.add(salirJuego);

        add(panelInferior, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void salirMenúActionPerformed(java.awt.event.ActionEvent evt) {
        if (marco != null) {
            // Si el juego ya está finalizado, simplemente volver al menú sin preguntar
            if (controlador != null && controlador.esJuegoFinalizado()) {
                // Finalizar apropiadamente la partida si no ha sido finalizada ya
                controlador.finalizarPartida();
                if (controlador.getUltimoErrorReportes() != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            controlador.getUltimoErrorReportes(),
                            "Error al guardar reporte",
                            JOptionPane.ERROR_MESSAGE);
                }
                marco.volverAlMenu();
                mensajeFinJuegoMostrado = false; // Reiniciar el mensaje de fin de juego
                return;
            }

            // Si la partida está en curso, pedir confirmación
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas volver al menú principal? Se perderá la partida actual.",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (respuesta == JOptionPane.YES_OPTION) {
                // Interrumpir partida en lugar de finalizarla para no generar reporte
                if (controlador != null) {
                    controlador.interrumpirPartida();
                }
                // Reiniciar estado antes de volver al menú
                mensajeFinJuegoMostrado = false;
                juegoEnPausa = false;
                marco.volverAlMenu();
            }
        }
    }

    private void pausarActionPerformed(java.awt.event.ActionEvent evt) {
        // Verificar si el controlador está disponible y el juego sigue en curso
        if (controlador == null) {
            return;
        }

        // Cambiar el estado de pausa
        juegoEnPausa = !juegoEnPausa;

        if (juegoEnPausa) {
            // Pausar el temporizador
            controlador.pausarTemporizador();

            // Cambiar el texto del botón
            pausar.setText("Reanudar");

            // Mostrar un mensaje informativo (no modal)
            JOptionPane.showMessageDialog(
                    this,
                    "Juego en pausa. Presiona el botón 'Reanudar' para continuar.",
                    "Juego Pausado",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Reanudar el temporizador
            controlador.reanudarTemporizador();

            // Restaurar el texto del botón
            pausar.setText("Pausar");


        }
    }

    private void salirJuegoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_salirJuegoActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas salir del juego? Se perderá la partida actual.",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            controlador.finalizarPartida();

            System.exit(0);
        }
    }// GEN-LAST:event_salirJuegoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contadorMinas;
    private javax.swing.JLabel contadorMinasName;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel nombreJugador;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JButton pausar;
    private javax.swing.JButton salirJuego;
    private javax.swing.JButton salirMenú;
    private javax.swing.JLabel tiempo;
    private javax.swing.JLabel tiempoName;
    // End of variables declaration//GEN-END:variables
}
